package fetalist.demo.service;

import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import fetalist.demo.bodies.CompleteShoppingListResponse;
import fetalist.demo.model.*;
import fetalist.demo.repository.FriendRepository;
import fetalist.demo.repository.ReceipeShoppingListRepository;
import fetalist.demo.repository.ShoppingListIngredientRepository;
import fetalist.demo.repository.ShoppingListRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class SMSServiceImpl implements SMSService {

    private FriendRepository friendRepository;
    private ShoppingListRepository shoppingListRepository;
    private ShoppingListIngredientRepository shoppingListIngredientRepository;
    private ReceipeShoppingListRepository receipeShoppingListRepository;
    private ShoppingListService shoppingListService;
    @Override
    public String shareSList(Token t, long idUserToSend, long idSLToShare) {
        VonageClient client = VonageClient.builder().apiKey("9dce1d5f").apiSecret("PAVZjYq0xIBObWl6").build();
        Users otherUser = Users.builder().idUser(idUserToSend).build();
        Friend f = idUserToSend == t.getUsers().getIdUser() ? Friend.builder().user1(t.getUsers()).status("ACCEPTED").build() : friendRepository.findBy(
                Example.of(
                        Friend.builder()
                                .user1(t.getUsers())
                                .user2(otherUser)
                                .build()), FluentQuery.FetchableFluentQuery::first)
                .orElse(friendRepository
                        .findBy(Example.of(
                                Friend.builder()
                                        .user1(otherUser)
                                        .user2(t.getUsers())
                                        .build()),FluentQuery.FetchableFluentQuery::first).orElse(null));
        if (f == null || !Objects.equals(f.getStatus(), Friend.ACCEPTED)) {
            return "Not friend with given user";
        }
        ShoppingList slToShare = shoppingListRepository.findById(idSLToShare).orElse(null);
        if (slToShare == null || !Objects.equals(slToShare.getUser().getIdUser(), t.getUsers().getIdUser())) return "This list isn't yours";
        TextMessage message = new TextMessage("Fetaliste",
                Objects.equals(f.getUser1().getIdUser(), t.getUsers().getIdUser()) ? f.getUser2().getPhone() : f.getUser1().getPhone(),
                this.createMessageFrom(t.getUsers().getName(), shoppingListService.getListFromId(t,slToShare.getId()))
        );

        SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);

        if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
            return "OK";
        } else {
            return "Message failed to send" + response.getMessages().get(0).getErrorText();
        }
    }

    @Override
    public String shareSListInApp(Token t, long idUserToSend, long idSLToShare) {
        Users otherUser = Users.builder().idUser(idUserToSend).build();
        Friend f = friendRepository.findBy(
                        Example.of(
                                Friend.builder()
                                        .user1(t.getUsers())
                                        .user2(otherUser)
                                        .build()), FluentQuery.FetchableFluentQuery::first)
                .orElse(friendRepository
                        .findBy(Example.of(
                                Friend.builder()
                                        .user1(otherUser)
                                        .user2(t.getUsers())
                                        .build()),FluentQuery.FetchableFluentQuery::first).orElse(null));
        if (f == null || !Objects.equals(f.getStatus(), Friend.ACCEPTED)) return "Not friend with given user";
        ShoppingList slToShare = shoppingListRepository.findById(idSLToShare).orElse(null);
        if (slToShare == null || !Objects.equals(slToShare.getUser().getIdUser(), t.getUsers().getIdUser())) return "This list isn't yours";
        ShoppingList sharedSL = ShoppingList.builder().user(otherUser).owner(slToShare.getOwner()).maxBuyDate(slToShare.getMaxBuyDate()).build();
        sharedSL = shoppingListRepository.save(sharedSL);
        CompleteShoppingListResponse crrOwned = shoppingListService.getListFromId(t, idSLToShare);
        ShoppingList finalSharedSL = sharedSL;
        crrOwned.getSli().forEach(i -> {
            ShoppingListIngredient newSI = ShoppingListIngredient.builder()
                    .ingredient(i.getIngredient())
                    .quantity(i.getQuantity())
                    .shoppingList(finalSharedSL)
                    .unit(i.getUnit()).build();
            shoppingListIngredientRepository.save(newSI);
        });
        crrOwned.getRsl().forEach(r -> {
            ReceipeShoppingList newRS = ReceipeShoppingList.builder()
                    .receipe(r.getReceipe())
                    .shoppingList(finalSharedSL).build();
            receipeShoppingListRepository.save(newRS);
        });
        return "OK";
    }

    private String createMessageFrom(String name, CompleteShoppingListResponse slToShare) {
        StringBuilder messageBuilder = new StringBuilder();

        messageBuilder.append("Bonjour,\n\nVoici votre liste de courses partagée par \"").append(name).append("\":\n\n");

        for (ShoppingListIngredient ingredient : slToShare.getSli()) {
            messageBuilder.append("  - ").append(ingredient.getIngredient().getName())
                    .append("(").append(ingredient.getQuantity());
            if (!Objects.equals(ingredient.getUnit().getName(), "---")
                    && !Objects.equals(ingredient.getUnit().getName(), "null")
                    && !Objects.equals(ingredient.getUnit().getName(), "")) messageBuilder.append(" ").append(ingredient.getUnit().getName());
            messageBuilder.append(")\n");
        }
        return messageBuilder.append("\nBonnes courses !").toString();
    }
}

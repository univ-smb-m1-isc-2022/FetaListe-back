package fetalist.demo.service;

import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import fetalist.demo.bodies.CompleteShoppingListResponse;
import fetalist.demo.model.*;
import fetalist.demo.repository.FriendRepository;
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
        if (f == null || !Objects.equals(f.getStatus(), "ACCEPTED")) {
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
            System.out.println("Message failed with error: " + response.getMessages().get(0).getErrorText());
            return "Message failed to send";
        }
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
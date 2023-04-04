package fetalist.demo.service;

import fetalist.demo.bodies.CompleteShoppingListResponse;
import fetalist.demo.model.*;
import fetalist.demo.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ShoppingListServiceImpl implements ShoppingListService {

    private ReceipeRepository receipeRepository;
    private ShoppingListRepository shoppingListRepository;
    private ShoppingListIngredientRepository shoppingListIngredientRepository;
    private ReceipeShoppingListRepository receipeShoppingListRepository;
    private ReceipeIngredientRepository receipeIngredientRepository;

    private CompleteShoppingListResponse createSLById(long id) {
        ShoppingList s = shoppingListRepository.findById(id).orElse(null);
        return CompleteShoppingListResponse.builder().sl(s)
                .sli(shoppingListIngredientRepository.findAll(Example.of(ShoppingListIngredient.builder().shoppingList(s).build())))
                .rsl(receipeShoppingListRepository.findAll(Example.of(ReceipeShoppingList.builder().shoppingList(s).build()))).build();
    }

    @Override
    public ShoppingList createList(Token t, Date reminderDate) {
        return shoppingListRepository.save(ShoppingList.builder()
                .user(t.getUsers())
                .owner(t.getUsers())
                .maxBuyDate(reminderDate)
                .build());
    }

    @Override
    public List<ShoppingList> getListOf(Token t) {
        return shoppingListRepository.findAll(Example.of(new ShoppingList(t.getUsers())));
    }

    @Override
    public CompleteShoppingListResponse getListFromId(Token t, long id) {
        ShoppingList s = shoppingListRepository.findById(id).orElse(null);
        if (s == null || !Objects.equals(s.getUser().getIdUser(), t.getUsers().getIdUser())) return null;
        return createSLById(s.getId());
    }

    @Override
    public CompleteShoppingListResponse addToList(Token t, long idShoppingList, List<Long> idsReceipes, long nbPersonnes) {
        // Récupération de la liste de course
        ShoppingList shoppingList = shoppingListRepository.findById(idShoppingList).orElse(null);
        if (shoppingList == null || !shoppingList.getUser().getIdUser().equals(t.getUsers().getIdUser())) {
            // La liste de course n'a pas été trouvée ou n'appartient pas à l'utilisateur connecté
            return null;
        }

        // Pour chaque recette, on l'ajoute à la liste de courses
        for (Long idReceipe : idsReceipes) {
            Receipe receipe = receipeRepository.findById(idReceipe).orElse(null);
            if (receipe == null) {
                continue;
            }

            ReceipeShoppingList rsl = new ReceipeShoppingList(receipe, shoppingList);
            ReceipeShoppingList receipeShoppingList = receipeShoppingListRepository.findBy(
                    Example.of(rsl),
                    FluentQuery.FetchableFluentQuery::first).orElse(null);
            if (receipeShoppingList == null) {
                receipeShoppingListRepository.save(rsl);
            }

            List<ReceipeIngredient> ril = receipeIngredientRepository.findAll(Example.of(ReceipeIngredient.builder().receipe(receipe).build()));
            ril.forEach(ri -> {
                ShoppingListIngredient siInShoppingList = shoppingListIngredientRepository.findBy(
                        Example.of(ShoppingListIngredient.builder()
                                .ingredient(ri.getIngredient())
                                .build()),FluentQuery.FetchableFluentQuery::first).orElse(null);
                siInShoppingList = ShoppingListIngredient.builder()
                        .shoppingList(shoppingList)
                        .unit(ri.getUnit())
                        .ingredient(ri.getIngredient())
                        .quantity(ri.getQuantity()*nbPersonnes + (siInShoppingList == null ? 0 : siInShoppingList.getQuantity()))
                        .build();
                shoppingListIngredientRepository.save(siInShoppingList);
            });

        }
        return createSLById(shoppingList.getId());
    }

    @Override
    public CompleteShoppingListResponse removeFromList(Token t, long idShoppingList, List<Long> idsReceipes, long nbPersonnes) {
        // Récupération de la liste de course
        ShoppingList shoppingList = shoppingListRepository.findById(idShoppingList).orElse(null);
        if (shoppingList == null || !shoppingList.getUser().getIdUser().equals(t.getUsers().getIdUser())) {
            // La liste de course n'a pas été trouvée ou n'appartient pas à l'utilisateur connecté
            return null;
        }

        for (Long idReceipe : idsReceipes) {
            Receipe receipe = receipeRepository.findById(idReceipe).orElse(null);
            if (receipe == null) {
                continue;
            }
            receipeShoppingListRepository.findBy(
                    Example.of(new ReceipeShoppingList(receipe, shoppingList)),
                    FluentQuery.FetchableFluentQuery::first)
                    .ifPresent(receipeShoppingList -> receipeShoppingListRepository.delete(receipeShoppingList));

            List<ReceipeIngredient> ril = receipeIngredientRepository.findAll(Example.of(ReceipeIngredient.builder().receipe(receipe).build()));
            ril.forEach(ri -> {
                ShoppingListIngredient siInShoppingList = shoppingListIngredientRepository.findBy(
                        Example.of(ShoppingListIngredient.builder()
                                .ingredient(ri.getIngredient())
                                .build()),FluentQuery.FetchableFluentQuery::first).orElse(null);
                if (siInShoppingList == null) return;
                siInShoppingList.setQuantity(siInShoppingList.getQuantity() - ri.getQuantity()*nbPersonnes);
                if (siInShoppingList.getQuantity() == 0) shoppingListIngredientRepository.delete(siInShoppingList);
                else shoppingListIngredientRepository.save(siInShoppingList);
            });
        }
        return createSLById(shoppingListRepository.save(shoppingList).getId());
    }

    @Override
    public boolean deleteList(Token t, long idShoppingList) {
        // Récupération de la liste de course
        ShoppingList shoppingList = shoppingListRepository.findById(idShoppingList).orElse(null);
        if (shoppingList == null || !shoppingList.getUser().getIdUser().equals(t.getUsers().getIdUser())) {
            // La liste de course n'a pas été trouvée ou n'appartient pas à l'utilisateur connecté
            return false;
        }
        shoppingListRepository.delete(shoppingList);
        return true;
    }
}
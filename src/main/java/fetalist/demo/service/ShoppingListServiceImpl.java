package fetalist.demo.service;

import fetalist.demo.model.*;
import fetalist.demo.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ShoppingListServiceImpl implements ShoppingListService {

    private ReceipeRepository receipeRepository;
    private ShoppingListRepository shoppingListRepository;
    private ShoppingListIngredientRepository shoppingListIngredientRepository;
    private ReceipeShoppingListRepository receipeShoppingListRepository;

    @Override
    public ShoppingList createList(Token t) {
        return shoppingListRepository.save(ShoppingList.builder()
                .user(t.getUsers())
                .owner(t.getUsers())
                .build());
    }

    @Override
    public List<ShoppingList> getListOf(Token t, long idShoppingList) {
        Users user = t.getUsers();
        if (idShoppingList == -1) return shoppingListRepository.findAll(Example.of(new ShoppingList(user)));
        ShoppingList s = shoppingListRepository.findById(idShoppingList).orElse(null);
        return s == null || !Objects.equals(s.getUser().getIdUser(), t.getUsers().getIdUser()) ? null : List.of(s);
    }

    @Override
    public ShoppingList addToList(Token t, long idShoppingList, List<Long> idsReceipes, List<ShoppingListIngredient> editedIngredients) {
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
        }

        for (ShoppingListIngredient si : editedIngredients) {
            // Si la recette est déjà dans la liste, on la supprime pour la réajouter plus tard avec les modifications
            ShoppingListIngredient siInShoppingList = shoppingListIngredientRepository.findBy(
                    Example.of(ShoppingListIngredient.builder()
                            .ingredient(si.getIngredient())
                            .build()),FluentQuery.FetchableFluentQuery::first).orElse(null);
            siInShoppingList = ShoppingListIngredient.builder()
                    .shoppingList(shoppingList)
                    .unit(si.getUnit())
                    .ingredient(si.getIngredient())
                    .quantity(si.getQuantity() + (siInShoppingList == null ? 0 : siInShoppingList.getQuantity()))
                    .build();
            shoppingListIngredientRepository.save(siInShoppingList);
        }
        return shoppingList;
    }

    @Override
    public ShoppingList removeFromList(Token t, long idShoppingList, List<Long> idsReceipes, List<ShoppingListIngredient> editedIngredients) {
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
                    FluentQuery.FetchableFluentQuery::first).ifPresent(receipeShoppingList -> receipeShoppingListRepository.delete(receipeShoppingList));
        }

        for (ShoppingListIngredient si : editedIngredients) {
            ShoppingListIngredient siInShoppingList = shoppingListIngredientRepository.findBy(
                    Example.of(ShoppingListIngredient.builder()
                            .ingredient(si.getIngredient())
                            .build()),FluentQuery.FetchableFluentQuery::first).orElse(null);
            if (siInShoppingList == null) continue;
            siInShoppingList.setQuantity(si.getQuantity() - siInShoppingList.getQuantity());
            if (siInShoppingList.getQuantity() == 0) shoppingListIngredientRepository.delete(siInShoppingList);
            else shoppingListIngredientRepository.save(siInShoppingList);
        }
        return shoppingListRepository.save(shoppingList);
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
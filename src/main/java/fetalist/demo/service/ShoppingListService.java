package fetalist.demo.service;

import fetalist.demo.model.ShoppingList;
import fetalist.demo.model.ShoppingListIngredient;
import fetalist.demo.model.Token;

import java.util.List;

public interface ShoppingListService {

    ShoppingList createList(Token t);

    List<ShoppingList> getListOf(Token t, long idShoppingList);

    ShoppingList addToList(Token t, long idShoppingList, List<Long> idsReceipes, List<ShoppingListIngredient> editedIngredients);

    ShoppingList removeFromList(Token t, long idShoppingList, List<Long> idsReceipes, List<ShoppingListIngredient> editedIngredients);

    boolean deleteList(Token t, long idShoppingList);
}

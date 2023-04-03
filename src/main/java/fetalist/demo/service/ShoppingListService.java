package fetalist.demo.service;

import fetalist.demo.bodies.CompleteShoppingListResponse;
import fetalist.demo.model.ShoppingList;
import fetalist.demo.model.Token;

import java.util.List;

public interface ShoppingListService {

    ShoppingList createList(Token t);

    List<CompleteShoppingListResponse> getListOf(Token t, long idShoppingList);

    CompleteShoppingListResponse addToList(Token t, long idShoppingList, List<Long> idsReceipes, long nbPersonnes);

    CompleteShoppingListResponse removeFromList(Token t, long idShoppingList, List<Long> idsReceipes, long nbPersonnes);

    boolean deleteList(Token t, long idShoppingList);
}

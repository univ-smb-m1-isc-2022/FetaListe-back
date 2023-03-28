package fetalist.demo.model;

import java.io.Serializable;
import java.util.Objects;

public class ReceipeShoppingListId implements Serializable {
    private Receipe receipe;
    private ShoppingList shoppingList;

    public ReceipeShoppingListId(Receipe receipe, ShoppingList shoppingList) {
        this.receipe = receipe;
        this.shoppingList = shoppingList;
    }

    public ReceipeShoppingListId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReceipeShoppingListId that = (ReceipeShoppingListId) o;
        return receipe.equals(that.receipe) && shoppingList.equals(that.shoppingList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receipe, shoppingList);
    }
}

package fetalist.demo.model;

import java.io.Serializable;
import java.util.Objects;

public class ShoppingListIngredientId implements Serializable {
    private ShoppingList shoppingList;
    private Ingredient ingredient;

    public ShoppingListIngredientId() {
    }

    public ShoppingListIngredientId(ShoppingList shoppingList, Ingredient ingredient) {
        this.shoppingList = shoppingList;
        this.ingredient = ingredient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingListIngredientId that = (ShoppingListIngredientId) o;
        return shoppingList.equals(that.shoppingList) && ingredient.equals(that.ingredient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shoppingList, ingredient);
    }
}

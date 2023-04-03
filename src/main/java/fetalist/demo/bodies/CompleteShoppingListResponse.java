package fetalist.demo.bodies;

import fetalist.demo.model.ReceipeShoppingList;
import fetalist.demo.model.ShoppingList;
import fetalist.demo.model.ShoppingListIngredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompleteShoppingListResponse {
    private ShoppingList sl;
    private List<ReceipeShoppingList> rsl;
    private List<ShoppingListIngredient> sli;
}

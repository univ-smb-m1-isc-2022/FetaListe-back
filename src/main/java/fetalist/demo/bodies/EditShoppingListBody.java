package fetalist.demo.bodies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditShoppingListBody {
    private String token;
    private long idShoppingList;
    private boolean add;
    private List<Long> idsReceipes;
    private long nbPersonnes;
}

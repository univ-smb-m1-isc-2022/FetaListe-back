package fetalist.demo.bodies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateShoppingListBody {
    private String token;
    private long idReceipe;
    private Date maxBuyDate;
}

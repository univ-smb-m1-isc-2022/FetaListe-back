package fetalist.demo.bodies;
import fetalist.demo.model.Favorite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteRemoveBody {
    private String token;
    private Favorite favorite;
}

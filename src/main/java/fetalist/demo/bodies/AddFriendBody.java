package fetalist.demo.bodies;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddFriendBody {
    private String token;
    private long idUserToAdd;
}

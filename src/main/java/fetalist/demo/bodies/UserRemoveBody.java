package fetalist.demo.bodies;

import fetalist.demo.model.Token;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRemoveBody {
    private Token token;
    private String password;
}

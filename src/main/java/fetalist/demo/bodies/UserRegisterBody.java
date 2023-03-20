package fetalist.demo.bodies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterBody {
    private String provider;
    private String name;
    private String password;
    private String phoneNumber;
    private String mail;
}
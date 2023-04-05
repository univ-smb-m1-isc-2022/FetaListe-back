package fetalist.demo.bodies;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListShareSMSBody {
    private String token;
    private long idSLToShare;
    private long idUserToSend;
}

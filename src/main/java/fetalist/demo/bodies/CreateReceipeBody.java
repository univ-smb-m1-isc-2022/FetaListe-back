package fetalist.demo.bodies;

import fetalist.demo.model.ReceipeIngredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateReceipeBody {
    private String name;
    private String categoryName;
    private String image;
    private long estimatedTime;
    private List<ReceipeIngredient> ri;
    private List<String> instructions;
}

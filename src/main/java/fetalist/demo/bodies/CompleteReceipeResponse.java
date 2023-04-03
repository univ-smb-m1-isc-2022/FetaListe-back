package fetalist.demo.bodies;

import fetalist.demo.model.Instruction;
import fetalist.demo.model.Receipe;
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
public class CompleteReceipeResponse {
    private Receipe receipe;
    private List<Instruction> instructions;
    private List<ReceipeIngredient> ri;
}

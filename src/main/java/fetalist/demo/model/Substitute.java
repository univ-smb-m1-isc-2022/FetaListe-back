package fetalist.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // lombok, getter and setter
@Builder // lombok design partern builder
@AllArgsConstructor // constructor with all args
@NoArgsConstructor // constructor with no args
@Entity
@Table
@IdClass(SubstituteId.class)
public class Substitute {
    @Id
    @ManyToOne
    private Ingredient baseIngredient;
    @Id
    @ManyToOne
    private Ingredient substitute;
}

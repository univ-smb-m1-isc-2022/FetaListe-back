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
@IdClass(ReceipeIngredientId.class)
public class ReceipeIngredient
{
    @Id
    @ManyToOne
    private Receipe receipe;
    @Id
    @ManyToOne
    private Ingredient ingredient;
    @ManyToOne
    private Unit unit;
    private Double quantity;
}



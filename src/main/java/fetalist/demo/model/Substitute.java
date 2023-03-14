package fetalist.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // lombok, getter and setter
@Builder // lombok design partern builder
@AllArgsConstructor // constructor with all args
@NoArgsConstructor // constructor with no args
@Entity
@Table(name= "Receipe_has_Ingredient")
public class Substitute {
    @Id
    @Column(name="Ingredients_idIngredients")
    private Integer idIngredient0;
    @Id
    @Column(name="Ingredients_idIngredients1")
    private Integer idIngredient1;
}

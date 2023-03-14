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
@Table(name= "ReceipeIngredient")
public class ReceipeIngredient
{
    @Id
    @Column (name ="Receipe_idReceipe")
    private Integer idReceipe;
    @Id
    @Column(name="Ingredients_idIngredients")
    private Integer idIngredient;

    @Id
    @Column(length = 45,name="Unity_idUnity")
    private String unity;

    private float quantity;
}

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
@Table(name= "public class ReceipeShoppingList")
public class ReceipeShoppingList {

    @Id
    @Column(name ="ShoppingList_idShoppingList")
    private Integer idShoppingList;
    @Id
    @Column(name="Ingrédients_idIngredients")
    private Integer idIngredient;

    @Id
    @Column(name="Unity_idUnity")
    private Integer unity;
}

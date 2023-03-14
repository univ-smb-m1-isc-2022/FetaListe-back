package fetalist.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data // lombok, getter and setter
@Builder // lombok design partern builder
@AllArgsConstructor // constructor with all args
@NoArgsConstructor // constructor with no args
@Entity
@Table(name= "ShoppingList")
public class ShoppingList {
    @Id
    @Column(name="Ingredients_idIngredients")
    private Integer idShoppingList;
    @Column(name="User_idUser")
    private Integer idUser;
    @Column(name="User_owner")
    private Integer idOwner;
    @Column(name="maxBuyDate")
    private Date maxBuyDate;
}

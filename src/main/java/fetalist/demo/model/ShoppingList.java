package fetalist.demo.model;

import jakarta.persistence.*;
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
@Table
public class ShoppingList {
    @Id
    private Long idShoppingList;
    @ManyToOne
    private Users user;
    @ManyToOne
    private Users owner;
    private Date maxBuyDate;

    public ShoppingList(Users user) {
        this.user=user;
    }
}

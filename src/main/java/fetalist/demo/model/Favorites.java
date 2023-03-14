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
@Table(name= "Favorite")
public class Substitute {
    @Id
    @Column(name="User_idUser")
    private Integer idUser;
    @Id
    @Column(name="Receipe_idReceipe")
    private Integer idReceipe;
}

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
@Table(name= "Friend")
public class Substitute {
    @Id
    @Column(name="idFriendship")
    private Integer idFriendship;

    @Column(name="idUser1")
    private Integer idUser1;

    @Column(name="idUser2")
    private Integer idUser2;

    @Id
    @Column(name="status", lenght=10)
    private String status;
}

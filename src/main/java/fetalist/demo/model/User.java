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
@Table(name= "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;
    @Column(length = 20, name="name")
    private String name;
    @Column (length = 45,name="password")
    private String password;
    @Column (length = 50,name="mail")
    private String mail;
    @Column (length = 15,name="phone")
    private String phone;
    @Column (name="registerDate")
    private Date registerDate;
}

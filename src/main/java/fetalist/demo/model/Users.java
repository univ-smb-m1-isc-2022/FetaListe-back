package fetalist.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    @Column(length = 20)
    private String name;
    @Column (length = 45)
    @JsonIgnore
    private String password;
    @Column (length = 50)
    private String mail;
    @Column (length = 15)
    private String phone;
    private Date registerDate;

    public Users(String name, String password) {
        this.name = name;
        this.password = password;
    }
}

package fetalist.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data // lombok, getter and setter
@Builder // lombok design partern builder
@AllArgsConstructor // constructor with all args
@NoArgsConstructor // constructor with no args
@Entity
@Table
public class Token {
    @Id
    private Long idToken;

    @OneToOne
    private Users idUsers;

    @Column(length=50)
    private String accessToken;

    private Date accessValidUntil;

    @Column(length=50)
    private String refreshToken;

    @Column(length=45)
    private String refreshValidToken;

    @Column(length=45)
    private String provider;


    public Token(Users idUser) {
        this.idUsers = idUser;
    }
}

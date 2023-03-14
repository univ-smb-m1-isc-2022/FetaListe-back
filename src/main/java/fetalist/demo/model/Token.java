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
@Table(name= "Token")
public class Substitute {
    @Id
    @Column(name="idToken")
    private Integer idToken;

    @Column(name="User_idUser")
    private Integer idUser;

    @Column(name="accessToken", lenght=50)
    private String accessToken;

    @Column(name="accessValidUntil")
    private Date accessValidUntil;

    @Column(name="refereshToken", length=50)
    private String refreshToken;

    @Column(name="refereshValidToken", length=45)
    private String refreshValidToken;

    @Column(name="provider", length=45)
    private String provider;


}

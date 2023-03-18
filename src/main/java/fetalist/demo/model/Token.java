package fetalist.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.sql.Date;
import java.time.Instant;
import java.util.Base64;

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
    private Users users;

    @Column(length=50)
    private String accessToken;

    private Date accessValidUntil;

    @Column(length=50)
    private String refreshToken;

    @Column(length=45)
    private String refreshValidToken;

    @Column(length=45)
    private String provider;

    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public Token(Users user) {
        this.users = user;
    }

    public Token(Users u, String provider) {
        this.users = u;
        this.provider = provider;
    }

    /**
     * Refresh la validité du token quand une nouvelle connexion par mot de passe est effectuée (validité = 7 jours)
     */
    public void refreshAccessValidUntil() {
        accessValidUntil = (Date) Date.from(Instant.now().plusSeconds(7*24*60*60));
    }

    public static Token generateToken(Users u, String provider) {
        Token t = new Token(u, provider);
        t.accessToken = generateNewToken();
        t.refreshToken = generateNewToken();
        t.refreshValidToken = generateNewToken();
        t.refreshAccessValidUntil();
        return t;
    }
}

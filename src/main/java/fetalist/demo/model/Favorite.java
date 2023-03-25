package fetalist.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // lombok, getter and setter
@Builder // lombok design partern builder
@AllArgsConstructor // constructor with all args
@NoArgsConstructor // constructor with no args
@Entity
@Table
public class Favorite {
    @Id
    @ManyToOne
    private Users users;
    @Id
    @ManyToOne
    private Receipe receipe;

    public Favorite(Users users) {
        this.users = users;
    }
}

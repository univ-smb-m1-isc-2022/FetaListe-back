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
@IdClass(FriendId.class)
public class Friend {
    @Id
    @ManyToOne
    private Users user1;

    @Id
    @ManyToOne
    private Users user2;

    @Column(length=10)
    private String status;

    public static final String ACCEPTED = "ACCEPTED";
    public static final String PENDING = "PENDING";
}

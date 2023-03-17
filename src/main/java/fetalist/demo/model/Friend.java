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
public class Friend {
    @Id
    @ManyToOne
    private Users idUsers1;

    @Id
    @ManyToOne
    private Users idUsers2;

    @Column(length=10)
    private String status;
}

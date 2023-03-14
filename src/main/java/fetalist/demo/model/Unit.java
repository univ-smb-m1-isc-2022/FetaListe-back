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
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUnit;
    @Column(length = 10)
    private String name;

    public Unit(String unit) {
        name = unit;
    }
}

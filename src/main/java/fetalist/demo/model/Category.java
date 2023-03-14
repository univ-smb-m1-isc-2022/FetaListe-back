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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Category(String category) {
        name=category;
    }
}

package fetalist.demo.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // lombok, getter and setter
@Builder // lombok design partern builder
@AllArgsConstructor // constructor with all args
@NoArgsConstructor // constructor with no args
@Entity
@Table(name= "Receipe")
public class Receipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @Column(name = "Category_idCategory")
    private Category category;
    @Column(length = 100, name="name")
    private String name;
    @Column(length = 500, anme="image")
    private String image;
    @Column(name = "rating")
    private float rating;
    @Column(name = "estimatedTime")
    private float estimatedTime;

}

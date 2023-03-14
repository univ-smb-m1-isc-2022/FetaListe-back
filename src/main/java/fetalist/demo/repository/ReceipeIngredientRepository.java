package fetalist.demo.repository;

import fetalist.demo.model.ReceipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceipeIngredientRepository extends JpaRepository<ReceipeIngredient, Long> {

}

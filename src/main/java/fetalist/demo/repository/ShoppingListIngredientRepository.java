package fetalist.demo.repository;

import fetalist.demo.model.ShoppingListIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingListIngredientRepository extends JpaRepository<ShoppingListIngredient, Long> {

}

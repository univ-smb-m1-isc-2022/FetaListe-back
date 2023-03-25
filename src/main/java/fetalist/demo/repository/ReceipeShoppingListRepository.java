package fetalist.demo.repository;

import fetalist.demo.model.ReceipeShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceipeShoppingListRepository extends JpaRepository<ReceipeShoppingList, Long> {

}

package fetalist.demo.repository;

import fetalist.demo.model.Receipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceipeRepository extends JpaRepository<Receipe, Long> {

}

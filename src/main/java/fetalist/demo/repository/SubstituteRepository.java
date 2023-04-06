package fetalist.demo.repository;

import fetalist.demo.model.Substitute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubstituteRepository extends JpaRepository<Substitute, Long> {

}

package fetalist.demo.service;

import fetalist.demo.model.Recette;
import fetalist.demo.repository.RecetteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RecetteService {
    Recette createRecette(Recette recette);
    List<Recette> getAllRecette();
    Recette getRecetteById(Long id);
}

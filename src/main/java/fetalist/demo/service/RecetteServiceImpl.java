package fetalist.demo.service;

import fetalist.demo.model.Recette;
import fetalist.demo.repository.RecetteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecetteServiceImpl implements RecetteService{
    private RecetteRepository recetteRepository;
    @Override
    public Recette createRecette(Recette recette) {
        return recetteRepository.save(recette);
    }

    @Override
    public List<Recette> getAllRecette() {
        return recetteRepository.findAll();
    }

    @Override
    public Recette getRecetteById(Long id) {
        return recetteRepository.findById(id).orElse(null);
    }
}

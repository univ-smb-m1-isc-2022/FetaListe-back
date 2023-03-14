package fetalist.demo.service;

import fetalist.demo.model.Receipe;
import fetalist.demo.repository.ReceipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReceipeServiceImpl implements ReceipeService{
    private ReceipeRepository receipeRepository;
    @Override
    public Receipe createReceipe(Receipe receipe) {
        return receipeRepository.save(receipe);
    }

    @Override
    public List<Receipe> getAllReceipe() {
        return receipeRepository.findAll();
    }

    @Override
    public Receipe getReceipeById(Long id) {
        return receipeRepository.findById(id).orElse(null);
    }
}

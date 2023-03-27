package fetalist.demo.service;

import fetalist.demo.model.Receipe;

import java.util.List;
import java.util.Optional;

public interface ReceipeService {
    Receipe createReceipe(Receipe receipe);
    List<Receipe> getAllReceipe();
    Optional<Receipe> getReceipeById(Long id);
}

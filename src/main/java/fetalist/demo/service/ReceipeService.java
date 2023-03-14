package fetalist.demo.service;

import fetalist.demo.model.Receipe;

import java.util.List;

public interface ReceipeService {
    Receipe createReceipe(Receipe receipe);
    List<Receipe> getAllReceipe();
    Receipe getReceipeById(Long id);
    void fillDatabaseWithJson();
}

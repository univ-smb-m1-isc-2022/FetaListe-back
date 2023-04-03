package fetalist.demo.service;

import fetalist.demo.bodies.CompleteReceipeResponse;
import fetalist.demo.bodies.CreateReceipeBody;
import fetalist.demo.model.Receipe;

import java.util.List;

public interface ReceipeService {
    boolean createReceipe(CreateReceipeBody body);
    List<Receipe> getAllReceipe();
    CompleteReceipeResponse getReceipeById(Long id);
}

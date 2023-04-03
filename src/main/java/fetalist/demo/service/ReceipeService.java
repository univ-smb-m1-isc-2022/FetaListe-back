package fetalist.demo.service;

import fetalist.demo.bodies.CompleteReceipeResponse;
import fetalist.demo.bodies.CreateReceipeBody;

import java.util.List;

public interface ReceipeService {
    boolean createReceipe(CreateReceipeBody body);
    List<CompleteReceipeResponse> getAllReceipe();
    CompleteReceipeResponse getReceipeById(Long id);
}

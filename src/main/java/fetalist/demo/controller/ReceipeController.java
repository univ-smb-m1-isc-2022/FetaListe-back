package fetalist.demo.controller;

import fetalist.demo.bodies.CompleteReceipeResponse;
import fetalist.demo.bodies.CreateReceipeBody;
import fetalist.demo.model.Receipe;
import fetalist.demo.service.ReceipeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receipe")
@AllArgsConstructor
public class ReceipeController {


    private ReceipeService receipeService;

    @PostMapping("/create")
    public ResponseEntity<Boolean> createReceipe(@RequestBody CreateReceipeBody body) {

        try {
            boolean hasBeenCreated = receipeService.createReceipe(body);
            return ResponseEntity.ok(hasBeenCreated);
        }catch (Exception e ){
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/getAll")
    public List<Receipe> getAllReceipe() {
        return receipeService.getAllReceipe();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompleteReceipeResponse> getReceipeById(@PathVariable Long id) {

        CompleteReceipeResponse receipe = receipeService.getReceipeById(id);

        if (receipe == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(receipe);
        }


    }

    @GetMapping("/search")
    public ResponseEntity<List<Receipe>> searchReceipe(@RequestParam(required = false) String name) {
        List<Receipe> receipes = receipeService.searchReceipe(name);
        if (receipes == null || receipes.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(receipes);
        }
    }

}

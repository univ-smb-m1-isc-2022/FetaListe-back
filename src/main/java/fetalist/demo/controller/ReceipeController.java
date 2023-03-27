package fetalist.demo.controller;

import fetalist.demo.bodies.UserRegisterBody;
import fetalist.demo.model.Receipe;
import fetalist.demo.model.Token;
import fetalist.demo.service.ReceipeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/receipe")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ReceipeController {


    private ReceipeService receipeService;

    @PostMapping("/create")
    public ResponseEntity<Receipe> createReceipe(Receipe receipe) {

        try {
            Receipe newReceipe= receipeService.createReceipe(receipe);
            return ResponseEntity.ok(newReceipe);
        }catch (Exception e ){
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/getAll")
    public List<Receipe> getAllReceipe() {
        return receipeService.getAllReceipe();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receipe> getReceipeById(@PathVariable Long id) {

        Optional<Receipe> receipe = this.receipeService.getReceipeById(id);

        if (receipe.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(receipe.get());
        }


    }

}

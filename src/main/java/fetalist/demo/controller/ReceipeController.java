package fetalist.demo.controller;

import fetalist.demo.model.Receipe;
import fetalist.demo.service.ReceipeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receipe")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ReceipeController {


    private ReceipeService receipeService;

    @PostMapping("/create")
    public Receipe createReceipe(Receipe receipe) {
        return receipeService.createReceipe(receipe);
    }

    @GetMapping("/getAll")
    public List<Receipe> getAllReceipe() {
        return receipeService.getAllReceipe();
    }

    @GetMapping("/getById")
    public Receipe getReceipeById(Long id) {
        return receipeService.getReceipeById(id);
    }

    @PostMapping("/fillDatabase")
    public void fillDatabaseWithJson() {
        receipeService.fillDatabaseWithJson();
    }
}

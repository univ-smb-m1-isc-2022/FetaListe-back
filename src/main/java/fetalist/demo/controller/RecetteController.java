package fetalist.demo.controller;

import fetalist.demo.model.Recette;
import fetalist.demo.service.RecetteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recette")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class RecetteController {


    private RecetteService recetteService;

    @PostMapping("/create")
    public Recette createRecette(Recette recette) {
        return recetteService.createRecette(recette);
    }

    @GetMapping("/getAll")
    public List<Recette> getAllRecette() {
        return recetteService.getAllRecette();
    }

    @GetMapping("/getById")
    public Recette getRecetteById(Long id) {
        return recetteService.getRecetteById(id);
    }


}

package fetalist.demo.controller;

import fetalist.demo.model.Ingredient;
import fetalist.demo.service.IngredientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class IngredientController {


    private IngredientService ingredientService;

    @PostMapping("/create")
    public Ingredient createIngredient(String ingredientName) {
        return ingredientService.createIngredient(ingredientName);
    }

    @PostMapping("/getAll")
    public List<Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }
}

package fetalist.demo.controller;

import fetalist.demo.model.Ingredient;
import fetalist.demo.service.IngredientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
@AllArgsConstructor
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

    @GetMapping("/search")
    public ResponseEntity<List<Ingredient>> searchReceipe(@RequestParam String name) {
        List<Ingredient> ing = ingredientService.searchIngredient(name);
        if (ing == null || ing.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(ing);
        }
    }
}

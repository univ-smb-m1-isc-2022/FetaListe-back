package fetalist.demo.controller;

import fetalist.demo.bodies.SubstituteAddBody;
import fetalist.demo.bodies.SubstituteRemoveBody;
import fetalist.demo.model.Substitute;
import fetalist.demo.service.SubstituteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/substitute")
@AllArgsConstructor
public class SubstituteController {
    private SubstituteService substituteService;

    @PostMapping("/add")
    public ResponseEntity<Substitute> createSubstitute(@RequestBody SubstituteAddBody body) {
        Substitute s = substituteService.createSubstitute(body.getIdIngredient(), body.getIdIngredientToSubstitute());
        return s == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(s);
    }

    @GetMapping("/getAllOf/{ingredientId}")
    public ResponseEntity<List<Substitute>> getAllSubstituteOf(@PathVariable long ingredientId) {
        return ResponseEntity.ok(substituteService.getAllSubstitutesOf(ingredientId));
    }

    @PostMapping("/remove")
    public ResponseEntity<Boolean> removeSubstitute(@RequestBody SubstituteRemoveBody body) {
        return substituteService.removeSubstitute(body.getIdIngredient(), body.getIdIngredientToSubstitute()) ? ResponseEntity.ok(true) : ResponseEntity.badRequest().build();
    }

}

package fetalist.demo.service;

import fetalist.demo.model.Ingredient;
import fetalist.demo.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class IngredientServiceImpl implements IngredientService{
    private IngredientRepository ingredientRepository;

    @Override
    public Ingredient createIngredient(String ingName) {
        Ingredient ingToAdd = new Ingredient(ingName);
        return ingredientRepository.findBy(Example.of(ingToAdd), FluentQuery.FetchableFluentQuery::first).orElseGet(() -> ingredientRepository.save(ingToAdd));
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    @Override
    public List<Ingredient> searchIngredient(String name) {
        List<Ingredient> ings = ingredientRepository.findAll();

        List<Ingredient> results = new ArrayList<>();
        for (Ingredient ing : ings) {
            if (ing.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(ing);
            }
        }
        return results;
    }
}

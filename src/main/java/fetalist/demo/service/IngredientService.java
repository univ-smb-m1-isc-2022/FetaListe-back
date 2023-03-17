package fetalist.demo.service;

import fetalist.demo.model.Ingredient;

import java.util.List;

public interface IngredientService {
    Ingredient createIngredient(String ingName);
    List<Ingredient> getAllIngredients();
}

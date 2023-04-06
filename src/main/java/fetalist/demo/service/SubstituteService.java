
package fetalist.demo.service;

import fetalist.demo.model.Substitute;

import java.util.List;

public interface SubstituteService {
    Substitute createSubstitute(long idIngredient, long idIngredientToSubstitute);

    List<Substitute> getAllSubstitutesOf(long ingredientId);

    boolean removeSubstitute(long idIngredient, long idIngredientToSubstitute);
}

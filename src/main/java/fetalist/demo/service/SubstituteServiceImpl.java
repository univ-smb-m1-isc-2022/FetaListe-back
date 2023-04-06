package fetalist.demo.service;

import fetalist.demo.model.Ingredient;
import fetalist.demo.model.Substitute;
import fetalist.demo.repository.IngredientRepository;
import fetalist.demo.repository.SubstituteRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubstituteServiceImpl implements SubstituteService{

    private SubstituteRepository substituteRepository;
    private IngredientRepository ingredientRepository;

    @Override
    public Substitute createSubstitute(long idIngredient, long idIngredientToSubstitute) {
        Ingredient i1 = ingredientRepository.findBy(Example.of(Ingredient.builder().idIngredient(idIngredient).build()), FluentQuery.FetchableFluentQuery::first).orElse(null);
        Ingredient i2 = ingredientRepository.findBy(Example.of(Ingredient.builder().idIngredient(idIngredientToSubstitute).build()), FluentQuery.FetchableFluentQuery::first).orElse(null);
        if (i1 == null || i2 == null) return null;
        return substituteRepository.save(Substitute.builder()
                .baseIngredient(i1).substitute(i2).build());
    }

    @Override
    public List<Substitute> getAllSubstitutesOf(long ingredientId) {
        return substituteRepository.findAll().stream().filter(s -> s.getBaseIngredient().getIdIngredient() == ingredientId).collect(Collectors.toList());
    }

    @Override
    public boolean removeSubstitute(long idIngredient, long idIngredientToSubstitute) {
        Ingredient i1 = ingredientRepository.findBy(Example.of(Ingredient.builder().idIngredient(idIngredient).build()), FluentQuery.FetchableFluentQuery::first).orElse(null);
        Ingredient i2 = ingredientRepository.findBy(Example.of(Ingredient.builder().idIngredient(idIngredientToSubstitute).build()), FluentQuery.FetchableFluentQuery::first).orElse(null);
        if (i1 == null || i2 == null) return false;
        Substitute s = substituteRepository.findBy(Example.of(Substitute.builder()
                .baseIngredient(i1).substitute(i2).build()), FluentQuery.FetchableFluentQuery::first).orElse(null);
        if (s == null) return false;
        substituteRepository.delete(s);
        return true;
    }
}

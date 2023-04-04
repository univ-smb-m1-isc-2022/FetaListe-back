package fetalist.demo.service;

import fetalist.demo.model.*;
import fetalist.demo.repository.*;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PopulateDBImpl implements PopulateDBService{
    private ReceipeRepository receipeRepository;
    private CategoryRepository categoryRepository;
    private InstructionRepository instructionRepository;
    private IngredientRepository ingredientRepository;
    private UnitRepository unitRepository;
    private ReceipeIngredientRepository receipeIngredientRepository;
    private final ResourceLoader resourceLoader;

    @Override
    public String fillDatabaseWithJson() {
        if (receipeRepository.count() != 0) return "already filled";
        try
        {
            InputStream in = resourceLoader.getResource("classpath:recettesBase.json").getInputStream();
            JSONArray receipeList = new JSONArray(new JSONTokener(in));
            receipeList.forEach( rec -> parseReceipeObject( (JSONObject) rec ));
            return "OK";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occured :\n".concat(e.toString());
        }
    }

    private void parseReceipeObject(JSONObject receipe)
    {
        Category example = new Category((String) receipe.get("category"));
        Category recetteC = categoryRepository.findBy(Example.of(example), FluentQuery.FetchableFluentQuery::first).orElse(null);
        if (recetteC == null) {
            recetteC = categoryRepository.save(example);
        }
        Receipe recette = new Receipe();
        recette.setCategory(recetteC);
        recette.setName((String) receipe.get("name"));
        recette.setImage((String) receipe.get("image"));
        recette.setRating(((BigDecimal)receipe.get("rating")).doubleValue());
        recette.setEstimatedTime(((Integer) receipe.get("estimated_time")).longValue());

        recette = receipeRepository.save(recette);

        JSONArray ingList = (JSONArray) receipe.get("ingredients");
        Receipe finalRecette = recette;
        ingList.forEach(ing -> parseIngredient((JSONObject) ing, finalRecette));

        JSONArray instList = (JSONArray) receipe.get("instructions");
        instList.forEach(inst -> parseInstruction((String) inst, finalRecette));
    }

    private void parseInstruction(String inst, Receipe recette) {
        Instruction i = new Instruction();
        i.setInstruction(inst);
        i.setReceipe(recette);
        instructionRepository.save(i);
    }

    private void parseIngredient(JSONObject ing, Receipe recette) {
        Ingredient exampleIng = new Ingredient((String) ing.get("name"));
        Ingredient ingredient = ingredientRepository.findBy(Example.of(exampleIng), FluentQuery.FetchableFluentQuery::first).orElse(null);
        if (ingredient == null) {
            ingredient = ingredientRepository.save(exampleIng);
        }
        Unit exampleUnit = new Unit(Objects.equals(ing.get("unit").toString(), "null") ? "---" : ing.get("unit").toString());
        Unit unit = unitRepository.findBy(Example.of(exampleUnit), FluentQuery.FetchableFluentQuery::first).orElse(null);
        if (unit == null) {
            unit = unitRepository.save(exampleUnit);
        }
        receipeIngredientRepository.save(new ReceipeIngredient(recette, ingredient, unit, ((BigDecimal) ing.get("quantity")).doubleValue()));
    }
}

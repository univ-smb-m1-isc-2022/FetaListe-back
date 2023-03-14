package fetalist.demo.service;

import fetalist.demo.model.*;
import fetalist.demo.repository.*;
import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ReceipeServiceImpl implements ReceipeService{
    private ReceipeRepository receipeRepository;
    private CategoryRepository categoryRepository;
    private InstructionRepository instructionRepository;
    private IngredientRepository ingredientRepository;
    private UnitRepository unitRepository;
    private ReceipeIngredientRepository receipeIngredientRepository;

    @Override
    public Receipe createReceipe(Receipe receipe) {
        return receipeRepository.save(receipe);
    }

    @Override
    public List<Receipe> getAllReceipe() {
        return receipeRepository.findAll();
    }

    @Override
    public Receipe getReceipeById(Long id) {
        return receipeRepository.findById(id).orElse(null);
    }


    @Override
    public void fillDatabaseWithJson() {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("../../../assets/json/recettesBase.json"))
        {
            Object obj = jsonParser.parse(reader);
            JSONArray receipeList = (JSONArray) obj;
            receipeList.forEach( rec -> parseReceipeObject( (JSONObject) rec ));

        } catch (IOException | ParseException e) {
            e.printStackTrace();
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
        recette.setRating((Float) receipe.get("rating"));
        recette.setEstimatedTime((int) receipe.get("estimated_time"));

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
        Unit exampleUnit = new Unit((String) ing.get("unit"));
        Unit unit = unitRepository.findBy(Example.of(exampleUnit), FluentQuery.FetchableFluentQuery::first).orElse(null);
        if (unit == null) {
            unit = unitRepository.save(exampleUnit);
        }
        receipeIngredientRepository.save(new ReceipeIngredient(recette, ingredient, unit, (Float) ing.get("quantity")));
    }
}

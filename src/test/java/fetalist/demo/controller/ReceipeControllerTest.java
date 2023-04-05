package fetalist.demo.controller;

import fetalist.demo.bodies.CreateReceipeBody;
import fetalist.demo.model.Ingredient;
import fetalist.demo.model.ReceipeIngredient;
import fetalist.demo.model.Unit;
import fetalist.demo.repository.IngredientRepository;
import fetalist.demo.repository.UnitRepository;
import fetalist.demo.service.ReceipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class ReceipeControllerTest {

    private MockMvc mockMvc;

    private ReceipeService receipeService;

    private UnitRepository unitRepository;
    private IngredientRepository ingredientRepository;

    // Tests

    @BeforeEach
    public void setup() {
        receipeService = mock(ReceipeService.class);
        mockMvc = standaloneSetup(new ReceipeController(receipeService)).build();
        unitRepository = mock(UnitRepository.class);
        ingredientRepository = mock(IngredientRepository.class);


        // Créer et sauvegarder des ingrédients
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setName("Tomate");
        ingredientRepository.save(ingredient1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setName("Fromage");
        ingredientRepository.save(ingredient2);

        // Créer et sauvegarder des unités
        Unit unit1 = new Unit();
        unit1.setName("g");
        unitRepository.save(unit1);

        Unit unit2 = new Unit();
        unit2.setName("mL");
        unitRepository.save(unit2);
    }

    @Test
    public void testCreateReceipe() throws Exception {
        when(ingredientRepository.findBy(Example.of(Ingredient.builder().idIngredient(1L).build()), FluentQuery.FetchableFluentQuery::first)).thenReturn(Optional.of(Ingredient.builder().idIngredient(1L).name("Tomate").build()));
        when(unitRepository.findBy(Example.of(Unit.builder().idUnit(2L).build()), FluentQuery.FetchableFluentQuery::first)).thenReturn(Optional.of(Unit.builder().idUnit(2L).name("mL").build()));
        CreateReceipeBody receipeBody = CreateReceipeBody.builder()
                .name("Pizza Margherita")
                .categoryName("Italian")
                .image("testImage")
                .estimatedTime(85)
                .instructions(List.of("Faire la pate", "Mettre la sauce", "Mettre le fromage", "Faire cuire"))
                .ri(List.of(
                        ReceipeIngredient.builder()
                                .ingredient(Ingredient.builder().idIngredient(1L).build())
                                .unit(Unit.builder().idUnit(2L).build())
                                .quantity(51.4).build()))
                .build();
        when(receipeService.createReceipe(receipeBody)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/receipe/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Pizza Margherita",
                                    "categoryName": "Italian",
                                    "image": "testImage",
                                    "estimatedTime": 85,
                                    "instructions": [
                                    "Faire la pate", "Mettre la sauce", "Mettre le fromage", "Faire cuire"
                                    ],
                                    "ri": [
                                        {
                                                "ingredient": {
                                                    "idIngredient": 1
                                                },
                                                "quantity": 51.4,
                                                "unit": {
                                                    "idUnit": 2
                                                }
                                        }
                                    ]
                                }""")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}

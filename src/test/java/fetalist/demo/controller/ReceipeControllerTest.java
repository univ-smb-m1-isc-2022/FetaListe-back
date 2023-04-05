package fetalist.demo.controller;

import fetalist.demo.bodies.CreateReceipeBody;
import fetalist.demo.model.Ingredient;
import fetalist.demo.model.ReceipeIngredient;
import fetalist.demo.model.Unit;
import fetalist.demo.repository.IngredientRepository;
import fetalist.demo.repository.UnitRepository;
import fetalist.demo.service.ReceipeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReceipeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReceipeService receipeService;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private UnitRepository unitRepository;

    @BeforeAll
    public void setUp() {
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

    // Tests

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReceipeController receipeController = new ReceipeController(receipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(receipeController).build();
    }

    @Test
    public void testCreateReceipe() throws Exception {
        Ingredient i = ingredientRepository.findBy(Example.of(Ingredient.builder().name("Tomate").build()), FluentQuery.FetchableFluentQuery::first).orElseThrow();
        Unit u = unitRepository.findBy(Example.of(Unit.builder().name("mL").build()), FluentQuery.FetchableFluentQuery::first).orElseThrow();
        CreateReceipeBody receipeBody = CreateReceipeBody.builder()
                .name("Pizza Margherita")
                .categoryName("Italian")
                .image("testImage")
                .estimatedTime(85)
                .instructions(List.of("Faire la pate", "Mettre la sauce", "Mettre le fromage", "Faire cuire"))
                .ri(List.of(
                        ReceipeIngredient.builder()
                                .ingredient(i)
                                .unit(u)
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
                                                    "idIngredient":"""+ i.getIdIngredient() +"""
                                                },
                                                "quantity": 51.4,
                                                "unit": {
                                                    "idUnit":""" + u.getIdUnit() + """
                                                }
                                        }
                                    ]
                                }""")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}

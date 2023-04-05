package fetalist.demo.controller;

import fetalist.demo.bodies.CompleteReceipeResponse;
import fetalist.demo.bodies.CreateReceipeBody;
import fetalist.demo.model.*;
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
    }

    @Test
    public void testReceipeRoutes() throws Exception {
        Receipe rtype = Receipe.builder().id(1L)
                .category(Category.builder().id(1L).name("Italian").build())
                .image("testImage")
                .estimatedTime(85L)
                .name("Pizza Margherita")
                .build();
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

        when(receipeService.getAllReceipe()).thenReturn(List.of(rtype));
        mockMvc.perform(MockMvcRequestBuilders.get("/receipe/getAll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                [{
                    "id": 1,
                    "category": {
                        "id": 1,
                        "name": "Italian"
                    },
                    "name": "Pizza Margherita",
                    "image": "testImage",
                    "rating": 0.0,
                    "estimatedTime": 85
                }]
                """));

        Ingredient itype = Ingredient.builder().idIngredient(1L).name("Tomate").build();
        Unit utype = Unit.builder().idUnit(2L).name("mL").build();
        CompleteReceipeResponse crrtype = CompleteReceipeResponse.builder().receipe(rtype)
                .instructions(List.of(
                        Instruction.builder().receipe(rtype).idInstructions(1L).instruction("test").build()
                ))
                .ri(List.of(ReceipeIngredient.builder().ingredient(itype).receipe(rtype).unit(utype).quantity(51.4).build())).build();

        when(receipeService.getReceipeById(1L)).thenReturn(crrtype);
        mockMvc.perform(MockMvcRequestBuilders.get("/receipe/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                                {
                                    "receipe": {
                                        "id": 1,
                                        "name": "Pizza Margherita",
                                        "category": {
                                            "name":"Italian"
                                        },
                                        "image": "testImage",
                                        "estimatedTime": 85
                                    },
                                    "instructions": [
                                        {
                                            "instruction": "test",
                                        }
                                    ],
                                    "ri": [
                                        {
                                                "ingredient": {
                                                    "name": "Tomate"
                                                },
                                                "quantity": 51.4,
                                                "unit": {
                                                    "name": "mL"
                                                }
                                        }
                                    ]
                                }"""));
        when(receipeService.searchReceipe(null)).thenReturn(List.of(rtype));
        when(receipeService.searchReceipe("pizza")).thenReturn(List.of(rtype));
        when(receipeService.searchReceipe("tomate")).thenReturn(List.of(rtype));
        when(receipeService.searchReceipe("autreChose")).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/receipe/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                [{
                    "id": 1,
                    "category": {
                        "id": 1,
                        "name": "Italian"
                    },
                    "name": "Pizza Margherita",
                    "image": "testImage",
                    "rating": 0.0,
                    "estimatedTime": 85
                }]
                """));
        mockMvc.perform(MockMvcRequestBuilders.get("/receipe/search?name=pizza")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                [{
                    "id": 1,
                    "category": {
                        "id": 1,
                        "name": "Italian"
                    },
                    "name": "Pizza Margherita",
                    "image": "testImage",
                    "rating": 0.0,
                    "estimatedTime": 85
                }]
                """));
        mockMvc.perform(MockMvcRequestBuilders.get("/receipe/search?name=tomate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                [{
                    "id": 1,
                    "category": {
                        "id": 1,
                        "name": "Italian"
                    },
                    "name": "Pizza Margherita",
                    "image": "testImage",
                    "rating": 0.0,
                    "estimatedTime": 85
                }]
                """));
        mockMvc.perform(MockMvcRequestBuilders.get("/receipe/search?name=autreChose")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isNotFound());

    }
}

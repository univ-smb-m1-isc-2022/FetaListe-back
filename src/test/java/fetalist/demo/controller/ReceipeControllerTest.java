package fetalist.demo.controller;

import fetalist.demo.bodies.CreateReceipeBody;
import fetalist.demo.model.Ingredient;
import fetalist.demo.model.ReceipeIngredient;
import fetalist.demo.model.Unit;
import fetalist.demo.service.ReceipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
public class ReceipeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReceipeService receipeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReceipeController receipeController = new ReceipeController(receipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(receipeController).build();
    }

    @Test
    public void testCreateReceipe() throws Exception {
        CreateReceipeBody receipeBody = CreateReceipeBody.builder()
                .name("Pizza Margherita")
                .categoryName("Italian")
                .image("testImage")
                .estimatedTime(85)
                .instructions(List.of("Faire la pate", "Mettre la sauce", "Mettre le fromage", "Faire cuire"))
                .ri(List.of(
                        ReceipeIngredient.builder()
                                .ingredient(Ingredient.builder().idIngredient(7L).build())
                                .unit(Unit.builder().idUnit(5L).build())
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
                                                    "idIngredient": 7
                                                },
                                                "quantity": 51.4,
                                                "unit": {
                                                    "idUnit": 5
                                                }
                                        }
                                    ]
                                }""")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(true));
    }
}
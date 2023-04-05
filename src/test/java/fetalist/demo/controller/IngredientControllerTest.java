package fetalist.demo.controller;

import fetalist.demo.model.Ingredient;
import fetalist.demo.service.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class IngredientControllerTest {

    private MockMvc mockMvc;
    private IngredientService ingredientService;
    // Tests

    @BeforeEach
    void setup() {
        ingredientService = mock(IngredientService.class);
        mockMvc = standaloneSetup(new IngredientController(ingredientService)).build();
    }

    @Test
    void testIngredientRoutes() throws Exception {
        Ingredient itype = Ingredient.builder().idIngredient(1L).name("Banane").build();
        when(ingredientService.createIngredient("Banane")).thenReturn(itype);

        mockMvc.perform(MockMvcRequestBuilders.post("/ingredient/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ingredientName\": \"Banane\"}"))
                .andExpect(status().isOk());
        when(ingredientService.getAllIngredients()).thenReturn(List.of(itype));
        mockMvc.perform(MockMvcRequestBuilders.post("/ingredient/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"idIngredient\": 1,\"name\": \"Banane\"}]"));
        when(ingredientService.searchIngredient("Ba")).thenReturn(List.of(itype));
        mockMvc.perform(MockMvcRequestBuilders.get("/ingredient/search?name=Ba")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"idIngredient\": 1,\"name\": \"Banane\"}]"));
        when(ingredientService.searchIngredient("Tomate")).thenReturn(List.of());
        mockMvc.perform(MockMvcRequestBuilders.get("/ingredient/search?name=Tomate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

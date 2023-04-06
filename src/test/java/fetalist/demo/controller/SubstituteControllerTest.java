package fetalist.demo.controller;

import fetalist.demo.model.Ingredient;
import fetalist.demo.model.Substitute;
import fetalist.demo.service.SubstituteService;
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

class SubstituteControllerTest {

    private MockMvc mockMvc;

    private SubstituteService substituteService;
    // Tests

    @BeforeEach
    void setup() {
        substituteService = mock(SubstituteService.class);
        mockMvc = standaloneSetup(new SubstituteController(substituteService)).build();
    }

    @Test
    void testSubstituteRoutes() throws Exception {
        Ingredient itype1 = Ingredient.builder().idIngredient(1L).name("Oeufs").build();
        Ingredient itype2 = Ingredient.builder().idIngredient(2L).name("Agar Agar").build();
        Substitute stype = Substitute.builder().baseIngredient(itype1).substitute(itype2).build();
        when(substituteService.createSubstitute(1L, 2L)).thenReturn(stype);
        when(substituteService.getAllSubstitutesOf(1L)).thenReturn(List.of(stype));
        when(substituteService.removeSubstitute(1L, 2L)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/substitute/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idIngredient\": 1, \"idIngredientToSubstitute\": 2}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"baseIngredient\": {\"idIngredient\": 1, \"name\": \"Oeufs\"}, \"substitute\": {\"idIngredient\": 2, \"name\":  \"Agar Agar\"}}"));
        mockMvc.perform(MockMvcRequestBuilders.get("/substitute/getAllOf/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"baseIngredient\": {\"idIngredient\": 1, \"name\": \"Oeufs\"}, \"substitute\": {\"idIngredient\": 2, \"name\":  \"Agar Agar\"}}]"));
        mockMvc.perform(MockMvcRequestBuilders.post("/substitute/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idIngredient\": 1, \"idIngredientToSubstitute\": 2}"))
                .andExpect(status().isOk());


    }
}

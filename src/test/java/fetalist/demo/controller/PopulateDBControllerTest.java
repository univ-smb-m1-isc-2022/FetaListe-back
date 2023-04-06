package fetalist.demo.controller;

import fetalist.demo.service.PopulateDBService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class PopulateDBControllerTest {

    private MockMvc mockMvc;

    private PopulateDBService populateDBService;
    // Tests

    @BeforeEach
    void setup() {
        populateDBService = mock(PopulateDBService.class);
        mockMvc = standaloneSetup(new PopulateDBController(populateDBService)).build();
    }

    @Test
    void testPopulateRoutes() throws Exception {
        when(populateDBService.fillDatabaseWithJson()).thenReturn("OK");
        mockMvc.perform(MockMvcRequestBuilders.post("/populate/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        when(populateDBService.fillDatabaseWithJson()).thenReturn("already filled");
        mockMvc.perform(MockMvcRequestBuilders.post("/populate/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());


    }
}

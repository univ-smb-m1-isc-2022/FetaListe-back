package fetalist.demo.controller;

import fetalist.demo.model.Unit;
import fetalist.demo.service.UnitService;
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

class UnitControllerTest {

    private MockMvc mockMvc;

    private UnitService unitService;
    // Tests

    @BeforeEach
    void setup() {
        unitService = mock(UnitService.class);
        mockMvc = standaloneSetup(new UnitController(unitService)).build();
    }

    @Test
    void testUnitRoutes() throws Exception {
        when(unitService.getAllUnits()).thenReturn(List.of(Unit.builder().idUnit(1L).name("mL").build(), Unit.builder().idUnit(2L).name("g").build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/unit/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                    [
                        {
                            "idUnit": 1,
                            "name": "mL"
                        },
                        {
                            "idUnit": 2,
                            "name": "g"
                        },
                    ]
"""));
    }
}

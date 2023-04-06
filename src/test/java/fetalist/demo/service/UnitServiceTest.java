package fetalist.demo.service;

import fetalist.demo.model.Unit;
import fetalist.demo.repository.UnitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UnitServiceTest {

    private UnitServiceImpl unitService;

    private UnitRepository unitRepository;
    // Tests

    @BeforeEach
    void setup() {
        unitRepository = mock(UnitRepository.class);
        unitService = new UnitServiceImpl(unitRepository);
    }

    @Test
    void testUnitService() {
        Unit u1 = Unit.builder().idUnit(1L).name("mL").build();
        when(unitRepository.findAll()).thenReturn(List.of(u1, Unit.builder().idUnit(2L).name("g").build()));
        assertThat(unitService.getAllUnits()).isSameAs(unitRepository.findAll());
        assertThat(unitService.getAllUnits()).asList().contains(u1);
    }
}

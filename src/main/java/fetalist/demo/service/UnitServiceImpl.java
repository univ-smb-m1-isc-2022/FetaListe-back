package fetalist.demo.service;

import fetalist.demo.model.Unit;
import fetalist.demo.repository.UnitRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UnitServiceImpl implements UnitService {

    private UnitRepository unitRepository;
    @Override
    public List<Unit> getAllUnits() {
        return unitRepository.findAll();
    }
}

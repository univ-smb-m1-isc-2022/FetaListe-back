package fetalist.demo.controller;

import fetalist.demo.model.Unit;
import fetalist.demo.service.UnitService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/unit")
@AllArgsConstructor
public class UnitController {


    private UnitService unitService;

    @GetMapping("/getAll")
    public List<Unit> getAllUnits() {
        return unitService.getAllUnits();
    }
}

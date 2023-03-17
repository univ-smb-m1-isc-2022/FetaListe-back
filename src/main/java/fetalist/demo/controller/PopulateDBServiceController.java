package fetalist.demo.controller;

import fetalist.demo.model.Receipe;
import fetalist.demo.service.PopulateDBService;
import fetalist.demo.service.ReceipeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/populate")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PopulateDBServiceController {
    private PopulateDBService populateDBService;

    @PostMapping("/")
    public void fillDatabaseWithJson() {
        populateDBService.fillDatabaseWithJson();
    }
}

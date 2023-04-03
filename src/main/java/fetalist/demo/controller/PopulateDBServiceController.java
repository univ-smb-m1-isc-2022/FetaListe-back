package fetalist.demo.controller;

import fetalist.demo.service.PopulateDBService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

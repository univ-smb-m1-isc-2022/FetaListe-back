package fetalist.demo.controller;

import fetalist.demo.service.PopulateDBService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/populate")
@AllArgsConstructor
public class PopulateDBController {
    private PopulateDBService populateDBService;

    @PostMapping("/")
    public ResponseEntity<String> fillDatabaseWithJson() {
        String s = populateDBService.fillDatabaseWithJson();
        return ResponseEntity.status(Objects.equals(s, "OK") ? 200 : 400).body(s);
    }
}

package fetalist.demo.controller;

import fetalist.demo.model.Token;
import fetalist.demo.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UsersController {


    private UsersService usersService;

    @PostMapping("/register")
    public Token registerUser(String provider, String name, String password, String phoneNumber, String mail) {
        return usersService.registerUser(provider, name, password, phoneNumber, mail);
    }

    @PostMapping("/login")
    public Token loginUser(String name, String password) {
        return usersService.loginUser(name, password);
    }

    @DeleteMapping("/remove")
    public boolean deleteUser(Token t, String password) {
        return usersService.deleteUser(t, password);
    }

}

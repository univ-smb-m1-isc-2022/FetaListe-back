package fetalist.demo.controller;

import fetalist.demo.model.Receipe;
import fetalist.demo.model.Token;
import fetalist.demo.model.Users;
import fetalist.demo.service.ReceipeService;
import fetalist.demo.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UsersController {


    private UsersService usersService;

    @PostMapping("/register")
    public Users registerUser(String provider, String name, String password, String phoneNumber) {
        return usersService.registerUser(provider, name, password, phoneNumber);
    }

    @PostMapping("/login")
    public Users loginUser(String name, String password) {
        return usersService.loginUser(name, password);
    }

    @DeleteMapping("/remove")
    public boolean deleteUser(Token t, String password) {
        return usersService.deleteUser(t, password);
    }

}

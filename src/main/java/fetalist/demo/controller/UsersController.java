package fetalist.demo.controller;

import fetalist.demo.bodies.UserLoginBody;
import fetalist.demo.bodies.UserRegisterBody;
import fetalist.demo.bodies.UserRemoveBody;
import fetalist.demo.model.Token;
import fetalist.demo.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UsersController {


    private UsersService usersService;

    @PostMapping("/register")
    public ResponseEntity<Token> registerUser(@RequestBody UserRegisterBody body) {
        Token t = usersService.registerUser(body.getProvider(), body.getName(), body.getPassword(), body.getPhoneNumber(), body.getMail());
        return t == null ? new ResponseEntity<>(HttpStatusCode.valueOf(400)) : ResponseEntity.ok(t);
    }

    @PostMapping("/login")
    public ResponseEntity<Token> loginUser(@RequestBody UserLoginBody body) {
        Token t = usersService.loginUser(body.getName(), body.getPassword());
        return t == null ? (ResponseEntity<Token>) ResponseEntity.badRequest() : ResponseEntity.ok(t);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Boolean> deleteUser(@RequestBody UserRemoveBody body) {
        return usersService.deleteUser(body.getToken(), body.getPassword()) ? (ResponseEntity<Boolean>) ResponseEntity.badRequest() : ResponseEntity.ok(true);
    }

}

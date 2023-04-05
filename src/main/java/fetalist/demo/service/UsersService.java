package fetalist.demo.service;

import fetalist.demo.bodies.BasicUser;
import fetalist.demo.model.Token;

import java.util.List;

public interface UsersService {
    Token registerUser(String provider, String name, String password, String phoneNumber, String mail);

    Token loginUser(String name, String password);

    boolean deleteUser(Token t, String password);

    List<BasicUser> getAllBasicUsers();
}

package fetalist.demo.service;

import fetalist.demo.model.Ingredient;
import fetalist.demo.model.Receipe;
import fetalist.demo.model.Token;
import fetalist.demo.model.Users;

import java.util.List;

public interface UsersService {
    Users registerUser(String provider, String name, String password, String phoneNumber);

    Users loginUser(String name, String password);

    boolean deleteUser(Token t, String password);
}

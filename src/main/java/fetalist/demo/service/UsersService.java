package fetalist.demo.service;

import fetalist.demo.model.Token;

public interface UsersService {
    Token registerUser(String provider, String name, String password, String phoneNumber, String mail);

    Token loginUser(String name, String password);

    boolean deleteUser(Token t, String password);
}

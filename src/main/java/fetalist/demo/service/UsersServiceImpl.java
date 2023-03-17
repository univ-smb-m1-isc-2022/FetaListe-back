package fetalist.demo.service;

import fetalist.demo.model.Receipe;
import fetalist.demo.model.Token;
import fetalist.demo.model.Users;
import fetalist.demo.repository.ReceipeRepository;
import fetalist.demo.repository.TokenRepository;
import fetalist.demo.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService{
    private UsersRepository usersRepository;
    private TokenRepository tokenRepository;

    @Override
    public Users registerUser(String provider, String name, String password, String phoneNumber) {
        return null;
    }

    @Override
    public Users loginUser(String name, String password) {
        Users u = usersRepository.findBy(Example.of(new Users(name, password)), FluentQuery.FetchableFluentQuery::first).orElse(null);
        if (u == null) return null;
        Token t = tokenRepository.findBy(Example.of(new Token(u)), FluentQuery.FetchableFluentQuery::first).orElse(null);
        return null;
    }

    @Override
    public boolean deleteUser(Token t, String password) {
        return false;
    }
}

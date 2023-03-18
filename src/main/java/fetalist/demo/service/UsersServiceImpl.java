package fetalist.demo.service;

import fetalist.demo.model.Token;
import fetalist.demo.model.Users;
import fetalist.demo.repository.TokenRepository;
import fetalist.demo.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService{
    private UsersRepository usersRepository;
    private TokenRepository tokenRepository;

    @Override
    public Token registerUser(String provider, String name, String password, String phoneNumber, String mail) {
        if (!Objects.equals(provider, "LOCAL")) return null; // TODO Tant qu'on a pas l'oauth google, ca sert à rien
        Users u = usersRepository.save(new Users(name, password, phoneNumber, mail));
        return tokenRepository.save(Token.generateToken(u, provider));
    }

    @Override
    public Token loginUser(String name, String password) {
        Users u = usersRepository.findBy(Example.of(new Users(name, password)), FluentQuery.FetchableFluentQuery::first).orElse(null);
        if (u == null) return null;
        Token t = tokenRepository.findBy(Example.of(new Token(u)), FluentQuery.FetchableFluentQuery::first).orElse(null);
        if (t == null) return null;
        t.refreshAccessValidUntil();
        return t;
    }

    @Override
    public boolean deleteUser(Token t, String password) {
        Token tDb = tokenRepository.findBy(Example.of(t), FluentQuery.FetchableFluentQuery::first).orElse(null);
        if (tDb == null || !Objects.equals(tDb.getUsers().getPassword(), password)) return false;
        tokenRepository.delete(tDb);
        usersRepository.delete(tDb.getUsers());
        return true;
    }
}

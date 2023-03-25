package fetalist.demo.service;

import fetalist.demo.model.Token;
import fetalist.demo.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {
    private TokenRepository tokenRepository;


    @Override
    public Token checkToken(String token) {
        Token t = tokenRepository.findBy(Example.of(new Token(token)), FluentQuery.FetchableFluentQuery::first).orElse(null);
        return t == null || t.getAccessValidUntil().toLocalDate().isBefore(LocalDate.now()) ? null : t;
    }
}

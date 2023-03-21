package fetalist.demo.service;

import fetalist.demo.model.Favorite;
import fetalist.demo.model.Receipe;
import fetalist.demo.model.Token;
import fetalist.demo.repository.FavoriteRepository;
import fetalist.demo.repository.ReceipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteRepository favoriteRepository;
    private ReceipeRepository receipeRepository;
    @Override
    public Favorite addFavorite(long idReceipe, Token t) {
        Receipe r = receipeRepository.getReferenceById(idReceipe);
        Favorite example = new Favorite(t.getUsers(), r);
        Favorite f = favoriteRepository.findBy(Example.of(example), FluentQuery.FetchableFluentQuery::first).orElse(null);
        if (f == null) return favoriteRepository.save(example);
        return null;
    }

    @Override
    public List<Favorite> getAll(Token t) {
        return favoriteRepository.findAll(Example.of(new Favorite(t.getUsers())));
    }

    @Override
    public boolean deleteFavorite(Favorite favorite) {
        Favorite f = favoriteRepository.findBy(Example.of(favorite), FluentQuery.FetchableFluentQuery::first).orElse(null);
        if (f == null) return false;
        favoriteRepository.delete(favorite);
        return true;
    }
}

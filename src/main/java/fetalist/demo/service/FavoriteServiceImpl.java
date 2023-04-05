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
    public boolean addFavorite(long idReceipe, Token t) {
        Receipe r = receipeRepository.findById(idReceipe).orElse(null);
        if (r == null) return false;
        Favorite example = new Favorite(t.getUsers(), r);
        Favorite f = favoriteRepository.findBy(Example.of(example), FluentQuery.FetchableFluentQuery::first).orElse(null);
        if (f == null) {
            favoriteRepository.save(example);
            return true;
        }
        return false;
    }

    @Override
    public List<Favorite> getAll(Token t) {
        return favoriteRepository.findAll(Example.of(new Favorite(t.getUsers())));
    }

    @Override
    public boolean deleteFavorite(Token t, long idReceipe) {
        Receipe r = receipeRepository.findById(idReceipe).orElse(null);
        if (r == null) return false;
        Favorite f = favoriteRepository.findBy(Example.of(Favorite.builder().users(t.getUsers()).receipe(r).build()), FluentQuery.FetchableFluentQuery::first).orElse(null);
        if (f == null) return false;
        favoriteRepository.delete(f);
        return true;
    }

    @Override
    public boolean isAFavoriteOf(Token t, Long idReceipe) {
        Receipe r = receipeRepository.findById(idReceipe).orElse(null);
        return r != null && favoriteRepository.exists(Example.of(Favorite.builder().receipe(r).users(t.getUsers()).build()));
    }
}

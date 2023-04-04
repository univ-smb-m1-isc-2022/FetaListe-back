package fetalist.demo.service;

import fetalist.demo.model.Favorite;
import fetalist.demo.model.Token;

import java.util.List;

public interface FavoriteService {

    boolean addFavorite(long idReceipe, Token t);

    List<Favorite> getAll(Token t);

    boolean deleteFavorite(Token t, long idReceipe);

    boolean isAFavoriteOf(Token t, Long idReceipe);
}

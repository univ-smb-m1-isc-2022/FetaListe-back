package fetalist.demo.controller;

import fetalist.demo.bodies.FavoriteAddBody;
import fetalist.demo.bodies.FavoriteCheckBody;
import fetalist.demo.bodies.FavoriteListBody;
import fetalist.demo.bodies.FavoriteRemoveBody;
import fetalist.demo.model.Favorite;
import fetalist.demo.model.Token;
import fetalist.demo.service.FavoriteService;
import fetalist.demo.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/favorite")
@AllArgsConstructor
public class FavoriteController {


    private TokenService tokenService;
    private FavoriteService favoriteService;

    @PostMapping("/add")
    public ResponseEntity<Boolean> addFavorite(@RequestBody FavoriteAddBody body) {
        Token t = tokenService.checkToken(body.getToken());
        if (t == null) return new ResponseEntity<>(HttpStatusCode.valueOf(403)); // Tout token invalide ou expiré est interdit
        Favorite fav = favoriteService.addFavorite(body.getIdReceipe(), t);
        return fav == null ? new ResponseEntity<>(HttpStatusCode.valueOf(400)) : ResponseEntity.ok(true);
    }

    @PostMapping("/getAll")
    public ResponseEntity<List<Favorite>> listFavoriteOfUser(@RequestBody FavoriteListBody body) {
        Token t = tokenService.checkToken(body.getToken());
        if (t == null) return new ResponseEntity<>(HttpStatusCode.valueOf(403)); // Tout token invalide ou expiré est interdit
        return ResponseEntity.ok(favoriteService.getAll(t));
    }

    @PostMapping("/isFavorite")
    public ResponseEntity<Boolean> listFavoriteOfUser(@RequestBody FavoriteCheckBody body) {
        Token t = tokenService.checkToken(body.getToken());
        if (t == null) return new ResponseEntity<>(HttpStatusCode.valueOf(403)); // Tout token invalide ou expiré est interdit
        return ResponseEntity.ok(favoriteService.isAFavoriteOf(t, body.getIdReceipe()));
    }

    @PostMapping("/remove")
    public ResponseEntity<Boolean> deleteFavorite(@RequestBody FavoriteRemoveBody body) {
        Token t = tokenService.checkToken(body.getToken());
        if (t == null) return new ResponseEntity<>(HttpStatusCode.valueOf(403)); // Tout token invalide ou expiré est interdit
        return favoriteService.deleteFavorite(t, body.getIdReceipe()) ? ResponseEntity.ok(true) : new ResponseEntity<>(HttpStatusCode.valueOf(400));
    }
}

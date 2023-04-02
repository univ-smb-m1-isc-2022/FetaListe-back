package fetalist.demo.controller;

import fetalist.demo.bodies.CreateShoppingListBody;
import fetalist.demo.bodies.DeleteShoppingListBody;
import fetalist.demo.bodies.EditShoppingListBody;
import fetalist.demo.bodies.GetShoppingListBody;
import fetalist.demo.model.ShoppingList;
import fetalist.demo.model.Token;
import fetalist.demo.service.ShoppingListService;
import fetalist.demo.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ShoppingListController {

    private TokenService tokenService;
    private ShoppingListService shoppingListService;


    @PostMapping("/create")
    public ResponseEntity<ShoppingList> createShoppingList(@RequestBody CreateShoppingListBody body) {
        Token t = tokenService.checkToken(body.getToken());
        if (t == null) return new ResponseEntity<>(HttpStatusCode.valueOf(403)); // Tout token invalide ou expiré est interdit
        ShoppingList sl = shoppingListService.createList(t);
        return sl == null ? new ResponseEntity<>(HttpStatusCode.valueOf(400)) : ResponseEntity.ok(sl);
    }

    @PostMapping("/getList")
    public ResponseEntity<List<ShoppingList>> getListOfUser(@RequestBody GetShoppingListBody body) {
        // Si idList précisé : renvoyer que celle ci, sinon toutes les renvoyer
        Token t = tokenService.checkToken(body.getToken());
        if (t == null) return new ResponseEntity<>(HttpStatusCode.valueOf(403)); // Tout token invalide ou expiré est interdit
        List<ShoppingList> sl = shoppingListService.getListOf(t, body.getIdShoppingList() == 0 ? -1 : body.getIdShoppingList());
        return sl == null ? new ResponseEntity<>(HttpStatusCode.valueOf(400)) : ResponseEntity.ok(sl);
    }

    @PostMapping("/edit")
    public ResponseEntity<ShoppingList> editList(@RequestBody EditShoppingListBody body) {
        // Ajoute / supprime une liste de recettes de la liste de courses
        Token t = tokenService.checkToken(body.getToken());
        if (t == null) return new ResponseEntity<>(HttpStatusCode.valueOf(403)); // Tout token invalide ou expiré est interdit
        ShoppingList editedSl = body.isAdd()
                ? shoppingListService.addToList(t, body.getIdShoppingList(), body.getIdsReceipes(), body.getEditedIngredients())
                : shoppingListService.removeFromList(t, body.getIdShoppingList(), body.getIdsReceipes(), body.getEditedIngredients());
        return editedSl == null ? new ResponseEntity<>(HttpStatusCode.valueOf(400)) : ResponseEntity.ok(editedSl);
    }

    @PostMapping("/remove")
    public ResponseEntity<Boolean> deleteShoppingList(@RequestBody DeleteShoppingListBody body) {
        Token t = tokenService.checkToken(body.getToken());
        if (t == null) return new ResponseEntity<>(HttpStatusCode.valueOf(403)); // Tout token invalide ou expiré est interdit
        return shoppingListService.deleteList(t, body.getIdShoppingList()) ? ResponseEntity.ok(true) : new ResponseEntity<>(HttpStatusCode.valueOf(400)) ;
    }
}

package fetalist.demo.controller;

import fetalist.demo.bodies.AddFriendBody;
import fetalist.demo.bodies.ListFriendBody;
import fetalist.demo.bodies.RemoveFriendBody;
import fetalist.demo.bodies.RequestResponseFriendBody;
import fetalist.demo.model.Friend;
import fetalist.demo.model.Token;
import fetalist.demo.service.FriendService;
import fetalist.demo.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
@AllArgsConstructor
public class FriendController {

    private TokenService tokenService;
    private FriendService friendService;

    @PostMapping("/add")
    public ResponseEntity<Boolean> addFriend(@RequestBody AddFriendBody body) {
        Token t = tokenService.checkToken(body.getToken());
        if (t == null) return new ResponseEntity<>(HttpStatusCode.valueOf(403)); // Tout token invalide ou expiré est interdit
        return friendService.requestFriend(body.getIdUserToAdd(), t) ? ResponseEntity.ok(true) : new ResponseEntity<>(HttpStatusCode.valueOf(400));
    }
    @PostMapping("/list")
    public ResponseEntity<List<Friend>> listFriend(@RequestBody ListFriendBody body) {
        Token t = tokenService.checkToken(body.getToken());
        if (t == null) return new ResponseEntity<>(HttpStatusCode.valueOf(403)); // Tout token invalide ou expiré est interdit
        return ResponseEntity.ok(friendService.getAllFriend(t));
    }
    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateFriend(@RequestBody RequestResponseFriendBody body) {
        Token t = tokenService.checkToken(body.getToken());
        if (t == null) return new ResponseEntity<>(HttpStatusCode.valueOf(403)); // Tout token invalide ou expiré est interdit
        return friendService.respondFriendRequest(t, body.getIdFriendInviteToRespond(), true) ? ResponseEntity.ok(true) : new ResponseEntity<>(HttpStatusCode.valueOf(400));
    }
    @PostMapping("/refuse")
    public ResponseEntity<Boolean> refuseFriend(@RequestBody RequestResponseFriendBody body) {
        Token t = tokenService.checkToken(body.getToken());
        if (t == null) return new ResponseEntity<>(HttpStatusCode.valueOf(403)); // Tout token invalide ou expiré est interdit
        return friendService.respondFriendRequest(t, body.getIdFriendInviteToRespond(), false) ? ResponseEntity.ok(true) : new ResponseEntity<>(HttpStatusCode.valueOf(400));
    }
    @PostMapping("/remove")
    public ResponseEntity<Boolean> deleteFriend(@RequestBody RemoveFriendBody body) {
        Token t = tokenService.checkToken(body.getToken());
        if (t == null) return new ResponseEntity<>(HttpStatusCode.valueOf(403)); // Tout token invalide ou expiré est interdit
        return friendService.deleteFriend(t, body.getIdFriendToRemove()) ? ResponseEntity.ok(true) : new ResponseEntity<>(HttpStatusCode.valueOf(400));
    }
}

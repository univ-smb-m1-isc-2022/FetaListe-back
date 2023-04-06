package fetalist.demo.controller;

import fetalist.demo.bodies.ListShareSMSBody;
import fetalist.demo.bodies.ResponseString;
import fetalist.demo.model.Token;
import fetalist.demo.service.SMSService;
import fetalist.demo.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/msg")
@AllArgsConstructor
public class SMSController {
    private SMSService smsService;
    private TokenService tokenService;

    @PostMapping("/send")
    public ResponseEntity<ResponseString> shareSList(@RequestBody ListShareSMSBody body) {
        Token t = tokenService.checkToken(body.getToken());
        if (t == null) return new ResponseEntity<>(HttpStatusCode.valueOf(403)); // Tout token invalide ou expiré est interdit

        String s = smsService.shareSList(t, body.getIdUserToSend(), body.getIdSLToShare());
        return ResponseEntity.status(Objects.equals(s, "OK") ? 200 : 400).body(new ResponseString(s));
    }

    @PostMapping("/share")
    public ResponseEntity<ResponseString> shareSListInApp(@RequestBody ListShareSMSBody body) {
        Token t = tokenService.checkToken(body.getToken());
        if (t == null) return new ResponseEntity<>(HttpStatusCode.valueOf(403)); // Tout token invalide ou expiré est interdit

        String s = smsService.shareSListInApp(t, body.getIdUserToSend(), body.getIdSLToShare());
        return ResponseEntity.status(Objects.equals(s, "OK") ? 200 : 400).body(new ResponseString(s));
    }
}

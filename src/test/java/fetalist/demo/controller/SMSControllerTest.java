package fetalist.demo.controller;

import fetalist.demo.model.Token;
import fetalist.demo.model.Users;
import fetalist.demo.service.SMSService;
import fetalist.demo.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class SMSControllerTest {

    private MockMvc mockMvc;

    private SMSService smsService;
    private TokenService tokenService;
    // Tests

    @BeforeEach
    void setup() {
        smsService = mock(SMSService.class);
        tokenService = mock(TokenService.class);
        mockMvc = standaloneSetup(new SMSController(smsService, tokenService)).build();
    }

    @Test
    void testMsgRoutes() throws Exception {
        Users utype = Users.builder().idUser(1L).name("test").mail("testMail").phone("testPhone").password("testPwd").registerDate(new Date()).build();
        Token ttype = Token.builder().accessToken("test1234").idToken(1L).users(utype).refreshToken("tokenrefresh").refreshValidToken("testvt").accessValidUntil(new java.sql.Date(Instant.now().getNano())).build();

        when(tokenService.checkToken(ttype.getAccessToken())).thenReturn(ttype);
        when(smsService.shareSListInApp(ttype, 2L, 1L)).thenReturn("OK");
        mockMvc.perform(MockMvcRequestBuilders.post("/msg/share")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \"test1234\",\"idSLToShare\": 1, \"idUserToSend\": 2}"))
                .andExpect(status().isOk());
        when(smsService.shareSList(ttype, 2L, 1L)).thenReturn("OK");
        mockMvc.perform(MockMvcRequestBuilders.post("/msg/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \"test1234\",\"idSLToShare\": 1, \"idUserToSend\": 2}"))
                .andExpect(status().isOk());


    }
}

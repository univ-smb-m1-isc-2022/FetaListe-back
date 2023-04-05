package fetalist.demo.controller;

import fetalist.demo.bodies.BasicUser;
import fetalist.demo.model.Token;
import fetalist.demo.model.Users;
import fetalist.demo.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class UsersControllerTest {

    private MockMvc mockMvc;

    private UsersService usersService;
    // Tests

    @BeforeEach
    void setup() {
        usersService = mock(UsersService.class);
        mockMvc = standaloneSetup(new UsersController(usersService)).build();
    }

    @Test
    void testUserRoutes() throws Exception {
        Users utype = Users.builder().idUser(1L).name("test").mail("testMail").phone("testPhone").password("testPwd").registerDate(new Date()).build();
        Token ttype = Token.builder().accessToken("test1234").idToken(1L).users(utype).refreshToken("tokenrefresh").refreshValidToken("testvt").accessValidUntil(new java.sql.Date(Instant.now().getNano())).build();


        when(usersService.registerUser("LOCAL", utype.getName(), utype.getPassword(), utype.getPhone(), utype.getMail())).thenReturn(ttype);
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"provider\": \"LOCAL\",\"name\": \"test\",\"password\": \"testPwd\",\"phoneNumber\": \"testPhone\",\"mail\": \"testMail\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "users": {
                                "name": "test",
                                "mail": "testMail",
                                "phone": "testPhone",
                            },
                            "accessToken": "test1234",
                            "refreshToken": "tokenrefresh",
                            "refreshValidToken": "testvt"
                        }"""));
        when(usersService.loginUser("test", "testPwd")).thenReturn(ttype);
        mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"test\", \"password\": \"testPwd\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "users": {
                                "name": "test",
                                "mail": "testMail",
                                "phone": "testPhone",
                            },
                            "accessToken": "test1234",
                            "refreshToken": "tokenrefresh",
                            "refreshValidToken": "testvt"
                        }"""));
        when(usersService.loginUser("test", "baba")).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{'name': 'test', 'password': 'baba'}"))
                .andExpect(status().isBadRequest());
        when(usersService.deleteUser(ttype.getAccessToken(), "baba")).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.post("/user/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{'token': 'test1234', 'password': 'baba'}"))
                .andExpect(status().isBadRequest());
        when(usersService.deleteUser(ttype.getAccessToken(), "testPwd")).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/user/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \"test1234\", \"password\": \"testPwd\"}"))
                .andExpect(status().isOk());
        when(usersService.getAllBasicUsers()).thenReturn(List.of(BasicUser.builder().name(utype.getName()).id(utype.getIdUser()).build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/user/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\": 1,\"name\": \"test\"}]"));

    }
}

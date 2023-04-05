package fetalist.demo.controller;

import fetalist.demo.model.Friend;
import fetalist.demo.model.Token;
import fetalist.demo.model.Users;
import fetalist.demo.service.FriendService;
import fetalist.demo.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class FriendControllerTest {

    private MockMvc mockMvc;

    private FriendService friendService;
    private TokenService tokenService;
    // Tests

    @BeforeEach
    void setup() {
        friendService = mock(FriendService.class);
        tokenService = mock(TokenService.class);
        mockMvc = standaloneSetup(new FriendController(tokenService, friendService)).build();
    }

    @Test
    void testFriendRoutes() throws Exception {
        Users utype1 = Users.builder().idUser(1L).name("test1").build();
        Users utype2 = Users.builder().idUser(2L).name("test2").build();
        Token ttype1 = Token.builder().accessToken("t1").users(utype1).build();
        Token ttype2 = Token.builder().accessToken("t2").users(utype2).build();


        when(tokenService.checkToken("t1")).thenReturn(ttype1);
        when(tokenService.checkToken("t2")).thenReturn(ttype2);
        when(friendService.requestFriend(utype1.getIdUser(), ttype1)).thenReturn(false);
        when(friendService.requestFriend(utype1.getIdUser(), ttype2)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/friend/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \"t1\", \"idUserToAdd\": 1}"))
                .andExpect(status().isBadRequest());
        mockMvc.perform(MockMvcRequestBuilders.post("/friend/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \"t2\", \"idUserToAdd\": 1}"))
                .andExpect(status().isOk());
        when(friendService.respondFriendRequest(ttype1, utype2.getIdUser(), true)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/friend/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \"t1\", \"idFriendInviteToRespond\": 2}"))
                .andExpect(status().isOk());
        when(friendService.respondFriendRequest(ttype1, utype2.getIdUser(), false)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/friend/refuse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \"t1\", \"idFriendInviteToRespond\": 2}"))
                .andExpect(status().isOk());
        when(friendService.getAllFriend(ttype1)).thenReturn(List.of(Friend.builder().user1(utype1).user2(utype2).status("ACCEPTED").build()));
        mockMvc.perform(MockMvcRequestBuilders.post("/friend/getAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \"t1\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                          {
                            "status": 'ACCEPTED',
                            "user1": {
                              "name": "test1"
                            },
                            "user2": {
                              "name": "test2"
                            }
                          }
                        ]
                        """));
        when(friendService.deleteFriend(ttype1, 2)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/friend/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \"t1\", \"idFriendToRemove\": 2}"))
                .andExpect(status().isOk());
    }
}

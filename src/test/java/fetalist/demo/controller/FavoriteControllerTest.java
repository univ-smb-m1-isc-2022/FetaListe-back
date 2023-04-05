package fetalist.demo.controller;

import fetalist.demo.model.Favorite;
import fetalist.demo.model.Receipe;
import fetalist.demo.model.Token;
import fetalist.demo.model.Users;
import fetalist.demo.service.FavoriteService;
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

class FavoriteControllerTest {

    private MockMvc mockMvc;

    private FavoriteService favoriteService;
    private TokenService tokenService;
    // Tests

    @BeforeEach
    void setup() {
        favoriteService = mock(FavoriteService.class);
        tokenService = mock(TokenService.class);
        mockMvc = standaloneSetup(new FavoriteController(tokenService, favoriteService)).build();
    }

    @Test
    void testFavoriteRoutes() throws Exception {
        Users utype = Users.builder().idUser(1L).name("test").build();
        Token ttype = Token.builder().accessToken("t1").users(utype).build();
        Receipe rtype = Receipe.builder().id(1L).name("Pizza").build();
        Favorite ftype = Favorite.builder().receipe(rtype).users(utype).build();

        when(tokenService.checkToken(ttype.getAccessToken())).thenReturn(ttype);
        when(favoriteService.addFavorite(1L, ttype)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/favorite/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \"t1\", \"idReceipe\": 1}"))
                .andExpect(status().isOk());
        when(favoriteService.getAll(ttype)).thenReturn(List.of(ftype));
        mockMvc.perform(MockMvcRequestBuilders.post("/favorite/getAll")
                        .contentType(MediaType.APPLICATION_JSON)
                .content("{\"token\": \"t1\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"users\": {\"name\": \"test\"}, \"receipe\": {\"name\": \"Pizza\"}}]"));
        when(favoriteService.isAFavoriteOf(ttype, 1L)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/favorite/isFavorite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \"t1\", \"idReceipe\": 1}"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
        when(favoriteService.isAFavoriteOf(ttype, 2L)).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.post("/favorite/isFavorite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \"t1\", \"idReceipe\": 2}"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
        when(favoriteService.deleteFavorite(ttype, 1L)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/favorite/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \"t1\", \"idReceipe\": 1}"))
                .andExpect(status().isOk());
    }
}

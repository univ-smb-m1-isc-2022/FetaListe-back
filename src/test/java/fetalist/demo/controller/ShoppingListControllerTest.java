package fetalist.demo.controller;

import fetalist.demo.model.ShoppingList;
import fetalist.demo.model.Token;
import fetalist.demo.model.Users;
import fetalist.demo.service.ShoppingListService;
import fetalist.demo.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class ShoppingListControllerTest {

    private MockMvc mockMvc;

    private ShoppingListService shoppingListService;
    private TokenService tokenService;
    // Tests

    @BeforeEach
    void setup() {
        shoppingListService = mock(ShoppingListService.class);
        tokenService = mock(TokenService.class);
        mockMvc = standaloneSetup(new ShoppingListController(tokenService, shoppingListService)).build();
    }

    @Test
    void testShoppingRoutes() throws Exception {
        Users utype = Users.builder().name("test").idUser(1L).build();
        Token ttype = Token.generateToken(utype, "LOCAL");
        Date d = new Date();
        ShoppingList stype = ShoppingList.builder().maxBuyDate(d.getTime()).owner(utype).user(utype).id(1L).build();
        when(shoppingListService.createList(ttype, d)).thenReturn(stype);
        when(tokenService.checkToken(ttype.getAccessToken())).thenReturn(ttype);
        String rqContent = "{\"token\":\"" + ttype.getAccessToken() + "\", \"reminderDate\":\"" + d.getTime() + "\"}";
        String utypeString = "{\"name\": \"test\"}";
        System.out.println(rqContent);
        mockMvc.perform(MockMvcRequestBuilders.post("/shop/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(rqContent))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"user\": " + utypeString + ", \"owner\": " + utypeString + ", \"maxBuyDate\": " + d.getTime() + "}"));
    }
}

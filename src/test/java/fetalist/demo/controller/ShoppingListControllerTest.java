package fetalist.demo.controller;

import fetalist.demo.bodies.CompleteShoppingListResponse;
import fetalist.demo.model.*;
import fetalist.demo.service.ShoppingListService;
import fetalist.demo.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.List;

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
        Receipe rtype = Receipe.builder().id(1L)
                .category(Category.builder().id(1L).name("Italian").build())
                .image("testImage")
                .estimatedTime(85L)
                .name("Pizza Margherita")
                .build();
        when(shoppingListService.createList(ttype, d)).thenReturn(stype);
        when(tokenService.checkToken(ttype.getAccessToken())).thenReturn(ttype);
        String rqContent = "{\"token\":\"" + ttype.getAccessToken() + "\", \"reminderDate\":\"" + d.getTime() + "\"}";
        String utypeString = "{\"name\": \"test\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/shop/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(rqContent))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"user\": " + utypeString + ", \"owner\": " + utypeString + ", \"maxBuyDate\": " + d.getTime() + "}"));
        when(shoppingListService.getListOf(ttype)).thenReturn(List.of(stype));
        mockMvc.perform(MockMvcRequestBuilders.post("/shop/getAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \""+ttype.getAccessToken()+"\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"id\":1,\"user\": " + utypeString + ", \"owner\": " + utypeString + ", \"maxBuyDate\": " + d.getTime() + "}]"));
        when(shoppingListService.getListFromId(ttype, 1L)).thenReturn(CompleteShoppingListResponse.builder().sl(stype).rsl(List.of()).sli(List.of()).build());
        mockMvc.perform(MockMvcRequestBuilders.post("/shop/getById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \""+ttype.getAccessToken()+"\",\"idShoppingList\": 1}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"sl\":{\"id\":1}, \"rsl\": [], \"sli\": []}"));

        when(shoppingListService.addToList(ttype, 1L,List.of(1L),3)).thenReturn(CompleteShoppingListResponse.builder().sl(stype).rsl(List.of(ReceipeShoppingList.builder().receipe(rtype).build())).sli(List.of(ShoppingListIngredient.builder().ingredient(Ingredient.builder().name("Tomate").build()).build())).build());
        when(shoppingListService.removeFromList(ttype, 1L,List.of(1L),3)).thenReturn(CompleteShoppingListResponse.builder().sl(stype).rsl(List.of()).sli(List.of()).build());

        mockMvc.perform(MockMvcRequestBuilders.post("/shop/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \""+ttype.getAccessToken()+"\",\"idShoppingList\": 1,\"add\": true,\"idsReceipes\": [1],\"nbPersonnes\": 3}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"sl\":{\"id\":1,\"user\": " + utypeString + ", \"owner\": " + utypeString + ", \"maxBuyDate\": " + d.getTime() + "}, \"rsl\": [{\"receipe\":{\"name\": \"Pizza Margherita\" }}], \"sli\": [{\"ingredient\": {\"name\":\"Tomate\"} }]}"));
        mockMvc.perform(MockMvcRequestBuilders.post("/shop/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \""+ttype.getAccessToken()+"\",\"idShoppingList\": 1,\"add\": false,\"idsReceipes\": [1],\"nbPersonnes\": 3}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"sl\":{\"id\":1,\"user\": " + utypeString + ", \"owner\": " + utypeString + ", \"maxBuyDate\": " + d.getTime() + "}, \"rsl\": [], \"sli\": []}"));

        when(shoppingListService.deleteList(ttype, 1L)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/shop/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \""+ttype.getAccessToken()+"\",\"idShoppingList\": 1}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }
}

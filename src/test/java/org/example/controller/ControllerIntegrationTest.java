package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Item;
import org.example.repository.ShoppingListRepository;
import org.example.service.ShoppingListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StoreControllerIntegrationTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShoppingListRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        repository.findAll().forEach(item -> repository.deleteById(item.getId()));
    }

    @Test
    void addItem_ReturnCreatedStatusAndValidJson() throws Exception{

        String requestJson = "{\"name\":\"Йогурт\"}";

        mockMvc.perform(post("/api/items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Йогурт"))
                .andExpect(jsonPath("$.purchased").value(false))
                .andExpect(jsonPath("$.id").isNumber());
    }

    @Test
    void markPurchased_ToggleStatusAndReturnOk() throws Exception{

        Item item = repository.save(new Item(null, "Апельсины", false));
        Long itemId = item.getId();

        mockMvc.perform(patch("/api/items/" + itemId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Апельсины"))
                .andExpect(jsonPath("$.purchased").value(true));
    }

    @Test
    void addItem_withBlankName_ReturnBadRequest() throws Exception{
        String requestJson = "{\"name\": \" \"}";

        mockMvc.perform(post("/api/items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAll_shouldReturnEmptyListWhenNoItems() throws Exception {
        mockMvc.perform(get("/api/items")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

}
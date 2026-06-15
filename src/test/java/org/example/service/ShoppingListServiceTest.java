package org.example.service;

import org.example.model.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ShoppingListServiceTest {

    private final ShoppingListService service = new ShoppingListService();

    @Test
    void addItem_checkCorrectProperties(){
        String itemName = "Сахар";
        Item result = service.addItem(itemName);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Сахар");
        assertThat(result.isPurchased()).isFalse();
    }

    @Test
    void markPurchased_checkToggleItemStatus(){

        Item item = service.addItem("Носки");
        Long itemId = item.getId();

        assertThat(item.isPurchased()).isFalse();

        Item updatedItem =  service.markPurchased(itemId);

        assertThat(updatedItem).isNotNull();
        assertThat(updatedItem.isPurchased()).isTrue();



    }
}

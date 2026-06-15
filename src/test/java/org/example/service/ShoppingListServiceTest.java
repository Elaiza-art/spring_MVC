package org.example.service;

import org.example.model.Item;
import org.example.repository.ShoppingListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ShoppingListServiceTest {

    @Mock
    private ShoppingListRepository repository;

    @InjectMocks
    private ShoppingListService service;

    @Test
    void addItem_checkReturnSavedItem(){
        Item item = new Item(1L, "Сахар", false);
        when(repository.save(any(Item.class))).thenReturn(item);

        Item result = service.addItem("Сахар");

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Сахар");
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.isPurchased()).isFalse();

        verify(repository, times(1)).save(any(Item.class));
    }

    @Test
    void markPurchased_checkToggleItemStatus(){

        Item item = new Item(2L, "Носки", false);
        when(repository.findById(2L)).thenReturn(item);

        Item updItem = new Item(2L, "Носки", true);
        when(repository.save(any(Item.class))).thenReturn(updItem);

        Item updatedItem =  service.markPurchased(2L);

        assertThat(updatedItem).isNotNull();
        assertThat(updatedItem.isPurchased()).isTrue();
        assertThat(updatedItem.getName()).isEqualTo("Носки");

        verify(repository, times(1)).findById(2L);
        verify(repository, times(1)).save(any(Item.class));



    }
}

package org.example.service;

import org.example.model.Item;
import org.example.repository.ShoppingListRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingListService implements ShoppingServiceI {

    private final ShoppingListRepository repository;

    public ShoppingListService(ShoppingListRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Item> getAll(){
        return repository.findAll();
    }

    @Override
    public Item addItem(String name){
        Item item = new Item(null, name, false);
        return repository.save(item);
    }

    @Override
    public void delete(Long id){
        repository.deleteById(id);
    }

    @Override
    public Item markPurchased(Long id) {
        Item item = repository.findById(id);
        if (item != null) {
            item.setPurchased(!item.isPurchased());
            return repository.save(item);
        }
        return null;
    }

}


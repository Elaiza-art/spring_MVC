package org.example.service;

import org.example.model.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ShoppingListService implements ShoppingServiceI {

    private final AtomicLong ITEM_ID_HOLDER = new AtomicLong(0);
    private final Map<Long, Item> STORE_MAP = new ConcurrentHashMap<>();

    @Override
    public List<Item> getAll(){
        return new ArrayList<>(STORE_MAP.values());
    }

    @Override
    public Item addItem(String name){
        Item item = new Item(ITEM_ID_HOLDER.incrementAndGet(), name, false);
        STORE_MAP.put(item.getId(), item);
        return item;
    }

    @Override
    public void delete(Long id){
        STORE_MAP.remove(id);
    }

    @Override
    public Item markPurchased(Long id) {
        Item item = STORE_MAP.get(id);
        if (item != null) {
            item.setPurchased(!item.isPurchased());
            return item;
        }
        return null;
    }

}


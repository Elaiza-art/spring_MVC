package org.example.repository;

import org.example.model.Item;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ShoppingListRepository {
    private final Map<Long, Item> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Item> findAll() {
        return new ArrayList<>(storage.values());
    }

    public Item save(Item item) {
        if (item.getId() == null) {
            item.setId(idGenerator.getAndIncrement());
        }
        storage.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return storage.get(id);
    }

    public void deleteById(Long id) {
        storage.remove(id);
    }
}

package org.example.repository;

import org.example.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public interface ShoppingListRepository extends JpaRepository<Item, Long> {
    // - findAll()
    // - save(Item)
    // - findById(Long)
    // - deleteById(Long)
}

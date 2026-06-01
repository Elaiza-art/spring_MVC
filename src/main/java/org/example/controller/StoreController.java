package org.example.controller;

import org.example.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.service.ShoppingListService;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class StoreController {

    private final ShoppingListService service;

    @Autowired
    public StoreController(ShoppingListService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<Item> addItem(@RequestBody Item item){
        if (item.getName() == null || item.getName().isBlank()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body((service.addItem(item.getName())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Item> markPurchased(@PathVariable Long id){
        Item item = service.markPurchased(id);
        if (item == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(item);
    }

}

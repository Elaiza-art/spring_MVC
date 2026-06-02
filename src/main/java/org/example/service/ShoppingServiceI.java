package org.example.service;

import org.example.model.Item;

import java.util.List;

public interface ShoppingServiceI {

    /**
     * Возвращает список всех имеющихся покупок
     * @return список покупок
     */
    List<Item> getAll();

    /**
     * Создает новый элемент
     * @param name - название элемента
     * @return - объект элемента с заданным ID
     */
    Item addItem(String name);
    /**
     * Отмечает элемент купленным
     * @param id - ID элемента
     * @return - объект элемента с заданным ID
     */
    Item markPurchased(Long id);

    /**
     * Удаляет элемент с заданным ID
     * @param id - id элемента, которого нужно удалить
     */
    void delete(Long id);

}

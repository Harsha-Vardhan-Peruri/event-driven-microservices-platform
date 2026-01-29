package com.vardhan.inventory_service.service;

import com.vardhan.inventory_service.entity.Inventory;
import com.vardhan.inventory_service.repository.InventoryRepository;
import org.springframework.stereotype.Component;

@Component
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public synchronized void reduceStock(String productId, int quantity){

        Inventory inventory = inventoryRepository.findById(productId)
                .orElseGet(() -> {
                    Inventory inv = new Inventory();
                    inv.setProductId(productId);
                    inv.setStock(10);
                    return inv;
                });

        inventory.setStock(inventory.getStock() - quantity);
        inventoryRepository.save(inventory);

        System.out.println("Stock updated for " + productId + "by thread" + Thread.currentThread().getName() + " | Remaining stock: " + inventory.getStock());
    }
}

package com.djoumatch.inventoryservice.service;

import com.djoumatch.inventoryservice.dto.InventoryRequest;
import com.djoumatch.inventoryservice.model.Inventory;
import com.djoumatch.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode){
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode)
                .orElseThrow(
                        () -> new RuntimeException(String.format("Cant find a product with skuCode : %s", skuCode))
                );

        return inventory.getQuantity() > 0 ? true : false;

    }

    public void createInventory(InventoryRequest inventoryRequest) {
        Inventory inventory = Inventory.builder()
                .skuCode(inventoryRequest.getSkuCode())
                .quantity(inventoryRequest.getQuantity())
                .build();

        inventoryRepository.save(inventory);
    }
}

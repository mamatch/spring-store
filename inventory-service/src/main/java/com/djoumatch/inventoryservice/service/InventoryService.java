package com.djoumatch.inventoryservice.service;

import com.djoumatch.inventoryservice.dto.InventoryRequest;
import com.djoumatch.inventoryservice.dto.InventoryResponse;
import com.djoumatch.inventoryservice.model.Inventory;
import com.djoumatch.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode){
        return inventoryRepository.findBySkuCodeIn(skuCode).stream().map(inventory ->
            InventoryResponse.builder()
                    .skuCode(inventory.getSkuCode())
                    .inStock(inventory.getQuantity() > 0)
                    .build()
        ).toList();

    }

    public void createInventory(InventoryRequest inventoryRequest) {
        Inventory inventory = Inventory.builder()
                .skuCode(inventoryRequest.getSkuCode())
                .quantity(inventoryRequest.getQuantity())
                .build();

        inventoryRepository.save(inventory);
    }
}

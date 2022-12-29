package com.djoumatch.inventoryservice.controller;

import com.djoumatch.inventoryservice.dto.InventoryRequest;
import com.djoumatch.inventoryservice.dto.InventoryResponse;
import com.djoumatch.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    /**
     * Tell if there is a product in stock based on the skucode
     * Take request like "http://localhost:8082/api/inventory?sku-code=s1&sku-code=s2"
     *
     * @param skuCode the code of the product
     * @return
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam("sku-code") List<String> skuCode){
        return inventoryService.isInStock(skuCode);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createInventory(@RequestBody InventoryRequest inventoryRequest){
        inventoryService.createInventory(inventoryRequest);
    }
}

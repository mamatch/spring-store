package com.djoumatch.orderservice.service;

import com.djoumatch.orderservice.dto.InventoryResponse;
import com.djoumatch.orderservice.dto.OrderLineItemDto;
import com.djoumatch.orderservice.dto.OrderRequest;
import com.djoumatch.orderservice.model.Order;
import com.djoumatch.orderservice.model.OrderLineItem;
import com.djoumatch.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Order Service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderName(UUID.randomUUID().toString());

        List<OrderLineItem> orderLineItemList = orderRequest.getOrderLineItemDtoList().stream().map(this::mapOLIToOLIDTO).toList();
        order.setOrderLineItemList(orderLineItemList);
        // Call invetory service and call service if it's in stock
        List<String> skuCodes = order.getOrderLineItemList()
                .stream()
                .map(OrderLineItem::getSkuCode).toList();
        InventoryResponse[]  inventoryResponses= webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("sku-code", skuCodes).build())
                        .retrieve()
                                .bodyToMono(InventoryResponse[].class)
                                        .block();

        assert inventoryResponses != null;
        boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);
        if (allProductsInStock) {
            orderRepository.save(order);
        }
        else {
            throw new IllegalArgumentException("Product not in stock");
        }

    }

    private OrderLineItem mapOLIToOLIDTO(OrderLineItemDto orderLineItemDto) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setQuantity(orderLineItemDto.getQuantity());
        orderLineItem.setPrice(orderLineItemDto.getPrice());
        orderLineItem.setSkuCode(orderLineItemDto.getSkuCode());

        return orderLineItem;
    }

}

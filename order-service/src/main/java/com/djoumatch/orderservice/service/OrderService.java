package com.djoumatch.orderservice.service;

import com.djoumatch.orderservice.dto.OrderLineItemDto;
import com.djoumatch.orderservice.dto.OrderRequest;
import com.djoumatch.orderservice.model.Order;
import com.djoumatch.orderservice.model.OrderLineItem;
import com.djoumatch.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderName(UUID.randomUUID().toString());

        List<OrderLineItem> orderLineItemList = orderRequest.getOrderLineItemDtoList().stream().map(this::mapOLIToOLIDTO).toList();
        order.setOrderLineItemList(orderLineItemList);
        orderRepository.save(order);
    }

    private OrderLineItem mapOLIToOLIDTO(OrderLineItemDto orderLineItemDto) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setQuantity(orderLineItemDto.getQuantity());
        orderLineItem.setPrice(orderLineItemDto.getPrice());
        orderLineItem.setSkuCode(orderLineItemDto.getSkuCode());

        return orderLineItem;
    }

}

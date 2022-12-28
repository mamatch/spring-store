package com.djoumatch.orderservice;

import com.djoumatch.orderservice.dto.OrderLineItemDto;
import com.djoumatch.orderservice.dto.OrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class OrderServiceApplicationTests {

    @Container
    static private MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:5");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void setDynamicProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
        dynamicPropertyRegistry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.driver-class-name", mySQLContainer::getDriverClassName);
        dynamicPropertyRegistry.add("spring.datasource.username", mySQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", mySQLContainer::getPassword);

    }

    /**
     * Assert the order is created
     * @throws Exception
     */
    @Test
    void shouldAddAnOrder() throws Exception {
        OrderRequest orderRequest = getOrderRequest();
        String orderRequestString = objectMapper.writeValueAsString(orderRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderRequestString))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    /**
     * Return a mock order
     * @return
     */
    private OrderRequest getOrderRequest() {
        OrderRequest orderRequest = new OrderRequest();
        List<OrderLineItemDto> orderLineItemDtoList = new ArrayList<>();
        OrderLineItemDto orderLineItemDto = new OrderLineItemDto();
        orderLineItemDto.setQuantity(5);
        orderLineItemDto.setPrice(BigDecimal.valueOf(1500));
        orderLineItemDto.setSkuCode("mdrrrrr");
        orderLineItemDtoList.add(orderLineItemDto);
        orderRequest.setOrderLineItemDtoList(orderLineItemDtoList);

        return orderRequest;

    }

}

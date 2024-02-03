package com.zerhmouti.orderservice.service;


import com.zerhmouti.orderservice.dto.InventoryResponse;
import com.zerhmouti.orderservice.dto.OrderLineItemsDto;
import com.zerhmouti.orderservice.dto.OrderRequest;
import com.zerhmouti.orderservice.event.OrderListEvent;
import com.zerhmouti.orderservice.model.Order;
import com.zerhmouti.orderservice.model.OrderLineItems;
import com.zerhmouti.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String,OrderListEvent> kafkaTemplate;
    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderLineItemsList(orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto).toList());
        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode).toList();

        // Call inventory Service, and place order if product is in stock
        InventoryResponse[] result=webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock= Arrays.stream(result)
                        .allMatch(InventoryResponse::isPresent);

        if(allProductsInStock){
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic",new OrderListEvent(order.getOrderNumber()));
            return "Order placed successfuly!" ;
        }else{
            throw new IllegalArgumentException("Product is not in stock, pleas try later");
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        return OrderLineItems.builder()
                .price(orderLineItemsDto.getPrice())
                .quantity(orderLineItemsDto.getQuantity())
                .skuCode(orderLineItemsDto.getSkuCode())
                .build();
    }
}

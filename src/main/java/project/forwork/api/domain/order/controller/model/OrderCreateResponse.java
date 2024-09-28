package project.forwork.api.domain.order.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.forwork.api.domain.order.model.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateResponse {

    private Long orderId;
    private String email;
    private LocalDateTime orderedAt;
    private BigDecimal totalPrice;

    public static OrderCreateResponse from(Order order){
        return OrderCreateResponse.builder()
                .orderId(order.getId())
                .email(order.getBuyerEmail())
                .orderedAt(order.getOrderedAt())
                .totalPrice(order.getTotalPrice())
                .build();
    }
}

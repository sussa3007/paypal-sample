package com.paypilot.paypilot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BreakdownDto {

    @JsonProperty("item_total")
    private ItemMoneyDto itemTotal;

    public static BreakdownDto of(OrderDto orderDto) {
        List<OrderItemDto> items =
                orderDto.getPurchaseUnits().get(0).getItems();
        double sum = items.stream()
                .mapToDouble(i ->
                        Double.parseDouble(i.getQuantity()) * Double.parseDouble(i.getUnitAmount().getValue())
                ).sum();

        return BreakdownDto.builder()
                .itemTotal(ItemMoneyDto.builder()
                        .currencyCode("USD")
                        .value(String.valueOf(sum))
                        .build())
                .build();

    }
}

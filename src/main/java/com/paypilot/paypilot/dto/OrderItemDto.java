package com.paypilot.paypilot.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderItemDto {

    private String name;

    private String quantity;

    @JsonProperty("unit_amount")
    private ItemMoneyDto unitAmount;
}

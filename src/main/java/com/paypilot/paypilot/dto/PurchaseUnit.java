package com.paypilot.paypilot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseUnit {

    private List<OrderItemDto> items;

    private MoneyDto amount;

}
package com.paypilot.paypilot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.paypilot.paypilot.constants.OrderIntent;
import com.paypilot.paypilot.constants.OrderStatus;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDetailsResponseDto {

    private String id;

    private OrderIntent intent;

    private OrderStatus status;

    @JsonProperty("purchase_units")
    private List<PurchaseUnit> purchaseUnits;

    private List<LinkDto> links;

}

package com.paypilot.paypilot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.paypilot.paypilot.constants.OrderIntent;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderDto implements Serializable {

    private OrderIntent intent;

    @JsonProperty("purchase_units")
    private List<PurchaseUnit> purchaseUnits;

    @JsonProperty("application_context")
    private PaypalAppContextDto applicationContext;

}
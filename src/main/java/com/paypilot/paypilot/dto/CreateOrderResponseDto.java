package com.paypilot.paypilot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.paypilot.paypilot.constants.OrderStatus;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateOrderResponseDto {

    private String id;

    private OrderStatus status;

    private List<LinkDto> links;

}
package com.paypilot.paypilot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.paypilot.paypilot.constants.PaymentLandingPage;
import lombok.Data;

@Data
public class PaypalAppContextDto {

    @JsonProperty("brand_name")
    private String brandName;

    @JsonProperty("landing_page")
    private PaymentLandingPage landingPage;

    @JsonProperty("return_url")
    private String returnUrl;

    @JsonProperty("cancel_url")
    private String cancelUrl;

    public static PaypalAppContextDto getDefaultContext() {
        PaypalAppContextDto ac = new PaypalAppContextDto();
        ac.setReturnUrl("http://localhost:8080/checkout/success");
        ac.setCancelUrl("http://localhost:8080/");
        ac.setBrandName("sussa");
        ac.setLandingPage(PaymentLandingPage.BILLING);
        return ac;
    }

}

package com.paypilot.paypilot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypilot.paypilot.config.PaypalConfig;
import com.paypilot.paypilot.constants.PaypalEndpoints;
import com.paypilot.paypilot.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com.paypilot.paypilot.constants.PaypalEndpoints.*;

@Service
@Slf4j

public class PaypalHttpClient {

    private final HttpClient httpClient;
    private final PaypalConfig paypalConfig;
    private final ObjectMapper objectMapper;

    private static final String BEARER_TYPE = "Bearer ";

    public PaypalHttpClient(
            PaypalConfig paypalConfig,
            ObjectMapper objectMapper
    ) {
        this.httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
        this.paypalConfig = paypalConfig;
        this.objectMapper = objectMapper;
    }


    public AccessTokenResponseDto getAccessToken() throws Exception{
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(createUrl(paypalConfig.getBaseUrl(), GET_ACCESS_TOKEN)))
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, encodeBasicCredentials())
                .header(HttpHeaders.ACCEPT_LANGUAGE, "en_US")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String content = response.body();

        return objectMapper.readValue(content, AccessTokenResponseDto.class);
    }


    public ClientTokenDto getClientToken() throws Exception {
        AccessTokenResponseDto accessTokenDto = getAccessToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(createUrl(paypalConfig.getBaseUrl(), GET_CLIENT_TOKEN)))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, BEARER_TYPE + accessTokenDto.getAccessToken())
                .header(HttpHeaders.ACCEPT_LANGUAGE, "en_US")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String content = response.body();

        return objectMapper.readValue(content, ClientTokenDto.class);
    }

    public CreateOrderResponseDto createOrder(OrderDto orderDto) throws Exception {
        AccessTokenResponseDto accessTokenDto = getAccessToken();
        String payload = objectMapper.writeValueAsString(orderDto);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(createUrl(paypalConfig.getBaseUrl(), ORDER_CHECKOUT)))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, BEARER_TYPE + accessTokenDto.getAccessToken())
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String content = response.body();

        return objectMapper.readValue(content, CreateOrderResponseDto.class);
    }

    public OrderDetailsResponseDto getOrderDetails(String orderId) throws Exception {
        AccessTokenResponseDto accessTokenDto = getAccessToken();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(createUrl(paypalConfig.getBaseUrl(), ORDER_CHECKOUT, orderId)))
                .header(HttpHeaders.AUTHORIZATION, BEARER_TYPE + accessTokenDto.getAccessToken())
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String content = response.body();
        return objectMapper.readValue(content, OrderDetailsResponseDto.class);
    }

    private String encodeBasicCredentials() {
        String input = paypalConfig.getClientId() + ":" + paypalConfig.getSecret();
        return "Basic " + Base64.getEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8));
    }
}

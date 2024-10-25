package com.lulu.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class RecaptchaService {
    @Value("${recaptcha.secret.key}")
    private String secretKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean verify(String recaptchaResponse) {
        String url = "https://www.google.com/recaptcha/api/siteverify";
        String requestUrl = String.format("%s?secret=%s&response=%s", url, secretKey, recaptchaResponse);

        ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, null, String.class);

        // 簡單解析回應來檢查成功
        return response.getBody() != null && response.getBody().contains("\"success\": true");
    }
}

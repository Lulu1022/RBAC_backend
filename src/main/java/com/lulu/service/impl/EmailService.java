package com.lulu.service.impl;

import com.lulu.model.dto.user.UserRegisterRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    private String recipient;
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(UserRegisterRequest registerRequest) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED, StandardCharsets.UTF_8.name());

            helper.setTo("lulu@example.com"); // 設置收件人，請確保這是有效的郵件地址
            helper.setSubject("申請通知"); // 設置主題

            String htmlContent = "<html>" +
                    "<head><style>" +
                    "body { font-family: Arial, sans-serif; }" +
                    ".header { color: #4CAF50; }" +
                    ".footer { color: #888888; font-size: 12px; }" +
                    "</style></head>" +
                    "<body>" +
                    "<h1 class='header'>申請通知</h1>" +
                    "<p>親愛的" + registerRequest.getUsername() + ",</p>" +
                    "<p>我們已經收到您的申請，感謝您的耐心等待。我們會在 1 個工作日內開啟您的帳號。</p>" +
                    "<p>以下是您的申請詳情：</p>" +
                    "<ul>" +
                    "<li><strong>帳號:</strong> " + registerRequest.getUsername() + "</li>" +
                    "<li><strong>信箱:</strong> " + registerRequest.getEmail() + "</li>" +
                    "</ul>" +
                    "<p class='footer'>此郵件由系統自動發送，請勿回復。</p>" +
                    "</body>" +
                    "</html>";

            helper.setText(htmlContent, true); // 設置 HTML 內容

            javaMailSender.send(message); // 發送郵件
        } catch (MessagingException e) {
            // 處理發送郵件過程中的異常
            e.printStackTrace();
        }
    }
}

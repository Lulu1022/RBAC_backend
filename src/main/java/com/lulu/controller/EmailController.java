package com.lulu.controller;

import com.lulu.model.dto.user.UserRegisterRequest;
import com.lulu.service.impl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emails")
@CrossOrigin(origins = "*")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public void sendEmail(@RequestBody UserRegisterRequest registerRequest) {
        emailService.sendEmail(registerRequest);
    }
}
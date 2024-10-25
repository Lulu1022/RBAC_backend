package com.lulu.controller;

import com.lulu.service.impl.RecaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/captcha")
@CrossOrigin(origins = "*")
public class CaptchaController {
    @Autowired
    private RecaptchaService recaptchaService;

    @PostMapping("/submit")
    public boolean verifyReCaptcha(@RequestBody String recaptchaResponse) {
        return recaptchaService.verify(recaptchaResponse);
    }
}

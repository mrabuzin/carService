package com.inovatrend.carService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class EmailManager {

    @Autowired
    private JavaMailSender mailSender;


    public void sendEmail(SimpleMailMessage email) {
        mailSender.send(email);
    }
}

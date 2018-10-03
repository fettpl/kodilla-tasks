package com.crud.tasks.config;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
@Getter
public class AdminConfig {
    @Value("${admin.mail}")
    private String adminMail;
}

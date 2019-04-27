package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailCreatorService {
    private static final String BUTTON = "Visit website";
    private static final String GOODBYE = "This notification was generated automatically. Please do not respond. Thanks for your cooperation!";
    private static final String URL = "http://localhost:8888/crud";

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", URL);
        context.setVariable("button", BUTTON);
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbye_message", GOODBYE);
        context.setVariable("company_details", adminConfig.getAdminMail());
        return templateEngine.process("mail/created-trello-card-mail", context);
    }
}

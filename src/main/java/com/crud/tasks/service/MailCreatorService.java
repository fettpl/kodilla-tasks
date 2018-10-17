package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {
    private static final String BUTTON = "Visit website";
    private static final String GOODBYE = "This notification was generated automatically. Please do not respond. Thanks for your cooperation!";
    private static final String URL = "http://localhost:8888/crud";

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private DbService dbService;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", URL);
        context.setVariable("button", BUTTON);
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbye_message", GOODBYE);
        context.setVariable("company_details", adminConfig.getAdminMail());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildTrelloPendingTasksEmail(String message) {

        List<Task> taskList = dbService.getAllTasks();

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", URL);
        context.setVariable("button", BUTTON);
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbye_message", GOODBYE);
        context.setVariable("company_details", adminConfig.getAdminMail());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("taskList", taskList);
        return templateEngine.process("mail/pending-tasks-mail", context);
    }
}

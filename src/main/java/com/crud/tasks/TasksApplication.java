package com.crud.tasks;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import com.ulisesbocchio.jasyptspringboot.environment.StandardEncryptableEnvironment;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableEncryptableProperties
@EncryptablePropertySource("application.properties")
@Configuration
@EncryptablePropertySource(name = "EncryptedProperties", value = "classpath:application.properties")
public class TasksApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .environment(new StandardEncryptableEnvironment())
                .sources(TasksApplication.class).run(args);
    }
}

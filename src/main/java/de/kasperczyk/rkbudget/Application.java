package de.kasperczyk.rkbudget;

import de.kasperczyk.rkbudget.config.DevUserConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

import java.util.Arrays;

@SpringBootApplication
@ImportResource("classpath:/application-context.xml")
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        context.getBean(Application.class).run(args, context);
    }

    private void run(String[] args, ConfigurableApplicationContext context) {
        if (Arrays.stream(context.getEnvironment().getActiveProfiles())
                .anyMatch(env -> env.equals("dev-h2") || env.equals("dev-postgres"))) {
            context.getBean(DevUserConfig.class).configureDevUser();
        }
    }
}

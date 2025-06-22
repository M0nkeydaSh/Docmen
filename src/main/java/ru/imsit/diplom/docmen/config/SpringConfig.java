package ru.imsit.diplom.docmen.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.imsit.diplom.docmen.notification.StubNotificationService;

@Configuration
public class SpringConfig {

    @Bean
    @ConditionalOnMissingBean
    public StubNotificationService stubNotificationService() {
        return new StubNotificationService();
    }
}

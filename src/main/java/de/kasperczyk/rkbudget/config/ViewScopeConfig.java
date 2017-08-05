package de.kasperczyk.rkbudget.config;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ViewScopeConfig {

    @Bean
    public CustomScopeConfigurer customScopeConfigurer() {
        CustomScopeConfigurer customScopeConfigurer = new CustomScopeConfigurer();
        Map<String, Object> scopeMap = new HashMap<>();
        scopeMap.put("view", new ViewScope());
        customScopeConfigurer.setScopes(scopeMap);
        return customScopeConfigurer;
    }
}

package de.kasperczyk.rkbudget.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ErrorPageConfig implements EmbeddedServletContainerCustomizer {

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        Set<ErrorPage> errorPages = new HashSet<>();
        errorPages.add(new ErrorPage(HttpStatus.UNAUTHORIZED, "/pages/error/unauthorized.xhtml"));
        errorPages.add(new ErrorPage(HttpStatus.NOT_FOUND, "/pages/error/not-found.xhtml"));
        errorPages.add(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/pages/error/error.xhtml"));
        container.setErrorPages(errorPages);
    }
}

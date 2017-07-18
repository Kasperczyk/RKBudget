package de.kasperczyk.rkbudget.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;

@Configuration
public class JsfConfig implements ServletContextAware {

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servletRegistration = new ServletRegistrationBean(new FacesServlet());
        servletRegistration.addUrlMappings("*.xhtml");
        servletRegistration.setLoadOnStartup(1);
        return servletRegistration;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
        servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
        servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", "true");
        servletContext.setInitParameter("javax.faces.FACELETS_BUFFER_SIZE", "65535"); // 64 kB
        servletContext.setInitParameter("javax.faces.DATETIMECONVERTER_DEFAULT_TIMEZONE_IS_SYSTEM_TIMEZONE", "true");
        servletContext.setInitParameter("primefaces.THEME", "delta");
        servletContext.setInitParameter("primefaces.FONT_AWESOME", "true");
    }
}

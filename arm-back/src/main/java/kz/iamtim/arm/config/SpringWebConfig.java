package kz.iamtim.arm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring Boot configuration class.
 *
 * @author Timur Tibeyev.
 */
@Configuration
public class SpringWebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}

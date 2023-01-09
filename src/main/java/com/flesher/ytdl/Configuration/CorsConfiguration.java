package com.flesher.ytdl.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedMethods("POST", "GET", "PATCH", "DELETE")
                .allowedHeaders("*")
                .maxAge(3600)
                .allowedOrigins(
                        "http://localhost:4200",
                        "https://wwwytdl-jf2qqsfolq-uc.a.run.app",
                        "https://www.rapid-ytdl.com"
                        );
    }
}

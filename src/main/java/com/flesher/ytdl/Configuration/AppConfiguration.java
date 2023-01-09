package com.flesher.ytdl.Configuration;

import com.flesher.ytdl.Properties.Creds;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties
@Configuration
public class AppConfiguration {

    @Bean(name = "credentials")
    @ConfigurationProperties(prefix = "creds")
    public Creds LoadCreds(){ return new Creds(); }

}

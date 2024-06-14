package com.birds.nn.gameCore.main;

import com.birds.nn.utils.Config;
import com.birds.nn.utils.ConfigLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.birds.nn")
public class AppConfig {

    @Bean
    public Config config() {
        return ConfigLoader.loadConfig("src/main/resources/com/birds/nn/config.json");
    }
}
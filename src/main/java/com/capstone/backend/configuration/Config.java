package com.capstone.backend.configuration;

import com.capstone.backend.gui.GUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public GUI getGUI() {
        return new GUI();
    }
}

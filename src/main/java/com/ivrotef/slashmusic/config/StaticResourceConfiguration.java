package com.ivrotef.slashmusic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;

@Configuration
@AutoConfigureAfter(DispatcherServletAutoConfiguration.class)
public class StaticResourceConfiguration extends WebMvcConfigurerAdapter {
/*
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
            .addResourceLocations("file:C:\\Users\\USER}\\semestre6\\SlashMusic\\src\\main\\resources\\static");
    } */
}

package com.springBootStudy.config;

import org.springframework.context.annotation.ComponentScan;import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ComponentScan("com.springBootStudy")
@Configuration
public class Config {
}

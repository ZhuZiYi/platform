package com.lbw.properties;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lenovo on 2018-02-24.
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class PropertiesConfig {


}

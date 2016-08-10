package com.epam.brest.course2015.social.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by alexander_borohov on 10.8.16.
 */
@Configuration
public class SeleniumConfig {
    @Bean
    DesiredCapabilities capabilities() {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("selenium", true);
        return capabilities;
    }

    @Bean
    WebDriver driver(@Autowired DesiredCapabilities capabilities) {
        return new RemoteWebDriver(capabilities);
    }
}

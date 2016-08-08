package com.epam.brest.course2015.social.app;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.epam.brest.course2015.social.consumer.SocialConsumer;
import com.epam.brest.course2015.social.core.User;
import com.epam.brest.course2015.social.mail.SocialMail;
import com.epam.brest.course2015.social.test.SocialLogger;
import org.easymock.EasyMock;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;


/**
 * Created by alexander_borohov on 28.7.16.
 */
@Configuration
@ComponentScan(basePackageClasses = {SocialAdminController.class})
class SocialControllerConfig {

    @Bean
    static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        configurer.setLocations(new ClassPathResource("test-data.properties", User.class.getClassLoader()),
                                new ClassPathResource("social-app.properties"),
                                new ClassPathResource("cdn.properties"),
                                new ClassPathResource("security-roles.properties"));
        return configurer;
    }

    @Bean
    SocialConsumer socialConsumer() {
        return EasyMock.createMock(SocialConsumer.class);
    }

    @Bean
    SocialMail socialMail() {
        return EasyMock.createMock(SocialMail.class);
    }

    @Bean
    Cloudinary cloudinary() {
        return new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name", "simple-social",
                        "api_key", "543582919166178",
                        "api_secret", "ZJuERS_91Sda3qhiLog6ZQ4DRgQ"
                )
        );
    }

    @Bean
    SocialLogger socialLogger() {
        return new SocialLogger();
    }


}
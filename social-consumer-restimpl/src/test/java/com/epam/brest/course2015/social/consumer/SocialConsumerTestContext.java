package com.epam.brest.course2015.social.consumer;

import com.epam.brest.course2015.social.core.Image;
import com.epam.brest.course2015.social.test.SocialLogger;
import org.easymock.EasyMock;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;


/**
 * Created by alexander_borohov on 1.8.16.
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"com.epam.brest.course2015.social.consumer"})
public class SocialConsumerTestContext {
    @Bean
    RestTemplate restTemplate() {
        return EasyMock.createMock(RestTemplate.class);
    }

    @Bean
    Image image() {
        Image image = new Image();
        image.setUrl("http://localhost:8082/test-rest/url");
        image.setUrl50("http://localhost:8082/test-rest/url50");
        image.setUrl81("http://localhost:8082/test-rest/url81");
        return image;
    }

    @Bean
    static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer conf = new PropertyPlaceholderConfigurer();
        conf.setIgnoreResourceNotFound(true);
        conf.setLocations(new ClassPathResource("social-app.properties")
                         , new ClassPathResource("test-data.properties", SocialLogger.class.getClassLoader()));
        return conf;
    }

    @Bean
    SocialLogger socialLogger() {
        return new SocialLogger();
    }

    @Bean
    SocialHeaderInterceptor interceptor() {
        return new SocialHeaderInterceptor();
    }
}

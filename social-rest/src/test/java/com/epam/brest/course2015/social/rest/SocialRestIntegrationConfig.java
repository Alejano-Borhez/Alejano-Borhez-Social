package com.epam.brest.course2015.social.rest;

import com.epam.brest.course2015.social.core.User;
import com.epam.brest.course2015.social.dao.UserDao;
import com.epam.brest.course2015.social.jpa.UserDaoJPA;
import com.epam.brest.course2015.social.test.SocialLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by alexander_borohov on 10.8.16.
 */
//@Configuration
//@EnableTransactionManagement
//@EnableAspectJAutoProxy
//@EnableCaching
//@ComponentScan(basePackages = {"com.epam.brest.course2015.social.core", "com.epam.brest.course2015.social.jpa"})
//@PropertySource("classpath:database.properties")
//@PropertySource("classpath:app-jpa.properties")
//@PropertySource("classpath:app-service.properties")
//@PropertySource("classpath:test-data.properties")
//@PropertySource("classpath:security-roles.properties")
class SocialRestIntegrationConfig {
    @Value("${jdbc.driverClassName}")
    private String driverClassName;
    @Value("${jdbc.url}")
    private String url;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(name = "dataSource")
    DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(driverClassName);
            dataSource.setUrl(url);
            dataSource.setUsername("${jdbc.username}");
            dataSource.setPassword("${jdbc.password}");
        return dataSource;
    }

    @Bean(name = "populator")
    DatabasePopulator populator() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("create-tables.sql"),
                new ClassPathResource("test-data-script.sql"));
        return populator;
    }

    @Bean
    @DependsOn({"dataSource", "populator"})
    DataSourceInitializer initializeDatabase(DataSource source, DatabasePopulator populator) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(source);
        initializer.setDatabasePopulator(populator);
        return initializer;
    }

    @Bean
    SocialLogger logger() {
        return new SocialLogger();
    }

    @Bean
    PersistenceAnnotationBeanPostProcessor postProcessor() {
        return new PersistenceAnnotationBeanPostProcessor();
    }

    @Bean
    PersistenceExceptionTranslationPostProcessor exceptionPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean(name = "jpaVendorAdapter")
    JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.HSQL);
        adapter.setDatabasePlatform("org.hibernate.dialect.HSQLDialect");
        adapter.setGenerateDdl(false);
        adapter.setShowSql(true);
        return adapter;
    }

    @Bean(name = "entityManagerFactory")
    @DependsOn({"dataSource", "jpaVendorAdapter"})
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter adapter) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setJpaVendorAdapter(adapter);
        factoryBean.setPackagesToScan("com.epam.brest.course2015.social.core");
        Properties props = new Properties();
        props.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");
        props.setProperty("hibernate.cache.use_second_level_cache", "true");
        props.setProperty("hibernate.cache.use_query_cache", "false");
        factoryBean.setJpaProperties(props);
        return factoryBean;
    }

    @Bean(name = "transactionManager")
    @DependsOn({"entityManagerFactory", "dataSource"})
    JpaTransactionManager transactionManager(DataSource dataSource, LocalContainerEntityManagerFactoryBean factoryBean) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource);
        transactionManager.setEntityManagerFactory((EntityManagerFactory) factoryBean);
        return transactionManager;
    }

    @Bean(name = "cacheManager")
    @DependsOn("ehcache")
    CacheManager cacheManager(EhCacheManagerFactoryBean cache) {
        EhCacheCacheManager cacheManager = new  EhCacheCacheManager();
        cacheManager.setCacheManager(cache.getObject());
        return cacheManager;
    }

    @Bean
    EhCacheManagerFactoryBean cache() {
        EhCacheManagerFactoryBean cache = new EhCacheManagerFactoryBean();
        cache.setConfigLocation(new ClassPathResource("socialcache.xml"));
        cache.setShared(true);
        return cache;
    }

}

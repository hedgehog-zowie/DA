package com.iuni.data.ws.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Configuration
@ComponentScan(basePackages={"com.iuni.data.ws"})
@PropertySource(value = { "classpath:da-ws.properties" })
@EnableAspectJAutoProxy
public class ApplicationConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer()    {
        return new PropertySourcesPlaceholderConfigurer();
    }

}

/**
 * 
 */
package com.example.demodeal.security.config;

import com.example.demodeal.security.property.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {

}


// Resource server ->property
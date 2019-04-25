package org.syaku.toy.security.config;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * ResourceServer Spring Security Configuration
 * @author Seok Kyun. Choi.
 * @since 2019-04-24
 */
@Configuration
@EnableResourceServer
@Profile("resourceServer")
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Value("${resource.id:spring-boot-application}")
    private String resourceId;

    @Bean
    public JwtAccessTokenConverter resourceAccessTokenConverter() throws IOException {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        Resource resource = new ClassPathResource("public.txt");
        String publicKey = IOUtils.toString(resource.getInputStream(), Charset.defaultCharset());
        converter.setVerifierKey(publicKey);
        return converter;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(resourceId);
    }
}

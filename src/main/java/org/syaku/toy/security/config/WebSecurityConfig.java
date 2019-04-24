package org.syaku.toy.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * ResourceServer Spring Security Configuration
 * @author Seok Kyun. Choi.
 * @since 2019-04-22
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    // There is no PasswordEncoder mapped for the id "null"
    // okay https://stackoverflow.com/questions/46999940/spring-boot-how-to-specify-the-passwordencoder
    // https://www.mkyong.com/spring-boot/spring-security-there-is-no-passwordencoder-mapped-for-the-id-null/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Configuration
    static class Security extends WebSecurityConfigurerAdapter {

        // 새로 생성하면 생성한 passwordEncoder 사용되지 않고 기본 암호화가 사용된다.
        /*@Bean
        @Override
        public UserDetailsService userDetailsService() {

            return new InMemoryUserDetailsManager(
                User.withDefaultPasswordEncoder()
                    .username("test")
                    .password("{noop}1234")
                    .roles("USER")
                    .build());
        }*/

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            // PasswordEncoder 공유하기 위한 설정
            auth.inMemoryAuthentication()
                .withUser("test").password("1234").roles("USER");

        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }


        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                .antMatchers("/hello/**").hasRole("USER");
        }
    }
}

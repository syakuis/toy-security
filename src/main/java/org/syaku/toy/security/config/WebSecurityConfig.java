package org.syaku.toy.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
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
            // {noop}1234
            // {noop}1234
            auth.inMemoryAuthentication()
                .withUser("test").password("{" + EncodingId.noop + "}1234").roles( "USER")
                .and()
                .withUser("admin").password("{" + EncodingId.bcrypt +"}$2a$10$HTFfh/OwE9D8cjp6FP80jOCHnH2o1m0lF5..g7usJ1CzVz84FIBA.").roles("ADMIN", "USER");

        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }


        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                .antMatchers("/basic/**").hasRole("USER")
//                .antMatchers("/**").denyAll()
                // 손님도 허용하기 위함
                // .anyRequest().anonymous()
                // 인증된 사용만 허용
                .anyRequest().fullyAuthenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                /*.and()
                .formLogin()
                .loginPage("/login").permitAll()*/;
        }
    }
}

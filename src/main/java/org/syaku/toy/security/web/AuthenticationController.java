package org.syaku.toy.security.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

/**
 * @author Seok Kyun. Choi.
 * @since 2019-04-24
 */
@Slf4j
@RestController
public class AuthenticationController {
    @GetMapping("/basic")
    public Authentication basic(Authentication authentication) {
        log.debug("{}", authentication);
        return authentication;
    }

    @GetMapping("/auth")
    public Authentication auth(Authentication authentication) {
        log.debug("{}", authentication);
        return authentication;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/hello")
    public String hello() {
        return "hello world!!!";
    }
}

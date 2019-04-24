package org.syaku.toy.security.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Seok Kyun. Choi.
 * @since 2019-04-24
 */
@RestController
public class HelloWorldController {
    @GetMapping("/hello")
    public String hello() {
        return "hello world!!!";
    }
}

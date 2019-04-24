package org.syaku.toy.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Seok Kyun. Choi.
 * @since 2019-04-23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

    @Autowired private MockMvc mvc;

    @Test
    public void 로그인() throws Exception {
        this.mvc.perform(post("/login")
            .with(csrf())
            .param("username", "test")
            .param("password", "1234")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 로그인_인증() throws Exception {
        this.mvc.perform(get("/basic"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 선언적인권한설정() throws Exception {
        this.mvc.perform(get("/hello")).andExpect(status().isOk());
    }
}

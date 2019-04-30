package org.syaku.toy.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Seok Kyun. Choi.
 * @since 2019-04-23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"default","resourceServer"})
public class AccessTokenTest {
    @Autowired private MockMvc mvc;

    @Test
    public void 토큰요청() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "bar");
        params.add("username", "test");
        params.add("password", "1234");

        this.mvc.perform(post("/oauth/token")
            .with(httpBasic("bar", "foo"))
            .params(params))
            .andExpect(status().isOk())
            .andDo(print());

    }

    /**
     * curl -u bar:foo http://localhost:8080/oauth/token -d  "grant_type=password&username=test&password=1234"
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    private String obtainAccessToken(String username, String password) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "bar");
        params.add("username", username);
        params.add("password", password);

        ResultActions result = this.mvc.perform(post("/oauth/token")
            .params(params)
            .with(httpBasic("bar","foo"))
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    @Test
    public void 요청_인증_실패() throws Exception {
        this.mvc.perform(get("/auth")).andExpect(status().is4xxClientError()).andDo(print());
    }

    /**
     * curl -H "authorization: bearer AccessToken" http://localhost:8080/auth
     * @throws Exception
     */
    @Test
    public void 요청_인증_성공() throws Exception {
        this.mvc.perform(get("/auth")
            .header("Authorization", "Bearer " + obtainAccessToken("test", "1234")))
            .andExpect(status().isOk());
    }
}

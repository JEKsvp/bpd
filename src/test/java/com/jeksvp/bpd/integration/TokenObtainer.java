package com.jeksvp.bpd.integration;

import lombok.SneakyThrows;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TokenObtainer {

    public static final String JEKSVP_LOGIN = "jeksvp";
    public static final String JEKSVP_PASSWORD = "testpassword";

    @SneakyThrows
    public String obtainAccessToken(MockMvc mockMvc, String login, String password) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", "jeksvp");
        params.add("password", "testpassword");

        ResultActions result
                = mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic("clientid", "secret"))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    @SneakyThrows
    public HttpHeaders obtainAuthHeader(MockMvc mockMvc, String login, String password) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        String token = obtainAccessToken(mockMvc, login, password);
        map.add("Authorization", "Bearer" + token);
        return new HttpHeaders(map);
    }
}

package com.jeksvp.bpd.integration.helpers;

import com.jeksvp.bpd.integration.models.DefaultUser;
import lombok.SneakyThrows;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.jeksvp.bpd.integration.models.DefaultClient.CLIENT_ID;
import static com.jeksvp.bpd.integration.models.DefaultClient.SECRET;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TokenObtainer {

    @SneakyThrows
    public String obtainAccessToken(MockMvc mockMvc, String login, String password) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", login);
        params.add("password", password);

        ResultActions result
                = mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic(CLIENT_ID, SECRET))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    @SneakyThrows
    public HttpHeaders obtainAuthHeader(MockMvc mockMvc, String login, String password) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        String token = obtainAccessToken(mockMvc, login, password);
        map.add("Authorization", "Bearer " + token);
        return new HttpHeaders(map);
    }

    @SneakyThrows
    public HttpHeaders obtainDefaultClientHeader(MockMvc mockMvc) {
        return obtainAuthHeader(mockMvc, DefaultUser.JEKSVP_USERNAME, DefaultUser.JEKSVP_PASSWORD);
    }

    @SneakyThrows
    public HttpHeaders obtainDefaultTherapistHeader(MockMvc mockMvc) {
        return obtainAuthHeader(mockMvc, DefaultUser.PSYCHO_USERNAME, DefaultUser.PSYCHO_PASSWORD);
    }
}

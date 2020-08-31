package com.jeksvp.bpd.web.controller;

import com.jeksvp.bpd.service.AccessListService;
import com.jeksvp.bpd.service.access.AccessService;
import com.jeksvp.bpd.web.dto.request.access.AccessFilter;
import com.jeksvp.bpd.web.dto.response.access.AccessResponse;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccessController.class)
public class AccessControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccessListService accessListService;

    @MockBean
    private AccessService accessService;

    @Test
    @WithMockUser(username = "psycho")
    public void getClientsByCurrentTherapistTest() throws Exception {
        when(accessListService.getAccessesOfUser(eq("psycho"), eq(AccessFilter.builder().build())))
                .thenReturn(buildClientAccessList());

        String responseBody = IOUtils.toString(getClass().getResource("/web/controller/client-controller/valid-client-accesses-response.json"), Charset.defaultCharset());
        mvc.perform(get("/api/v1/users/current/accesses", "testUser"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    @Test
    @WithMockUser(username = "psycho")
    public void getClientsByCurrentTherapistAndStatusTest() throws Exception {
        AccessFilter filter = AccessFilter.builder()
                .status("PENDING")
                .build();
        when(accessListService.getAccessesOfUser(eq("psycho"), eq(filter)))
                .thenReturn(buildPendingClientAccessList());

        String responseBody = IOUtils.toString(getClass().getResource("/web/controller/client-controller/filtered-client-accesses-response.json"), Charset.defaultCharset());
        mvc.perform(get("/api/v1/users/current/accesses", "testUser")
                .queryParam("status", "PENDING"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    private List<AccessResponse> buildClientAccessList() {
        return List.of(
                AccessResponse.builder()
                        .username("jeksvp")
                        .status("ACCEPT")
                        .build(),
                AccessResponse.builder()
                        .username("tbolivar")
                        .status("PENDING")
                        .build()
        );
    }


    private List<AccessResponse> buildPendingClientAccessList() {
        return List.of(
                AccessResponse.builder()
                        .username("tbolivar")
                        .status("PENDING")
                        .build()
        );
    }

}
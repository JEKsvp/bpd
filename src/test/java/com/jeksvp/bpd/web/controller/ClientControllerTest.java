package com.jeksvp.bpd.web.controller;

import com.jeksvp.bpd.service.ClientService;
import com.jeksvp.bpd.web.dto.request.client.ClientAccessFilter;
import com.jeksvp.bpd.web.dto.response.client.ClientAccessResponse;
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
@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClientService clientService;

    @Test
    @WithMockUser(username = "psycho")
    public void getClientsByCurrentTherapistTest() throws Exception {
        when(clientService.getAccessedTherapistsOfUser(eq("psycho"), eq(ClientAccessFilter.builder().build())))
                .thenReturn(buildClientAccessList());

        String responseBody = IOUtils.toString(getClass().getResource("/web/controller/client-controller/valid-client-accesses-response.json"), Charset.defaultCharset());
        mvc.perform(get("/api/v1/users/current/client-accesses", "testUser"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    @Test
    @WithMockUser(username = "psycho")
    public void getClientsByCurrentTherapistAndStatusTest() throws Exception {
        ClientAccessFilter filter = ClientAccessFilter.builder()
                .status("PENDING")
                .build();
        when(clientService.getAccessedTherapistsOfUser(eq("psycho"), eq(filter)))
                .thenReturn(buildPendingClientAccessList());

        String responseBody = IOUtils.toString(getClass().getResource("/web/controller/client-controller/filtered-client-accesses-response.json"), Charset.defaultCharset());
        mvc.perform(get("/api/v1/users/current/client-accesses", "testUser")
                .queryParam("status", "PENDING"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    private List<ClientAccessResponse> buildClientAccessList() {
        return List.of(
                ClientAccessResponse.builder()
                        .username("jeksvp")
                        .status("ACCEPT")
                        .build(),
                ClientAccessResponse.builder()
                        .username("tbolivar")
                        .status("PENDING")
                        .build()
        );
    }


    private List<ClientAccessResponse> buildPendingClientAccessList() {
        return List.of(
                ClientAccessResponse.builder()
                        .username("tbolivar")
                        .status("PENDING")
                        .build()
        );
    }

}
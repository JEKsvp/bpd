package com.jeksvp.bpd.web.controller;

import com.jeksvp.bpd.service.TherapistService;
import com.jeksvp.bpd.web.dto.request.therapist.TherapistAccessFilter;
import com.jeksvp.bpd.web.dto.response.therapist.TherapistAccessResponse;
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
@WebMvcTest(TherapistController.class)
public class TherapistControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TherapistService therapistService;

    @Test
    @WithMockUser(username = "psycho")
    public void getClientsByCurrentTherapistTest() throws Exception {
        when(therapistService.getTherapistAccesses(eq("psycho"), eq(TherapistAccessFilter.builder().build())))
                .thenReturn(buildTherapistAccessList());

        String responseBody = IOUtils.toString(getClass().getResource("/web/controller/therapist-controller/valid-client-accesses-response.json"), Charset.defaultCharset());
        mvc.perform(get("/api/v1/users/current/therapist-accesses", "testUser"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    @Test
    @WithMockUser(username = "psycho")
    public void getClientsByCurrentTherapistAndStatusTest() throws Exception {
        TherapistAccessFilter filter = TherapistAccessFilter.builder()
                .status("PENDING")
                .build();
        when(therapistService.getTherapistAccesses(eq("psycho"), eq(filter)))
                .thenReturn(buildPendingTherapistAccessList());

        String responseBody = IOUtils.toString(getClass().getResource("/web/controller/therapist-controller/filtered-therapist-accesses-response.json"), Charset.defaultCharset());
        mvc.perform(get("/api/v1/users/current/therapist-accesses", "testUser")
                .queryParam("status", "PENDING"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    private List<TherapistAccessResponse> buildTherapistAccessList() {
        return List.of(
                TherapistAccessResponse.builder()
                        .username("jeksvp")
                        .status("ACCEPT")
                        .build(),
                TherapistAccessResponse.builder()
                        .username("tbolivar")
                        .status("PENDING")
                        .build()
        );
    }


    private List<TherapistAccessResponse> buildPendingTherapistAccessList() {
        return List.of(
                TherapistAccessResponse.builder()
                        .username("tbolivar")
                        .status("PENDING")
                        .build()
        );
    }

}
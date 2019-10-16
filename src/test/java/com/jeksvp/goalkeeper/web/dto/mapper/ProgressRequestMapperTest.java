package com.jeksvp.goalkeeper.web.dto.mapper;

import com.jeksvp.goalkeeper.domain.entity.Progress;
import com.jeksvp.goalkeeper.web.dto.request.ProgressRequest;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ProgressRequestMapperTest {

    @Test
    public void mapTest(){
        ProgressRequest request = ProgressRequest.builder()
                .name("test name")
                .maxValue(new BigDecimal(100))
                .currentValue(new BigDecimal(50))
                .build();

        Progress expected = Progress.builder()
                .name("test name")
                .maxValue(new BigDecimal(100))
                .currentValue(new BigDecimal(50))
                .build();
        ProgressRequestMapper mapper = new ProgressRequestMapper();
        Progress actual = mapper.map(request);
        assertEquals(expected, actual);
    }


}
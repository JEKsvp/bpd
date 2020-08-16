package com.jeksvp.bpd.web.dto.response.paging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageableDto<DTO> {
    private List<DTO> data;
    private PagingDto paging;

    public <ENTITY> PageableDto(Page<ENTITY> page, Function<ENTITY, DTO> function) {
        this.data = page.getContent().stream()
                .map(function)
                .collect(Collectors.toList());
        this.paging = new PagingDto(page.getSize(), page.getTotalElements(), page.getTotalPages(), page.getNumber());
    }
}
package com.jeksvp.bpd.web.dto.response.paging;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagingDto {
    private long size;
    private long totalElements;
    private long totalPages;
    private long number;
}

package com.repo.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ParameterDto {
    private Long id;
    private String parameter;
    private String value;
}
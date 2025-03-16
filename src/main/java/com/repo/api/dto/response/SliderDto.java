package com.repo.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SliderDto {

    private Long id;
    private String message;
    private String  image;
    private String category;
}

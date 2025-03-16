package com.repo.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {
        private Long id;
        private String category;
        private String parent;
        private String image;

}

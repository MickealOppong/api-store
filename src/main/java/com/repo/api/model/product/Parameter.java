package com.repo.api.model.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Parameter {

    @Id
    @GeneratedValue
    private Long id;
    private String parameter;
    private String value;
}

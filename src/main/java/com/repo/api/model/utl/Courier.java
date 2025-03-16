package com.repo.api.model.utl;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Courier {

    @Id @GeneratedValue
    private Long recId;
    private String courier;
    private double cost;
    private Long deliveryDays;
}

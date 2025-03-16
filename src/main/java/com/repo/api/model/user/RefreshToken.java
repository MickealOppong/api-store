package com.repo.api.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue
    private Long id;
    private String refreshToken;
    private Instant expiredAt;
    private Instant issuedAt;

    @ManyToOne
    @JoinColumn(name = "fk_id",referencedColumnName = "id")
    private Customer customer;
}

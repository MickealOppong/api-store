package com.repo.api.model.user;

import com.repo.api.util.LogEntity;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LastWatched extends LogEntity {

    @Id
    @GeneratedValue
    private Long id;
    private Long productId;
    private String sessionId;

    @OneToOne
    private Customer customer;
}

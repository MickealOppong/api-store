package com.repo.api.util;

import com.repo.api.model.user.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "privacy")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrivacyPolicy extends LogEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String privacyType;

    @Column(length = 2000)
    private String text;
    private boolean active;
    @ManyToOne
    @JoinColumn(name = "fk_id",referencedColumnName = "id")
    private Customer customer;
}

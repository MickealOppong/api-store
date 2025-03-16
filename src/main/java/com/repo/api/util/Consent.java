package com.repo.api.util;

import com.repo.api.model.user.Customer;
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
public class Consent extends LogEntity {

    @Id @GeneratedValue
    private Long id;
    private String consentType;
    private boolean sms;
    private boolean email;
    @ManyToOne
    @JoinColumn(name = "fk_id",referencedColumnName = "id")
    private Customer customer;

}

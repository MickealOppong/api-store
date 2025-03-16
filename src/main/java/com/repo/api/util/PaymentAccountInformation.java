package com.repo.api.util;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PaymentAccountInformation extends LogEntity{

    @Id @GeneratedValue
    private Long id;
    private String accountName;
    private String IBAN;
    private String swiftCode;
    private String accountNumber;


}

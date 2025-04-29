package com.repo.api.model.util;

import com.repo.api.util.LogEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GlobalAddressBook extends LogEntity {

    @Id @GeneratedValue
    private Long addressId;
    private Long customerId;
    private String firstName;
    private String lastName;
    private String companyName;
    private String nip;
    private String street;
    private String city;
    private String houseNumber;
    private String apartmentNumber;
    private String postCode;
    private String addressType;

}

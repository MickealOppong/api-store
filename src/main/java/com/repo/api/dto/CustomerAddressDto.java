package com.repo.api.dto;

import com.repo.api.model.util.GlobalAddressBook;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CustomerAddressDto {


    private Long addressId;
    private Long customerId;
    private String firstName;
    private String lastName;
    private String companyName;
    private String nip;
    private String street;
    private String houseNumber;
    private String city;
    private String apartmentNumber;
    private String postCode;
    private String addressType;

    public CustomerAddressDto(GlobalAddressBook globalAddressBook) {
        this.addressId=globalAddressBook.getAddressId();
        this.customerId = globalAddressBook.getCustomerId();
        this.firstName = globalAddressBook.getFirstName();
        this.lastName = globalAddressBook.getLastName();
        this.city = globalAddressBook.getCity();
        this.companyName = globalAddressBook.getCompanyName();
        this.nip = globalAddressBook.getNip();
        this.street = globalAddressBook.getStreet();
        this.houseNumber =globalAddressBook.getHouseNumber();
        this.apartmentNumber = globalAddressBook.getApartmentNumber();
        this.postCode = globalAddressBook.getPostCode();
        this.addressType = globalAddressBook.getAddressType();
    }
}

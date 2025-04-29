package com.repo.api.dto.response;

import com.repo.api.model.util.GlobalAddressBook;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DeliveryAddressDto {

    private Long addressId;
    private String firstName;
    private String lastName;
    private String companyName;
    private String street;
    private String city;
    private String houseNumber;
    private String apartmentNumber;
    private String postCode;

    public DeliveryAddressDto(GlobalAddressBook globalAddressBook) {
        this.addressId=globalAddressBook.getAddressId();
        this.firstName = globalAddressBook.getFirstName();
        this.lastName = globalAddressBook.getLastName();
        this.companyName = globalAddressBook.getCompanyName();
        this.city = globalAddressBook.getCity();
        this.street = globalAddressBook.getStreet();
        this.houseNumber =globalAddressBook.getHouseNumber();
        this.apartmentNumber = globalAddressBook.getApartmentNumber();
        this.postCode = globalAddressBook.getPostCode();
    }
}

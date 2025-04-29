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
public class InvoiceAddressDto {
    private Long addressId;
    private String companyName;
    private String firstName;
    private String lastName;
    private String nip;
    private String street;
    private String city;
    private String houseNumber;
    private String apartmentNumber;
    private String postCode;

    public InvoiceAddressDto(GlobalAddressBook globalAddressBook) {
        this.firstName = globalAddressBook.getFirstName();
        this.lastName = globalAddressBook.getLastName();
        this.addressId=globalAddressBook.getAddressId();
        this.companyName = globalAddressBook.getCompanyName();
        this.nip = globalAddressBook.getNip();
        this.city = globalAddressBook.getCity();
        this.street = globalAddressBook.getStreet();
        this.houseNumber =globalAddressBook.getHouseNumber();
        this.apartmentNumber = globalAddressBook.getApartmentNumber();
        this.postCode = globalAddressBook.getPostCode();
    }
}

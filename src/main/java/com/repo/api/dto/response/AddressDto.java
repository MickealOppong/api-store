package com.repo.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {

    List<InvoiceAddressDto> invoiceAddressList = new ArrayList<>();
    List<DeliveryAddressDto> deliveryAddressList = new ArrayList<>();
}

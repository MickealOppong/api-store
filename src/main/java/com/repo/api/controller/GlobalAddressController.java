package com.repo.api.controller;

import com.repo.api.dto.CustomerAddressDto;
import com.repo.api.dto.ResponseDto;
import com.repo.api.service.GlobalAddressService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class GlobalAddressController {

    private final GlobalAddressService globalAddressService;

    public GlobalAddressController(GlobalAddressService globalAddressService) {
        this.globalAddressService = globalAddressService;
    }
    @GetMapping("/address")
    public ResponseDto<Object> customerAddress(Long customerId){
        return   globalAddressService.addressList(customerId);
    }
    @GetMapping("/delivery/{customerId}")
    public ResponseDto<Object> customerDeliveryAddress(Long customerId){
        return   globalAddressService.getCustomerDeliveryAddress(customerId);
    }
    @GetMapping("/invoice/{customerId}")
    public ResponseDto<Object> customerInvoiceAddress(Long customerId){
        return   globalAddressService.getCustomerInvoiceAddress(customerId);
    }

    @PostMapping("address")
    public ResponseDto<Object> addEditAddress(@RequestBody CustomerAddressDto customerAddressDto){
      return   globalAddressService.addEditCustomerAddress(customerAddressDto);
    }
    @DeleteMapping("/address/{addressId}")
    public ResponseDto<Object> deleteAddress(Long addressId){
        return   globalAddressService.deleteCustomerAddress(addressId);
    }
}

package com.repo.api.controller;

import com.repo.api.dto.CustomerDto;
import com.repo.api.dto.ResponseDto;
import com.repo.api.model.user.Customer;
import com.repo.api.service.CustomerDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users")
public class CustomerController {

    private final CustomerDetailsService customerDetailsService;

    public CustomerController(CustomerDetailsService customerDetailsService) {
        this.customerDetailsService = customerDetailsService;
    }



    @GetMapping("/user")
    public CustomerDto getCustomer(String username){

        Customer customer= customerDetailsService.getCustomerByUsername(username);

        return CustomerDto.builder()
                .accountCreatedAt(customer.getCreatedAt())
                .lastLogin(customer.getLastLogin())
                .peselNumber(customer.getPeselNumber())
                .username(customer.getUsername())
                .accountNumber(customer.getAccountNumber())
                .paymentAccount(customer.getPaymentAccountInformation().getAccountNumber())
                .name(customer.getFirstName()+" "+ customer.getLastName())
                .telephone(customer.getTelephone())
                .build();
    }

    @PatchMapping("/telephone")
    public ResponseDto<Object> updateTelephone(Long userId,String telephone){
      return customerDetailsService.updateTelephone(userId,telephone);
    }

    @PatchMapping("/password")
    public ResponseDto<Object> updatePassword(Long userId,String currentPassword,String newPassword){
        return customerDetailsService.updatePassword(userId,currentPassword,newPassword);
    }


}

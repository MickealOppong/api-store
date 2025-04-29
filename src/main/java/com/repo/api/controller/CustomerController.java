package com.repo.api.controller;

import com.repo.api.dto.ResponseDto;
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

    @GetMapping("/{customerId}")
    public ResponseDto<Object> getCustomer(Long customerId){
        return customerDetailsService.getCustomerById(customerId);
    }

    @PatchMapping("/telephone")
    public ResponseDto<Object> updateTelephone(Long userId,String telephone){
      return customerDetailsService.updateTelephone(userId,telephone);
    }

    @PatchMapping("/password")
    public ResponseDto<Object> updatePassword(Long userId,String currentPassword,String newPassword){
        return customerDetailsService.updatePassword(userId,currentPassword,newPassword);
    }

    @PatchMapping("/editName")
    public ResponseDto<Object> updateFirstNameAndLastName(Long userId,String firstName,String lastName){
        return customerDetailsService.updateName(userId,firstName,lastName);
    }
    @PatchMapping("/username")
    public ResponseDto<Object> updateUsername(Long userId,String newUsername){
        return customerDetailsService.updateUsername(userId,newUsername);
    }
}

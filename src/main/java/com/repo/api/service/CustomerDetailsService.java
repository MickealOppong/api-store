package com.repo.api.service;

import com.repo.api.dto.CustomerAddressDto;
import com.repo.api.dto.CustomerDto;
import com.repo.api.dto.ResponseDto;
import com.repo.api.dto.request.UserRegistrationRequest;
import com.repo.api.model.user.Customer;
import com.repo.api.repository.CustomerRepository;
import com.repo.api.repository.RolesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
public class CustomerDetailsService implements UserDetailsService {

    private static final double CREDIT_LIMIT= 2000;
    private final CustomerRepository customerRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConsentPrivacyService consentPrivacyService;
    private final GlobalAddressService globalAddressService;

    public CustomerDetailsService(CustomerRepository customerRepository,RolesRepository rolesRepository,
                                  @Lazy PasswordEncoder passwordEncoder,
                                  ConsentPrivacyService consentPrivacyService,GlobalAddressService globalAddressService){
        this.customerRepository = customerRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.consentPrivacyService = consentPrivacyService;
        this.globalAddressService=globalAddressService;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Customer customer = customerRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User does ot exist"));
        return  new User(customer.getUsername(),customer.getPassword(),customer.getAuthorities());
    }


    public Customer getCustomer(Long customerId){
        return customerRepository.findById(customerId).
                orElse(null);
    }
    public ResponseDto<Object> getCustomerById(Long customerId){
       try{

           Customer customer = customerRepository.findById(customerId)
                   .orElse(null);
           if(customer!=null){

               CustomerDto customerDto = CustomerDto.builder()
                       .username(customer.getUsername())
                       .telephone(customer.getTelephone())
                       .firstName(customer.getFirstName())
                       .lastName(customer.getLastName())
                       .lastLogin(customer.getLastLogin())
                       .customerDeliveryAddress((List<CustomerAddressDto>) globalAddressService
                               .getCustomerDeliveryAddress(customerId).getData())
                       .customerInvoiceAddress((List<CustomerAddressDto>) globalAddressService
                               .getCustomerInvoiceAddress(customerId).getData())
                       .build();
               return ResponseDto.builder()
                       .data(customerDto)
                       .message("Success")
                       .httpStatus(HttpStatus.OK)
                       .build();
           }
           return ResponseDto.builder()
                   .data(null)
                   .message("No record found")
                   .httpStatus(HttpStatus.NOT_FOUND)
                   .build();
       }catch (Exception e){
           return ResponseDto.builder()
                   .data(null)
                   .message(e.getMessage())
                   .httpStatus(HttpStatus.BAD_REQUEST)
                   .build();
       }
    }
    public ResponseDto<Object> getCustomerByUsername(String username){
        try{

            Customer customer = customerRepository.findByUsername(username)
                    .orElse(null);
            if(customer!=null){
                return ResponseDto.builder()
                        .data(customer)
                        .message("Success")
                        .httpStatus(HttpStatus.OK)
                        .build();
            }
            return ResponseDto.builder()
                    .data(null)
                    .message("No record found")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .data(null)
                    .message(e.getMessage())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }
    public ResponseDto<Object> addUser(UserRegistrationRequest userRegistrationRequest){
            try{
                Customer isUserAlreadyExist =customerRepository
                        .findByUsername(userRegistrationRequest.username()).orElse(null);

                if (isUserAlreadyExist ==null){

                    Customer newCustomer =  Customer.builder()
                            .firstName(userRegistrationRequest.firstName())
                            .lastName(userRegistrationRequest.lastName())
                            .password(passwordEncoder.encode(userRegistrationRequest.password()))
                            .username(userRegistrationRequest.username())
                            .customerRoles(Set.of(Objects.requireNonNull(rolesRepository.findByRole("USER").orElse(null))))
                            .build();
                    newCustomer.userDefaultSettings();
                    Customer customer= customerRepository.save(newCustomer);
                    //default user consents
                    consentPrivacyService.saveConsent(customer);
                    //default user privacy
                    consentPrivacyService.savePrivacy(customer);
                    return ResponseDto.builder()
                            .httpStatus(HttpStatus.OK)
                            .message("Success")
                            .data(true)
                            .build();
                }
                return ResponseDto.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("Found duplicate")
                        .data(false)
                        .build();
            }catch (Exception e){
                return ResponseDto.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message(e.getMessage())
                        .data(null)
                        .build();
            }
    }

    public void updateLastLogin(String username){
      Customer loginCustomer= customerRepository.findByUsername(username).orElse(null);
       if(loginCustomer!=null){
           loginCustomer.setLastLogin(Instant.now());
           customerRepository.save(loginCustomer);
       }
    }

    public ResponseDto<Object> updateTelephone(Long id,String telephone){

       try{

            Customer retrieveCustomer= customerRepository.findById(id).orElse(null);
            if(retrieveCustomer !=null){
                retrieveCustomer.setTelephone(telephone);
                 customerRepository.save(retrieveCustomer);
                return ResponseDto.builder()
                        .data(true)
                        .message("Telephone number updated")
                        .httpStatus(HttpStatus.OK)
                        .build();
            }

            return ResponseDto.builder()
                    .data(false)
                    .message("Account does not exist")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .message("Oops, something went wrong. Please try again")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build();
        }
    }
    public ResponseDto<Object> updateName(Long id,String firstName,String lastName){

        try{

            Customer retrieveCustomer= customerRepository.findById(id).orElse(null);
            if(retrieveCustomer !=null){
                retrieveCustomer.setFirstName(firstName);
                retrieveCustomer.setLastName(lastName);

                customerRepository.save(retrieveCustomer);
                return ResponseDto.builder()
                        .data(true)
                        .message("Name updated")
                        .httpStatus(HttpStatus.OK)
                        .build();
            }

            return ResponseDto.builder()
                    .data(false)
                    .message("Account does not exist")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .message("Oops, something went wrong. Please try again")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build();
        }
    }
    public ResponseDto<Object> updateUsername(Long id,String newUsername){

        try{

            Customer retrieveCustomer= customerRepository.findById(id).orElse(null);
            if(retrieveCustomer !=null){
                retrieveCustomer.setUsername(newUsername);

                customerRepository.save(retrieveCustomer);
                return ResponseDto.builder()
                        .data(true)
                        .message("Username updated")
                        .httpStatus(HttpStatus.OK)
                        .build();
            }

            return ResponseDto.builder()
                    .data(false)
                    .message("Account does not exist")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .message("Oops, something went wrong. Please try again")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build();
        }
    }
    public ResponseDto<Object> updatePassword(Long id, String currentPassword, String newPassword){
        try{
            Customer retrieveCustomer= customerRepository.findById(id).orElse(null);
            if(retrieveCustomer !=null){
                if(passwordEncoder.matches(currentPassword,retrieveCustomer.getPassword())){
                    retrieveCustomer.setPassword(passwordEncoder.encode(newPassword));
                    customerRepository.save(retrieveCustomer);
                    return ResponseDto.builder()
                            .data(true)
                            .message("Password updated")
                            .httpStatus(HttpStatus.OK)
                            .build();
                }
                return ResponseDto.builder()
                        .data(false)
                        .message("Incorrect password")
                        .httpStatus(HttpStatus.OK)
                        .build();
            }
            return ResponseDto.builder()
                    .data(null)
                    .message("Account does not  exist").httpStatus(HttpStatus.OK)
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .message("Oops, something went wrong. Please try again")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build();
        }
    }


}

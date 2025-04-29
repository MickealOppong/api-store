package com.repo.api.service;

import com.repo.api.dto.CustomerAddressDto;
import com.repo.api.dto.ResponseDto;
import com.repo.api.dto.response.AddressDto;
import com.repo.api.dto.response.DeliveryAddressDto;
import com.repo.api.dto.response.InvoiceAddressDto;
import com.repo.api.enums.AddressType;
import com.repo.api.model.util.GlobalAddressBook;
import com.repo.api.repository.GlobalAddressRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GlobalAddressService {


    private final GlobalAddressRepository globalAddressRepository;

    public GlobalAddressService(GlobalAddressRepository globalAddressRepository) {

        this.globalAddressRepository = globalAddressRepository;
    }

    public ResponseDto<Object> addressList(Long customerId){

      try{
          List<InvoiceAddressDto> invoiceAddressDto=  globalAddressRepository.findAllByCustomerId(customerId).stream()
                  .filter(addressType->addressType.getAddressType().equalsIgnoreCase(AddressType.INVOICE.name()))
                  .map(InvoiceAddressDto::new).toList();
          List<DeliveryAddressDto> deliveryAddressDto=  globalAddressRepository.findAllByCustomerId(customerId).stream()
                  .filter(addressType->addressType.getAddressType().equalsIgnoreCase(AddressType.DELIVERY.name()))
                  .map(DeliveryAddressDto::new).toList();
          AddressDto addressDto = AddressDto.builder()
                  .invoiceAddressList(invoiceAddressDto)
                  .deliveryAddressList(deliveryAddressDto)
                  .build();

          return ResponseDto.builder()
                  .data(addressDto)
                  .message("Success")
                  .httpStatus(HttpStatus.OK)
                  .build();
      }catch (Exception e){
          return ResponseDto.builder()
                  .data(null)
                  .message(e.getMessage())
                  .httpStatus(HttpStatus.BAD_REQUEST)
                  .build();
      }
    }

    public ResponseDto<Object> addEditCustomerAddress(CustomerAddressDto customerAddressDto){

        try{

           GlobalAddressBook retrievedAddress = globalAddressRepository.findById(customerAddressDto.getAddressId()).orElse(null);
           if(retrievedAddress!=null){

               //update address with  new data
               if(customerAddressDto.getNip() != null){
                   retrievedAddress.setNip(customerAddressDto.getNip());
               }
               if(customerAddressDto.getStreet() != null){
                   retrievedAddress.setStreet(customerAddressDto.getStreet());
               }
               if(customerAddressDto.getHouseNumber() != null){
                   retrievedAddress.setHouseNumber(customerAddressDto.getHouseNumber());
               }
               if(customerAddressDto.getApartmentNumber() != null){
                   retrievedAddress.setApartmentNumber(customerAddressDto.getApartmentNumber());
               }
               if(customerAddressDto.getCompanyName() != null){
                   retrievedAddress.setCompanyName(customerAddressDto.getCompanyName());
               }

               if(customerAddressDto.getPostCode() != null){
                   retrievedAddress.setPostCode(customerAddressDto.getPostCode());
               }
               if(customerAddressDto.getFirstName() != null){
                   retrievedAddress.setFirstName(customerAddressDto.getFirstName());
               }
               if(customerAddressDto.getLastName() != null){
                   retrievedAddress.setLastName(customerAddressDto.getLastName());
               }
               if(customerAddressDto.getCity()!= null){
                   retrievedAddress.setCity(customerAddressDto.getCity());
               }


               //commit to database
               globalAddressRepository.save(retrievedAddress);
               return ResponseDto.builder()
                       .httpStatus(HttpStatus.OK)
                       .data(true)
                       .message("Address updated")
                       .build();
           }
            GlobalAddressBook globalAddressBook = GlobalAddressBook.builder()
                    .addressType(customerAddressDto.getAddressType())
                    .nip(customerAddressDto.getNip())
                    .companyName(customerAddressDto.getCompanyName())
                    .customerId(customerAddressDto.getCustomerId())
                    .houseNumber(customerAddressDto.getHouseNumber())
                    .city(customerAddressDto.getCity())
                    .apartmentNumber(customerAddressDto.getApartmentNumber())
                    .street(customerAddressDto.getStreet())
                    .postCode(customerAddressDto.getPostCode())
                    .firstName(customerAddressDto.getFirstName())
                    .lastName(customerAddressDto.getLastName())
                    .build();
            globalAddressRepository.save(globalAddressBook);
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.OK)
                    .data(true)
                    .message("Success")
                    .build();

        }catch (Exception e){
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .message(e.getMessage())
                    .build();
        }
    }


    public ResponseDto<Object> updateCustomerAddress(Long id,CustomerAddressDto customerAddressDto){

        try{

          GlobalAddressBook addressBook=  globalAddressRepository.findById(id).orElse(null);
           if(addressBook !=null){
               addressBook.setNip(customerAddressDto.getNip());
               addressBook.setStreet(customerAddressDto.getStreet());
               addressBook.setCompanyName(customerAddressDto.getCompanyName());
               addressBook.setApartmentNumber(customerAddressDto.getApartmentNumber());
               addressBook.setStreet(customerAddressDto.getStreet());
               addressBook.setHouseNumber(customerAddressDto.getHouseNumber());
               addressBook.setFirstName(customerAddressDto.getFirstName());
               addressBook.setLastName(customerAddressDto.getLastName());
               addressBook.setPostCode(customerAddressDto.getPostCode());

               globalAddressRepository.save(addressBook);
               return ResponseDto.builder()
                       .httpStatus(HttpStatus.OK)
                       .data(true)
                       .message("Success")
                       .build();
           }
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .data(null)
                    .message("No record found")
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .message(e.getMessage())
                    .build();
        }
    }
    public ResponseDto<Object> deleteCustomerAddress(Long id){

        try{

            GlobalAddressBook addressBook=  globalAddressRepository.findById(id).orElse(null);
            if(addressBook !=null){

                globalAddressRepository.delete(addressBook);
                return ResponseDto.builder()
                        .httpStatus(HttpStatus.OK)
                        .data(true)
                        .message("Success")
                        .build();
            }
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .data(null)
                    .message("No record found")
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .message(e.getMessage())
                    .build();
        }
    }
    public ResponseDto<Object> getCustomerInvoiceAddress(Long customerId){

        try{

            List<CustomerAddressDto> addressBook=  globalAddressRepository.findAllByCustomerId(customerId)
                    .stream().filter(address-> address.getAddressType().equalsIgnoreCase("invoice")).map(CustomerAddressDto::new).toList();
                return ResponseDto.builder()
                        .httpStatus(HttpStatus.OK)
                        .data(addressBook)
                        .message("Success")
                        .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .message(e.getMessage())
                    .build();
        }
    }


    public ResponseDto<Object> getCustomerDeliveryAddress(Long customerId){

        try{

            List<CustomerAddressDto> addressBook=  globalAddressRepository.findAllByCustomerId(customerId)
                    .stream().filter(address-> address.getAddressType().equalsIgnoreCase("delivery")).map(CustomerAddressDto::new).toList();
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.OK)
                    .data(addressBook)
                    .message("Success")
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .message(e.getMessage())
                    .build();
        }
    }
}

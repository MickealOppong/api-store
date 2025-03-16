package com.repo.api.service;

import com.repo.api.dto.ResponseDto;
import com.repo.api.dto.response.ConsentResponse;
import com.repo.api.dto.response.PrivacyResponse;
import com.repo.api.enums.ConsentType;
import com.repo.api.enums.PrivacyType;
import com.repo.api.model.user.Customer;
import com.repo.api.repository.ConsentRepository;
import com.repo.api.repository.CustomerRepository;
import com.repo.api.repository.PrivacyPolicyRepository;
import com.repo.api.util.Consent;
import com.repo.api.util.PrivacyPolicy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ConsentPrivacyService {

    private final ConsentRepository consentRepository;
    private final CustomerRepository customerRepository;
    private final PrivacyPolicyRepository privacyPolicyRepository;

    public ConsentPrivacyService(ConsentRepository consentRepository,
                                 CustomerRepository customerRepository,
                                 PrivacyPolicyRepository privacyPolicyRepository
                          ) {
        this.consentRepository = consentRepository;
        this.customerRepository = customerRepository;
        this.privacyPolicyRepository
                 = privacyPolicyRepository;
    }

    public ResponseDto<Object> allConsent(Long userId){
        try{
            List<ConsentResponse> consentResponseList = new ArrayList<>();
            for(Consent userConsent :consentRepository.findByUserId(userId)){
                ConsentResponse consentResponse = ConsentResponse.builder()
                        .consentType(userConsent.getConsentType())
                        .id(userConsent.getId())
                        .email(userConsent.isEmail())
                        .sms(userConsent.isSms())
                        .build();
                consentResponseList.add((consentResponse));
            }
            return ResponseDto.builder()
                    .data(consentResponseList)
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
    public ResponseDto<Object> allPrivacy(Long userId){
        try{
            List<PrivacyResponse> privacyResponseList = new ArrayList<>();
            for(PrivacyPolicy userPrivacy :privacyPolicyRepository.findByUserId(userId)){
                PrivacyResponse privacyResponse = PrivacyResponse.builder()
                        .active(userPrivacy.isActive())
                        .id(userPrivacy.getId())
                        .text(userPrivacy.getText())
                        .privacyType(userPrivacy.getPrivacyType())
                        .build();
                privacyResponseList.add((privacyResponse));
            }
            return ResponseDto.builder()
                    .data(privacyResponseList)
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

    public ResponseDto<Object> saveConsent(Customer customer){
        try{
            Consent general = Consent.builder()
                    .consentType(ConsentType.ACCOUNT_ACTIVITIES.getDescription())
                    .email(true)
                    .sms(true)
                    .customer(customer)
                    .build();
            Consent marketingAndPromotions = Consent.builder()
                    .consentType(ConsentType.MARKETING_PROMOTIONS.getDescription())
                    .email(false)
                    .sms(false)
                    .customer(customer)
                    .build();
            consentRepository.saveAll(List.of(marketingAndPromotions,general));

            return ResponseDto.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Record updated")
                    .data(true)
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .message(e.getMessage())
                    .data(true)
                    .build();
        }
    }
    public ResponseDto<Object> savePrivacy(Customer customer){
        try{
            PrivacyPolicy targetMarketing= PrivacyPolicy.builder()
                    .privacyType(PrivacyType.TARGETED_MARKETING.getDescription())
                    .active(false)
                    .customer(customer)
                    .text("Zaba works with marketing partners " +
                            "to connect with you on other websites. " +
                            "This makes the ads you see on other" +
                            " websites more relevant to your" +
                            " interests and provides us advertising input. " +
                            "By disabling Targeted Advertising, we will " +
                            "not share your personal information for targeted advertising purposes.")
                    .build();

            PrivacyPolicy necessary= PrivacyPolicy.builder()
                    .privacyType(PrivacyType.STRICTLY_NECESSARY.getDescription())
                    .active(true)
                    .customer(customer)
                    .text("Strictly necessary cookies\n" +
                            "These cookies are required for our Services" +
                            " and websites to operate and are always on")
                    .build();

            PrivacyPolicy functional= PrivacyPolicy.builder()
                    .privacyType(PrivacyType.FUNCTIONAL_COOKIES.getDescription())
                    .active(true)
                    .customer(customer)
                    .text("These cookies allow a website to remember choices the " +
                            "user has made in the past, like what language they prefer, " +
                            "what region they would like weather reports for, " +
                            "or what their username and password are so they can automatically log in")
                    .build();

            PrivacyPolicy analytical= PrivacyPolicy.builder()
                    .privacyType(PrivacyType.ANALYTICAL.getDescription())
                    .active(false)
                    .customer(customer)
                    .text("These cookies enhance our products and show us how" +
                            " you use our products, so we can make them better." +
                            " We also use these cookies to find and fix " +
                            "problems to make your customer experience even smoother.")
                    .build();
            PrivacyPolicy targeting= PrivacyPolicy.builder()
                    .privacyType(PrivacyType.TARGETING_ADVERTISING.getDescription())
                    .active(false)
                    .customer(customer)
                    .text("These cookies help us improve our marketing efforts, " +
                            "allowing us to spend less on platforms like Google or " +
                            "Facebook while making sure we serve you relevant advertising")
                    .build();
            privacyPolicyRepository.saveAll(List.of(targetMarketing,necessary,analytical,functional,targeting));

            return ResponseDto.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Record updated")
                    .data(true)
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .message(e.getMessage())
                    .data(true)
                    .build();
        }
    }

    public ResponseDto<Object> updatePrivacy(Long id,boolean active){
        try{
            PrivacyPolicy retrievePrivacy = privacyPolicyRepository.findById(id).orElse(null);
            if(retrievePrivacy !=null){
                retrievePrivacy.setActive(active);
                privacyPolicyRepository.save(retrievePrivacy);
                return ResponseDto.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("Record updated")
                        .data(true)
                        .build();
            }
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("Account does not exist")
                    .data(true)
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .message(e.getMessage())
                    .data(true)
                    .build();
        }

    }
    public ResponseDto<Object> updateEmailConsent(Long id,boolean email){
      try{
         Consent retrieveConsent = consentRepository.findById(id).orElse(null);
          if(retrieveConsent !=null){
              retrieveConsent.setEmail(email);
              consentRepository.save(retrieveConsent);
              return ResponseDto.builder()
                      .httpStatus(HttpStatus.OK)
                      .message("Record updated")
                      .data(true)
                      .build();
          }
          return ResponseDto.builder()
                  .httpStatus(HttpStatus.BAD_REQUEST)
                  .message("Account does not exist")
                  .data(true)
                  .build();
      }catch (Exception e){
          return ResponseDto.builder()
                  .httpStatus(HttpStatus.FORBIDDEN)
                  .message(e.getMessage())
                  .data(true)
                  .build();
      }

    }
    public ResponseDto<Object> updateSMSConsent(Long id,boolean sms){
        try{
            Consent retrieveConsent = consentRepository.findById(id).orElse(null);
            if(retrieveConsent !=null){
                retrieveConsent.setSms(sms);
                consentRepository.save(retrieveConsent);
                return ResponseDto.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("Record updated")
                        .data(true)
                        .build();
            }
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("Account does not exist")
                    .data(true)
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .message(e.getMessage())
                    .data(true)
                    .build();
        }

    }
}

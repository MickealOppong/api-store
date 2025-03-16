package com.repo.api.controller;

import com.repo.api.dto.ResponseDto;
import com.repo.api.service.ConsentPrivacyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/consent")
public class ConsentPrivacyController {


    private final ConsentPrivacyService consentPrivacyService;


    public ConsentPrivacyController(ConsentPrivacyService consentPrivacyService) {
        this.consentPrivacyService = consentPrivacyService;
    }

    @GetMapping("/consents")
    public ResponseDto<Object> allConsents(Long userId){
      return  consentPrivacyService.allConsent(userId);
    }

    @GetMapping("/privacy")
    public ResponseDto<Object> allPrivacyPolicies(Long userId){
        return  consentPrivacyService.allPrivacy(userId);
    }

    @PatchMapping("/privacy-type")
    public ResponseDto<Object> updatePrivacy(Long id,boolean active){
        return consentPrivacyService.updatePrivacy(id,active);
    }
    @PatchMapping("/sms")
    public ResponseDto<Object> updateSMS(Long id,boolean sms){
        return consentPrivacyService.updateSMSConsent(id,sms);
    }

    @PatchMapping("/email")
    public ResponseDto<Object> updateEmail(Long id,boolean email){
        return consentPrivacyService.updateEmailConsent(id,email);
    }
}

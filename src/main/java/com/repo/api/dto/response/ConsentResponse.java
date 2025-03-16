package com.repo.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ConsentResponse {

    private Long id;
    private String consentType;
    private boolean sms;
    private boolean email;
}

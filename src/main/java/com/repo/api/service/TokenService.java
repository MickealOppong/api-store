package com.repo.api.service;

import com.repo.api.model.user.Customer;
import com.repo.api.repository.CustomerRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TokenService {


    private final JwtEncoder jwtEncoder;
    private final CustomerRepository customerRepository;
    private final RefreshTokenService refreshTokenService;


    public TokenService(JwtEncoder jwtEncoder, CustomerRepository customerRepository, RefreshTokenService refreshTokenService) {
        this.jwtEncoder = jwtEncoder;
        this.customerRepository = customerRepository;
        this.refreshTokenService = refreshTokenService;
    }

    public Optional<String> token(Authentication authentication){

        // String scope = userRepository.findByUsername(authentication.getName())
        //       .map(AppUser::getAuthorities).get().toString();
        Customer appUser =customerRepository.findByUsername(authentication.getName()).orElse(null);
        if(appUser!=null){
            Set<String> roles = AuthorityUtils.authorityListToSet(appUser.getAuthorities())
                    .stream()
                    .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
            JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                    .issuer("local")
                    .issuedAt(Instant.now())
                    .expiresAt(Instant.now().plus(200, ChronoUnit.MINUTES))
                    .subject(authentication.getName())
                    .claim("scope",roles)
                    .build();
            return Optional.of(jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue());
        }
        return Optional.empty();
    }

    public Optional<String> token(String username){

        // String scope = userRepository.findByUsername(authentication.getName())
        //       .map(AppUser::getAuthorities).get().toString();
        Customer appUser =customerRepository.findByUsername(username).orElse(null);
        if(appUser!=null){
            Set<String> roles = AuthorityUtils.authorityListToSet(appUser.getAuthorities())
                    .stream()
                    .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
            JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                    .issuer("local")
                    .issuedAt(Instant.now())
                    .expiresAt(Instant.now().plus(200, ChronoUnit.MINUTES))
                    .subject(appUser.getUsername())
                    .claim("scope",roles)
                    .build();
            return Optional.of(jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue());
        }
        return Optional.empty();
    }
}

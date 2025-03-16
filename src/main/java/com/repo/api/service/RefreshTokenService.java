package com.repo.api.service;

import com.repo.api.model.user.Customer;
import com.repo.api.model.user.RefreshToken;
import com.repo.api.repository.CustomerRepository;
import com.repo.api.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomerRepository customerRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository,
                               CustomerRepository customerRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.customerRepository = customerRepository;
    }

    public Optional<RefreshToken> findToken(String token){
        return refreshTokenRepository.findByRefreshToken(token);
    }

    public RefreshToken createToken(String username){
        RefreshToken refreshToken = RefreshToken.builder()
                .customer(customerRepository.findByUsername(username)
                        .orElseThrow(()->new UsernameNotFoundException("Could not find "+username)))
                .expiredAt(Instant.now().plus(20, ChronoUnit.MINUTES))
                .refreshToken(UUID.randomUUID().toString())
                .issuedAt(Instant.now())
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public RefreshToken newToken(String token,String username){
        //delete old token
        removeToken(token);
        //create new token
        RefreshToken refreshToken = RefreshToken.builder()
                .customer(customerRepository.findByUsername(username)
                        .orElseThrow(()->new UsernameNotFoundException("Could not find "+username)))
                .issuedAt(Instant.now())
                .expiredAt(Instant.now().plus(20, ChronoUnit.MINUTES))
                .refreshToken(UUID.randomUUID().toString())
                .build();
        //return new token to user

        return refreshTokenRepository.save(refreshToken);
    }


    public boolean isTokenExpired(RefreshToken refreshToken){
        return refreshToken.getExpiredAt().isBefore(Instant.now());
    }

    public boolean removeToken(String refreshToken){
        RefreshToken token = refreshTokenRepository.findByRefreshToken(refreshToken).orElse(null);
       if(token!=null){
           refreshTokenRepository.delete(token);
           return true;
       }
       return false;
    }

    public Optional<RefreshToken> getByUsername(String username){
        Optional<Customer> appUser = customerRepository.findByUsername(username);
        return appUser.flatMap(user -> refreshTokenRepository.findById(user.getId()));
    }
}

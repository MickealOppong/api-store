package com.repo.api.repository;

import com.repo.api.model.user.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {


    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}

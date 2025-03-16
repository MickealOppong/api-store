package com.repo.api.repository;

import com.repo.api.util.Consent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsentRepository extends CrudRepository<Consent,Long> {

    Optional<Consent> findByConsentType(String consentType);

    @Query(value = "SELECT * from consent c WHERE c.fk_id=?",nativeQuery = true)
    List<Consent> findByUserId(Long userId);
}

package com.repo.api.repository;

import com.repo.api.util.PrivacyPolicy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrivacyPolicyRepository extends CrudRepository<PrivacyPolicy,Long> {

    Optional<PrivacyPolicy> findByPrivacyType(String privacyType);

    @Query(value = "SELECT * from privacy c WHERE c.fk_id=?",nativeQuery = true)
    List<PrivacyPolicy> findByUserId(Long userId);
}

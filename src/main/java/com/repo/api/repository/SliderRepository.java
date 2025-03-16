package com.repo.api.repository;

import com.repo.api.model.utl.Slider;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SliderRepository extends CrudRepository<Slider,Long> {
    Optional<Slider> findByMessage(String message);
}

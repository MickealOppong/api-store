package com.repo.api.repository;

import com.repo.api.util.Photo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoRepository extends CrudRepository<Photo,Long> {

    Optional<Photo> findByFileName(String fileName);
    Optional<Photo> findByPath(String path);
    List<Photo> findByProductRecId(Long recId);

    Optional<Photo> findByCategoryRecId(Long recId);

    Optional<Photo> findBySliderRecId(Long recId);
}

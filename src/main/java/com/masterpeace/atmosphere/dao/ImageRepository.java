package com.masterpeace.atmosphere.dao;

import com.masterpeace.atmosphere.model.Image;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Implemented by classes that access the Image datasource
 */
public interface ImageRepository extends CrudRepository<Image, Long> {
    List<Image> findByUserGroupId(long userGroupId);
}

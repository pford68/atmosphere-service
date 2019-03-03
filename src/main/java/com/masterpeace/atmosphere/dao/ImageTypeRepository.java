package com.masterpeace.atmosphere.dao;

import com.masterpeace.atmosphere.model.ImageType;
import org.springframework.data.repository.CrudRepository;

/**
 *
 */
// PF:  Eventually this probably should extend PagingAndSortingRepository.
public interface ImageTypeRepository extends CrudRepository<ImageType, Long>{
}

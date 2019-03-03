package com.masterpeace.atmosphere.dao;

import com.masterpeace.atmosphere.model.Limit;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Implemented by classes that acccess the table containing usage limit information
 */
public interface LimitRepository extends CrudRepository<Limit, Long>{
    List<Limit> findByUserId(long userId);
}

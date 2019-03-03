package com.masterpeace.atmosphere.dao;

import com.masterpeace.atmosphere.model.Provider;
import org.springframework.data.repository.CrudRepository;

/**
 * Implemented by classes that access the Provider data store
 */
public interface ProviderRepository extends CrudRepository<Provider, Long>{
}

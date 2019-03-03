package com.masterpeace.atmosphere.dao;

import com.masterpeace.atmosphere.model.inventory.InstanceOption;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *
 */
public interface InstanceOptionRepository extends CrudRepository<InstanceOption, Long>{

    List<InstanceOption> findByImageId(long imageTypeId);
}

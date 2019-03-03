package com.masterpeace.atmosphere.dao;

import com.masterpeace.atmosphere.model.Instance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *  Implemented by classes that access the Instances table.
 */
public interface InstanceRepository extends JpaRepository<Instance, Long> {
    List<Instance> findByUserGroupId(long groupId);

    List<Instance> findByUserGroupIdInOrderByIdAsc(List<Long> ids);

    List<Instance> findByRegionAndUserGroupIdIn(long timezone, List<Long> ids);
}

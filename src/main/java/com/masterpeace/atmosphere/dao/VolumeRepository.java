package com.masterpeace.atmosphere.dao;

import com.masterpeace.atmosphere.model.Volume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * For accessing Volume information
 */
public interface VolumeRepository extends JpaRepository<Volume, Long> {

    List<Volume> findByUserGroupId(long groupId);

    List<Volume> findByUserGroupIdInOrderByIdAsc(List<Long> ids);
}

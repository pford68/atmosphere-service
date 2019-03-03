package com.masterpeace.atmosphere.dao;

import com.masterpeace.atmosphere.model.Snapshot;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Implemented by classes that access the Snapshot table.
 */
public interface SnapshotRepository extends CrudRepository<Snapshot, Long> {
/*
    List<Snapshot> findByVolumeId(long volumeId);

    @Query("select s from Snapshot s where s.createdDate = (select max(s.createdDate) from Snapshot s where s.volume.id = :volumeId)")
    Snapshot findLastByVolumeId(@Param("volumeId") long volumeId);*/
}

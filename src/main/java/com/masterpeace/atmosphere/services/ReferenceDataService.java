package com.masterpeace.atmosphere.services;

import com.masterpeace.atmosphere.dao.*;
import com.masterpeace.atmosphere.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service for accessing reference data--security groups, user groups, snapshots, etc.
 */
@Service
@Transactional
public class ReferenceDataService {

    private final SecurityGroupRepository securityGroupRepository;
    private final UserGroupRepository userGroupRepository;
    private final SnapshotRepository snapshotRepository;
    private final RegionRepository regionRepository;
    private final VolumeTypeRepository volumeTypeRepository;

    @Autowired
    public ReferenceDataService(SecurityGroupRepository securityGroupRepository,
                                UserGroupRepository userGroupRepository,
                                SnapshotRepository snapshotRepository,
                                RegionRepository regionRepository,
                                VolumeTypeRepository volumeTypeRepository){
        this.regionRepository = regionRepository;
        this.securityGroupRepository = securityGroupRepository;
        this.snapshotRepository = snapshotRepository;
        this.userGroupRepository = userGroupRepository;
        this.volumeTypeRepository = volumeTypeRepository;
    }


    public Iterable<Region> getRegions(){
        return this.regionRepository.findAll();
    }

    public Iterable<Snapshot> getSnapshots(){
        return this.snapshotRepository.findAll();
    }

    public Iterable<SecurityGroup> getSecurityGroups(){
        return this.securityGroupRepository.findAll();
    }

    public Iterable<UserGroup> getUserGroups(){
        return this.userGroupRepository.findAll();
    }

    public Iterable<VolumeType> getVolumeTypes() { return this.volumeTypeRepository.findAll(); }
}


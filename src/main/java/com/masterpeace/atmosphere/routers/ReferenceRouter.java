package com.masterpeace.atmosphere.routers;

import com.masterpeace.atmosphere.dto.UserGroupDto;
import com.masterpeace.atmosphere.model.*;
import com.masterpeace.atmosphere.services.ReferenceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/reference")
public class ReferenceRouter {

    private final ReferenceDataService service;

    @Autowired
    public ReferenceRouter(ReferenceDataService service){
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Reference getAll(){
        List<UserGroupDto> groups = new ArrayList<UserGroupDto>();
        this.service.getUserGroups().forEach(grp -> groups.add(new UserGroupDto(grp)));
        return new Reference(this.service.getSnapshots(),
                this.service.getRegions(),
                this.service.getSecurityGroups(),
                groups,
                this.service.getVolumeTypes());
    }

}


class Reference {
    Iterable<Snapshot> snapshots;
    Iterable<Region> regions;
    Iterable<SecurityGroup> securityGroups;
    Iterable<UserGroupDto> userGroups;
    Iterable<VolumeType> volumeTypes;

    Reference(Iterable<Snapshot> snapshots,
              Iterable<Region> regions,
              Iterable<SecurityGroup> securityGroups,
              Iterable<UserGroupDto> userGroups,
              Iterable<VolumeType> volumeTypes) {
        this.regions = regions;
        this.securityGroups = securityGroups;
        this.snapshots = snapshots;
        this.userGroups = userGroups;
        this.volumeTypes = volumeTypes;
    }

    public Iterable<Snapshot> getSnapshots() {
        return snapshots;
    }

    public Iterable<Region> getRegions() {
        return regions;
    }

    public Iterable<SecurityGroup> getSecurityGroups() {
        return securityGroups;
    }

    public Iterable<UserGroupDto> getUserGroups() {
        return userGroups;
    }

    public Iterable<VolumeType> getVolumeTypes() {
        return volumeTypes;
    }
}

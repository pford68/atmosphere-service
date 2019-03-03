package com.masterpeace.atmosphere.routers;

import com.masterpeace.atmosphere.dao.ImageTypeRepository;
import com.masterpeace.atmosphere.dao.InstanceConfigurationOptionRepository;
import com.masterpeace.atmosphere.dao.InstanceOptionRepository;
import com.masterpeace.atmosphere.dao.StorageOptionRepository;
import com.masterpeace.atmosphere.model.ImageType;
import com.masterpeace.atmosphere.model.inventory.InstanceConfigurationOption;
import com.masterpeace.atmosphere.model.inventory.InstanceOption;
import com.masterpeace.atmosphere.model.inventory.StorageOption;
import com.masterpeace.atmosphere.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/inventory")
public class InventoryRouter {

    private final InventoryService service;


    @Autowired
    public InventoryRouter(InventoryService service) {
        this.service = service;
    }


    @RequestMapping(value="/images", method= RequestMethod.GET)
    public Iterable<ImageType> start() throws Exception {
        return this.service.getImageTypes();
    }


    @RequestMapping(value="/images/{imageTypeId}/instances", method=RequestMethod.GET)
    public List<InstanceOption> proceedToInstances(@PathVariable long imageTypeId) throws Exception {
        return this.service.getInstancesByImageType(imageTypeId);
    }

    @RequestMapping(value="/images/{imageTypeId}/instances/{instanceId}/options", method=RequestMethod.GET)
    public Iterable<InstanceConfigurationOption> configureInstance(@PathVariable long imageTypeId, @PathVariable long instanceId) throws Exception {
        /* PF:  Note that currently we have a standard set of options for all instances,
            so imageTypeId and instanceId are currently irrelevant, but that may not always be so. */
        return this.service.getConfigurationOptions(imageTypeId, instanceId);
    }

    @RequestMapping(value="/storage", method=RequestMethod.GET)
    public Iterable<StorageOption> configureStorage() throws Exception {
        return this.service.getStorageOptions();
    }

}

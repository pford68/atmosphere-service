package com.masterpeace.atmosphere.services;

import com.masterpeace.atmosphere.dao.ImageTypeRepository;
import com.masterpeace.atmosphere.dao.InstanceConfigurationOptionRepository;
import com.masterpeace.atmosphere.dao.InstanceOptionRepository;
import com.masterpeace.atmosphere.dao.StorageOptionRepository;
import com.masterpeace.atmosphere.model.ImageType;
import com.masterpeace.atmosphere.model.inventory.InstanceConfigurationOption;
import com.masterpeace.atmosphere.model.inventory.InstanceOption;
import com.masterpeace.atmosphere.model.inventory.StorageOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
@Service
@Transactional(readOnly = true)
public class InventoryService {

    private final ImageTypeRepository imageTypeRepository;
    private final InstanceOptionRepository instanceOptionRepository;
    private final StorageOptionRepository storageOptionRepository;
    private final InstanceConfigurationOptionRepository instanceConfigurationOptionRepository;


    @Autowired
    public InventoryService(ImageTypeRepository imageTypeRepository,
                            InstanceOptionRepository instanceOptionRepository,
                            StorageOptionRepository storageOptionRepository,
                            InstanceConfigurationOptionRepository instanceConfigurationOptionRepository){
        this.imageTypeRepository = imageTypeRepository;
        this.instanceOptionRepository = instanceOptionRepository;
        this.storageOptionRepository = storageOptionRepository;
        this.instanceConfigurationOptionRepository = instanceConfigurationOptionRepository;
    }


    public Iterable<ImageType> getImageTypes() throws Exception {
        return this.imageTypeRepository.findAll();
    }


    public List<InstanceOption> getInstancesByImageType(long imageTypeId) throws Exception {
        return this.instanceOptionRepository.findByImageId(imageTypeId);
    }

    public Iterable<InstanceConfigurationOption> getConfigurationOptions(long imageTypeId, long instanceId) throws Exception {
        return this.instanceConfigurationOptionRepository.findAll();
    }

    public Iterable<StorageOption> getStorageOptions() throws Exception {
        return this.storageOptionRepository.findAll();
    }

}

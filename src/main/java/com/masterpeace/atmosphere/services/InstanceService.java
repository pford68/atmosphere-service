package com.masterpeace.atmosphere.services;

import com.masterpeace.atmosphere.annotations.Protected;
import com.masterpeace.atmosphere.dao.InstanceRepository;
import com.masterpeace.atmosphere.dao.StateRepository;
import com.masterpeace.atmosphere.model.Dns;
import com.masterpeace.atmosphere.model.Instance;
import com.masterpeace.atmosphere.model.IpAddress;
import com.masterpeace.atmosphere.model.Volume;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.masterpeace.atmosphere.model.Instance.InstanceBuilder;

/**
 * Business tier services for accessing Instances (VMs).
 */
@Service
@Transactional(readOnly = true)
public class InstanceService {

    private final InstanceRepository repository;
    private final StateRepository stateRepository;
    private final DnsService dnsService;
    private final VolumeService volumeService;


    private static final Logger LOGGER = Logger.getLogger(InstanceService.class);



    @Autowired
    public InstanceService(InstanceRepository repository,
                           StateRepository stateRepository,
                           DnsService dnsService,
                           VolumeService volumeService){
        this.repository = repository;
        this.stateRepository = stateRepository;
        this.dnsService = dnsService;
        this.volumeService = volumeService;
    }



    /**
     * Returns all of the specified group's instances.
     *
     * @return              the list of instances belonging to this group
     */
    public List<Instance> getByGroupId(long groupId){
        return this.repository.findByUserGroupId(groupId);
    }


    /**
     *
     * @return              the instances belonging to all of the specified groups
     */
    public List<Instance> getAllGroupInstances(List<Long> groupIds) {
        return this.repository.findByUserGroupIdInOrderByIdAsc(groupIds);
    }

    /**
     *
     * @return              the instanes belong to all of the specified groups within the specified region
     */
    public List<Instance> getAllGroupInstancesInRegion(List<Long> groupIds, long region) {
        return this.repository.findByRegionAndUserGroupIdIn(region, groupIds);
    }


    /**
     * Returns a single instance by ID
     *
     * @param instanceId    the id of this instance
     * @return              the instance with the specified ID
     */
    public Instance getById(long instanceId){
        return this.repository.findOne(instanceId);
    }


    /**
     * Creates new instances.  Expects a List of instances
     *
     * @return              the instances that were created
     */
    @Transactional
    public List<Instance> saveAll(List<Instance> instances){
        List<Instance> _instances = new ArrayList<Instance>();
        Instance _instance = null;
        for (Instance instance : instances){
            IpAddress ip = dnsService.generateIp(true); //TODO:  send exposed value
            Dns dns = dnsService.generateDns(ip, true);
            _instance = new InstanceBuilder(instance)
                    .addDns(dns)
                    .addIp(ip).build();
            _instances.add(_instance);
        }
        this.repository.save(_instances);
        return _instances;  //Return the saved instances
    }

    @Transactional
    public Instance save(Instance instance){
        IpAddress ip = dnsService.generateIp(true); //TODO:  send exposed value
        Dns dns = dnsService.generateDns(ip, true);
        Instance _instance = new InstanceBuilder(instance)
                .addDns(dns)
                .addIp(ip).build();
        return this.repository.save(_instance);
    }


    /**
     * Updates a List of instances.
     *
     * @return              the updated instances
     */
    @Transactional
    public Instance updateState(long instanceId, int stateId){
        Instance _instance = new InstanceBuilder(this.repository.findOne(instanceId))
                .setState(this.stateRepository.findOne(stateId))
                .build();
        return this.repository.save(_instance);
    }


    /**
     * Updates a List of instances.
     *
     * @return              the updated instances
     */
    @Transactional
    public Instance updateState(Instance instance, int stateId){
        Instance _instance = new InstanceBuilder(instance)
                .setState(this.stateRepository.findOne(stateId))
                .build();
        return this.repository.save(_instance);
    }


    /**
     * Deletes this instance.
     *
     * @param instance      the instance to delete
     * @return              the deleted instance
     */
    @Protected
    @Transactional
    public Instance delete(Instance instance) throws Exception {
        // I am not sure we should detach volumes automatically.  PF 2015/04/23
        List<Volume> volumes = instance.getVolumes();
        for (Volume volume : volumes){
            volumeService.detach(volume.getId());
        }
        instance = new InstanceBuilder(instance).setStatus(null).build();
        this.repository.delete(instance);
        return instance;
    }


    /**
     * Starts this instance, setting its state to active
     *
     * @param instance      the instance to be started
     * @return
     */
    @Transactional
    public Instance start(Instance instance){
        return this.updateState(instance.getId(), Instance.ACTIVE_STATE);
    }

    /**
     * Stops this instance, chaning its state to inactive.
     *
     * @param instance      thie instance to be stopped
     * @return
     */
    @Transactional
    public Instance stop(Instance instance){
        return this.updateState(instance.getId(), Instance.INACTIVE_STATE);
    }


    /**
     * Susoends a protected instance, placing it in a state in which it can be deleted by a privileged user.
     *
     * @param instance      the instance to be suspended
     * @return
     */
    @Transactional
    public Instance suspend(Instance instance){
        return this.updateState(instance.getId(), Instance.SUSPENDED_STATE);
    }

}

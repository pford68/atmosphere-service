package com.masterpeace.atmosphere.routers;

import com.masterpeace.atmosphere.model.Instance;
import com.masterpeace.atmosphere.services.InstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * RESTful services for accessing Instances (VMs).
 */
@RestController
public class InstanceRouter {

    private final InstanceService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(InstanceRouter.class);


    @Autowired
    public InstanceRouter(InstanceService service) {
        this.service = service;
    }


    /**
     * Returns all of the specified group's instances.
     *
     * @return              the list of instances belonging to this group
     */
    @RequestMapping(value="/groups/{groupId}/instances", method=RequestMethod.GET)
    public List<Instance> getByGroupId(@PathVariable long groupId){
        return this.service.getByGroupId(groupId);
    }


    /**
     * Returns all of the instances in specified UserGroups
     *
     * @param groupIds        the IDs of the UserGroups
     * @return
     */
    @RequestMapping(value = "/groups/instances", method=RequestMethod.GET)
    public List<Instance> getByGroupIds(@RequestParam("ids") List<Long> groupIds){
        return this.service.getAllGroupInstances(groupIds);
    }


    /**
     * Returns a single instance by ID
     *
     * @param instanceId    the id of this instance
     * @return              the instance with the specified ID
     */
    @RequestMapping(value="/instances/{instanceId}", method=RequestMethod.GET)
    public Instance getById(@PathVariable long instanceId){
        return this.service.getById(instanceId);
    }


    /**
     * Creates new instances.  Expects a List of instances
     *
     * @return              the instances that were created
     */
    @RequestMapping(value="/instances", method=RequestMethod.POST)
    public List<Instance> create(@RequestBody List<Instance> instances){
        return this.service.saveAll(instances);
    }


    /**
     * Updates a List of instances.
     *
     * @return              the updated instances
     */
    @RequestMapping(value="/instances/{instanceId}/state/{stateId}", method=RequestMethod.PUT)
    public Instance updateState(@PathVariable long instanceId, @PathVariable int stateId){
        return this.service.updateState(instanceId, stateId);
    }


    /**
     *
     * @param instanceId    the id of the instance to delete
     * @return              the deleted instance
     */
    @RequestMapping(value="/instances/{instanceId}", method=RequestMethod.DELETE)
    public Instance delete(@PathVariable long instanceId) throws Exception{
        Instance instance = this.service.getById(instanceId);
        this.service.delete(instance);
        return instance;
    }

}

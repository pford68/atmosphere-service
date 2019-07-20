package com.masterpeace.atmosphere.routers;

import com.masterpeace.atmosphere.model.Volume;
import com.masterpeace.atmosphere.services.VolumeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



/**
 * RESTful services for accessing Volumes
 */
@RestController
public class VolumeRouter {

    private static final Logger LOGGER = LoggerFactory.getLogger(VolumeRouter.class);

    private final VolumeService service;


    @Autowired
    public VolumeRouter(VolumeService service) {
        this.service = service;
    }


    @RequestMapping(value="/groups/{groupId}/volumes", method=RequestMethod.GET)
    public List<Volume> getByGroupId(@PathVariable long groupId) {
        return this.service.getByGroupId(groupId);
    }


    /**
     * Returns all of the volumes in specified UserGroups
     *
     * @param groupIds        the IDs of the UserGroups
     * @return
     */
    @RequestMapping(value = "/groups/volumes", method=RequestMethod.GET)
    public List<Volume> getByGroupIds(@RequestParam("ids") List<Long> groupIds){
        return this.service.getAllGroupInstances(groupIds);
    }



    @RequestMapping(value="/volumes/{volumeId}", method=RequestMethod.GET)
    public Volume getById(@PathVariable long volumeId) {
        return this.service.getById(volumeId);
    }



    @RequestMapping(value="/volumes", method=RequestMethod.POST)
    public Iterable<Volume> create(@RequestBody List<Volume> volumes) {
        return this.service.save(volumes);
    }



    @RequestMapping(value="/volumes/{volumeId}/instances/{instanceId}", method=RequestMethod.PUT)
    public Volume attachToInstance(@RequestBody Volume volume, @PathVariable long volumeId, @PathVariable long instanceId) {
        return this.service.attach(volume, instanceId);
    }


    @RequestMapping(value="/volumes/{volumeId}/instances/{instanceId}", method=RequestMethod.DELETE)
    public Volume detachFromInstance(@PathVariable long volumeId, @PathVariable long instanceId) {
        return this.service.detach(volumeId);
    }



    @RequestMapping(value="/volumes/{volumeId}", method=RequestMethod.DELETE)
    public long delete(@PathVariable long volumeId) throws Exception {
        Volume volume = service.getById(volumeId);
        this.service.delete(volume);
        return volumeId;
    }


    @RequestMapping(value="/volumes/{volumeId}/snapshot", method=RequestMethod.POST)
    public Volume createSnapshot(@PathVariable long volumeId) {
        Volume volume = this.service.getById(volumeId);
        return this.service.takeSnapshot(volume);
    }

}

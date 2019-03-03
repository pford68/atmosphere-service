package com.masterpeace.atmosphere.routers;

import com.masterpeace.atmosphere.model.Image;
import com.masterpeace.atmosphere.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */
@RestController
public class ImageRouter {

    private final ImageService service;


    @Autowired
    public ImageRouter(ImageService service) {
        this.service = service;
    }


    @RequestMapping(value="/groups/{groupId}/images", method=RequestMethod.GET)
    public List<Image> getImagesByGroupId(@PathVariable long groupId) throws Exception {
        return this.service.getImagesByGroupId(groupId);
    }

    @RequestMapping(value="/images", method=RequestMethod.POST)
    public Iterable<Image> saveAll(@RequestBody List<Image> images) throws Exception {
        return this.service.save(images);
    }

    @RequestMapping(value="/images/{imageId}/state/{stateId}", method=RequestMethod.PUT)
    public Image updateState(@PathVariable long imageId, @PathVariable int stateId){
        return this.service.updateState(imageId, stateId);
    }

    @RequestMapping(value="/images/{id}", method=RequestMethod.DELETE)
    public long removeImage(@PathVariable long id){
        return this.service.removeImage(id);
    }
}

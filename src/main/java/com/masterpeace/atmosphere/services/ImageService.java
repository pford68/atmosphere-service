package com.masterpeace.atmosphere.services;

import com.masterpeace.atmosphere.dao.ImageRepository;
import com.masterpeace.atmosphere.dao.StateRepository;
import com.masterpeace.atmosphere.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.masterpeace.atmosphere.model.Image.ImageBuilder;

/**
 *
 */
@Service
@Transactional(readOnly = true)
public class ImageService {

    private final ImageRepository repository;
    private final StateRepository stateRepository;

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    public ImageService(ImageRepository repository, StateRepository stateRepository){
        this.repository = repository;
        this.stateRepository = stateRepository;
    }


    public List<Image> getImagesByGroupId(long groupId) throws Exception {
        return this.repository.findByUserGroupId(groupId);
    }

    @Transactional
    public Iterable<Image> save(List<Image> images){
        Iterable<Image> result = this.repository.saveAll(images);

        for (Image image : result){
            entityManager.refresh(image);
        }

        return result;
    }

    @Transactional
    public Image updateState(long imageId, int stateId){
        Image image = this.repository.getOne(imageId);
        Image _image = new ImageBuilder(image)
                .setState(this.stateRepository.getOne(stateId))
                .build();
        return this.repository.save(_image);
    }

    @Transactional
    public long removeImage(long id){
        Image image = this.repository.getOne(id);
        this.repository.delete(image);
        return id;
    }
}

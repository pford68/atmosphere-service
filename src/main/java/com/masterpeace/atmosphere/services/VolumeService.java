package com.masterpeace.atmosphere.services;

import com.masterpeace.atmosphere.dao.InstanceRepository;
import com.masterpeace.atmosphere.dao.SnapshotRepository;
import com.masterpeace.atmosphere.dao.StateRepository;
import com.masterpeace.atmosphere.dao.VolumeRepository;
import com.masterpeace.atmosphere.model.Instance;
import com.masterpeace.atmosphere.model.Snapshot;
import com.masterpeace.atmosphere.model.State;
import com.masterpeace.atmosphere.model.Volume;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

import static com.masterpeace.atmosphere.model.Volume.VolumeBuilder;



/**
 * Business tier services for accessing Volumes
 */
@Service
@Transactional(readOnly = true)
public class VolumeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VolumeService.class);

    private final VolumeRepository repository;
    private final InstanceRepository instanceRepository;
    private final StateRepository stateRepository;
    private final SnapshotRepository snapshotRepository;

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    public VolumeService(VolumeRepository repository,
                         InstanceRepository instanceRepository,
                         StateRepository stateRepository,
                         SnapshotRepository snapshotRepository){
        this.repository = repository;
        this.instanceRepository = instanceRepository;
        this.stateRepository = stateRepository;
        this.snapshotRepository = snapshotRepository;
    }



    public List<Volume> getByGroupId(long groupId) {
        return this.repository.findByUserGroupId(groupId);
    }


    /**
     *
     * @return              the volumes belonging to all of the specified groups
     */
    public List<Volume> getAllGroupInstances(List<Long> groupIds) {
        return this.repository.findByUserGroupIdInOrderByIdAsc(groupIds);
    }



    public Volume getById(long volumeId) {
        return this.repository.findById(volumeId).get();
    }



    public Iterable<Volume> getAll(Iterable<Long> ids){
        return this.repository.findAllById(ids);
    }



    @Transactional
    public Iterable<Volume> save(List<Volume> volumes) {
        Iterable<Volume> result = this.repository.saveAll(volumes);   // Changed with Spring Boot 2.x

        for (Volume volume : result){
            entityManager.refresh(volume);
        }
        return result;
    }



    @Transactional
    public Volume updateState(Volume volume, int stateId) {
        Volume _volume = new VolumeBuilder(volume)
                .setState(this.stateRepository.findById(stateId).get())
                .build();
        return this.repository.save(_volume);
    }


    @Transactional
    public Volume attach(Volume volume, long instanceId){
        long volumeId = volume.getId();
        Instance instance = this.instanceRepository.findById(instanceId).get();
        State state = this.stateRepository.findById(Volume.ATTACHED_STATE).get();
        Volume _volume = new VolumeBuilder(this.repository.findById(volumeId).get())
                .setInstance(instance)
                .setState(state)
                .build();
        return this.repository.save(_volume);
    }

    @Transactional
    public Volume detach(long volumeId){
        State state = this.stateRepository.findById(Volume.DETACHED_STATE).get();
        Volume _volume = new VolumeBuilder(this.repository.findById(volumeId).get())
                .setInstance(null)
                .setState(state)
                .build();
        return this.repository.save(_volume);
    }



    @Transactional
    public Volume delete(Volume volume) throws Exception {
        this.repository.delete(volume);
        return volume;
    }


    public Volume suspend(Volume volume){
        return this.updateState(volume, Volume.SUSPENDED_STATE);
    }


    @Transactional
    public Volume takeSnapshot(Volume volume) {
        Snapshot snapshot = new Snapshot(new Date().getTime());
        this.snapshotRepository.save(snapshot);
        Volume _volume = new VolumeBuilder(volume).setSnapshot(snapshot).build();
        return this.repository.save(_volume);
    }

}

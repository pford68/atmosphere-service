package com.masterpeace.atmosphere.services;

import com.masterpeace.atmosphere.dao.InstanceRepository;
import com.masterpeace.atmosphere.dao.SnapshotRepository;
import com.masterpeace.atmosphere.dao.StateRepository;
import com.masterpeace.atmosphere.dao.VolumeRepository;
import com.masterpeace.atmosphere.model.Instance;
import com.masterpeace.atmosphere.model.Snapshot;
import com.masterpeace.atmosphere.model.State;
import com.masterpeace.atmosphere.model.Volume;
import org.apache.log4j.Logger;
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

    private static final Logger LOGGER = Logger.getLogger(VolumeService.class);

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
        return this.repository.findOne(volumeId);
    }



    public Iterable<Volume> getAll(Iterable<Long> ids){
        return this.repository.findAll(ids);
    }



    @Transactional
    public Iterable<Volume> save(List<Volume> volumes) {
        Iterable<Volume> result = this.repository.save(volumes);

        for (Volume volume : result){
            entityManager.refresh(volume);
        }
        return result;
    }



    @Transactional
    public Volume updateState(Volume volume, int stateId) {
        Volume _volume = new VolumeBuilder(volume)
                .setState(this.stateRepository.findOne(stateId))
                .build();
        return this.repository.save(_volume);
    }


    @Transactional
    public Volume attach(Volume volume, long instanceId){
        long volumeId = volume.getId();
        Instance instance = this.instanceRepository.findOne(instanceId);
        State state = this.stateRepository.findOne(Volume.ATTACHED_STATE);
        Volume _volume = new VolumeBuilder(this.repository.findOne(volumeId))
                .setInstance(instance)
                .setState(state)
                .build();
        return this.repository.save(_volume);
    }

    @Transactional
    public Volume detach(long volumeId){
        State state = this.stateRepository.findOne(Volume.DETACHED_STATE);
        Volume _volume = new VolumeBuilder(this.repository.findOne(volumeId))
                .setInstance(null)
                .setState(state)
                .build();
        return this.repository.save(_volume);
    }


    @Transactional
    public long delete(long volumeId) throws Exception {
        this.repository.delete(volumeId);
        return volumeId;
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

package com.masterpeace.dao;

import com.masterpeace.atmosphere.Application;
import com.masterpeace.atmosphere.dao.UserGroupRepository;
import com.masterpeace.atmosphere.model.UserGroup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit test for the UserGroupRepository bean
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class UserGroupRepositoryTest {

    @Autowired
    private UserGroupRepository repository;


    @Test
    public void findByUserId() throws Exception{
        List<UserGroup> groups = repository.findByUserId(1L);
        assertEquals(2, groups.size());
        assertEquals("mps", groups.get(0).getName());
        assertEquals("atmosphere-dev", groups.get(1).getName());
    }

}

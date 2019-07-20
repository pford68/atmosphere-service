package com.masterpeace.services;

import com.masterpeace.atmosphere.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Integration Tests for InventoryService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
@WebAppConfiguration
@AutoConfigureMockMvc
public class InventoryServiceIT {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;



    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    public void getImageTypes() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/inventory/images").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].value", is("machine")))
                .andExpect(jsonPath("$[0].description", isEmptyOrNullString()))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].value", is("Ubuntu")))
                .andExpect(jsonPath("$[1].description", is("This is the free Ubuntu image.")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].value", is("Red Hat")))
                .andExpect(jsonPath("$[2].description", is("This is not free.")));
    }


    @Test
    public void getInstancesByImageType() throws Exception {
        /*
        Hitting the service for each image type/instance type combination
         */

        mvc.perform(MockMvcRequestBuilders.get("/inventory/images/2/instances").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].image.id", is(2)))
                .andExpect(jsonPath("$[0].image.value", is("Ubuntu")))
                .andExpect(jsonPath("$[0].instance.id", is(1)))
                .andExpect(jsonPath("$[0].instance.value", is("small")))
                .andExpect(jsonPath("$[1].image.id", is(2)))
                .andExpect(jsonPath("$[1].image.value", is("Ubuntu")))
                .andExpect(jsonPath("$[1].instance.id", is(2)))
                .andExpect(jsonPath("$[1].instance.value", is("medium")));

        mvc.perform(MockMvcRequestBuilders.get("/inventory/images/3/instances").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].image.id", is(3)))
                .andExpect(jsonPath("$[0].image.value", is("Red Hat")))
                .andExpect(jsonPath("$[0].instance.id", is(1)))
                .andExpect(jsonPath("$[0].instance.value", is("small")));
    }


    @Test
    public void getConfigurationOptions() throws Exception {
        /*
        Hitting the service with the first image type/instance type combination.  Walking the returned list.
         */
        mvc.perform(MockMvcRequestBuilders.get("/inventory/images/2/instances/1/options").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("numInstances")))
                .andExpect(jsonPath("$[0].defaultValue", is("1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("spotInstances")))
                .andExpect(jsonPath("$[1].defaultValue", is("false")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("network")))
                .andExpect(jsonPath("$[2].defaultValue", is("pc-801db5e5 (173.31.0.0/16) (default)")))
                .andExpect(jsonPath("$[3].id", is(4)))
                .andExpect(jsonPath("$[3].name", is("subnet")))
                .andExpect(jsonPath("$[3].defaultValue", is("vpc-801db5e5 (173.31.0.0/16) (default)")))
                .andExpect(jsonPath("$[4].id", is(5)))
                .andExpect(jsonPath("$[4].name", is("autoAssignPublicIp")))
                .andExpect(jsonPath("$[4].defaultValue", is("Use subnet setting (Enable)")))
                .andExpect(jsonPath("$[5].id", is(6)))
                .andExpect(jsonPath("$[5].name", is("shutdownBehavior")))
                .andExpect(jsonPath("$[5].defaultValue", is("stop")))
                .andExpect(jsonPath("$[6].id", is(7)))
                .andExpect(jsonPath("$[6].name", is("protected")))
                .andExpect(jsonPath("$[6].defaultValue", is("false")))
                .andExpect(jsonPath("$[7].id", is(8)))
                .andExpect(jsonPath("$[7].name", is("monitoring")))
                .andExpect(jsonPath("$[7].defaultValue", is("false")));


        /*
        Hitting the same service for each of the other instances to verify that
        the correct number of options returns.

        Not walking these lists
        */

        mvc.perform(MockMvcRequestBuilders.get("/inventory/images/2/instances/2/options").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)));

        mvc.perform(MockMvcRequestBuilders.get("/inventory/images/1/instances/1/options").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)));
    }


    @Test
    public void getStorageOptions() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/inventory/storage").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].type", is("root")))
                .andExpect(jsonPath("$[0].size", is(16)))
                .andExpect(jsonPath("$[0].volumeType.value", is("General Purpose (SSD)")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].type", is("root")))
                .andExpect(jsonPath("$[1].size", is(8)))
                .andExpect(jsonPath("$[1].volumeType.value", is("Magnetic")));
    }
}

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ReferenceServiceIT {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp(){
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getReferenceData() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/reference").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.regions", hasSize(4)))
                .andExpect(jsonPath("$.regions[0].value", is("us-west")))
                .andExpect(jsonPath("$.regions[1].value", is("us-mountain")))
                .andExpect(jsonPath("$.regions[2].value", is("us-central")))
                .andExpect(jsonPath("$.regions[3].value", is("us-east")))
                .andExpect(jsonPath("$.userGroups", hasSize(2)))
                .andExpect(jsonPath("$.userGroups[0].name", is("mps")))
                .andExpect(jsonPath("$.userGroups[1].name", is("atmosphere-dev")))
                .andExpect(jsonPath("$.securityGroups", hasSize(3)))
                .andExpect(jsonPath("$.securityGroups[0].value", is("default")))
                .andExpect(jsonPath("$.securityGroups[1].value", is("administrators")))
                .andExpect(jsonPath("$.securityGroups[2].value", is("admin")))
                .andExpect(jsonPath("$.volumeTypes", hasSize(2)))
                .andExpect(jsonPath("$.snapshots", hasSize(4)));
    }
}

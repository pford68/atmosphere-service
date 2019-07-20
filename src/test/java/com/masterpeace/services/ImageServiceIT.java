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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ImageServiceIT {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;


    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    public void getImagesForUserGroup() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/groups/1/images").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("kioko")))
                .andExpect(jsonPath("$[0].source", is("masterpeace")))
                .andExpect(jsonPath("$[0].platform", is("Ubuntu")))
                .andExpect(jsonPath("$[0].description", is("This is named after my cat.")))
                .andExpect(jsonPath("$[0].userGroup.name", is("mps")));
    }
}

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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test for UserService.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Sql({"classpath:fixtures/reset.sql"})
public class UserServiceIT {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;


    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    public void getUserById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limits").doesNotExist())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.first", is("Philip")))
                .andExpect(jsonPath("$.last", is("Ford")))
                .andExpect(jsonPath("$.active", is(true)))
                .andExpect(jsonPath("$.securityGroups", hasSize(3)))
                .andExpect(jsonPath("$.securityGroups[0].value", is("default")))
                .andExpect(jsonPath("$.securityGroups[0].userGroup.name", is("mps")))
                .andExpect(jsonPath("$.securityGroups[1].value", is("administrators")))
                .andExpect(jsonPath("$.securityGroups[1].userGroup.name", is("mps")))
                .andExpect(jsonPath("$.securityGroups[2].value", is("admin")))
                .andExpect(jsonPath("$.securityGroups[2].userGroup.name", is("atmosphere-dev")))
                //.andExpect(jsonPath("$.keyPair.value", is("AngelEyes-Oregon")))   // Not using keyPairs yet.
                .andExpect(jsonPath("$.email", is("pford@gmail.com")))
                .andExpect(jsonPath("$.password").doesNotExist());
    }


    @Test
    public void getUserByUserName() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users?username=pford@gmail.com").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limits").doesNotExist())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.first", is("Philip")))
                .andExpect(jsonPath("$.last", is("Ford")))
                .andExpect(jsonPath("$.active", is(true)))
                .andExpect(jsonPath("$.securityGroups", hasSize(3)))
                .andExpect(jsonPath("$.securityGroups[0].value", is("default")))
                .andExpect(jsonPath("$.securityGroups[0].userGroup.name", is("mps")))
                .andExpect(jsonPath("$.securityGroups[1].value", is("administrators")))
                .andExpect(jsonPath("$.securityGroups[1].userGroup.name", is("mps")))
                .andExpect(jsonPath("$.securityGroups[2].value", is("admin")))
                .andExpect(jsonPath("$.securityGroups[2].userGroup.name", is("atmosphere-dev")))
                        //.andExpect(jsonPath("$.keyPair.value", is("AngelEyes-Oregon")))   // Not using keyPairs yet.
                .andExpect(jsonPath("$.email", is("pford@gmail.com")))
                .andExpect(jsonPath("$.password").doesNotExist());
    }


    @Test
    public void getGroupsByUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/1/groups").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("mps")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("atmosphere-dev")));

        mvc.perform(MockMvcRequestBuilders.get("/users/2/groups").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("mps")));
    }


    @Test
    public void getUserLimits() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/1/limits").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limits", hasSize(5)))
                .andExpect(jsonPath("$.limits[0].size", is(20)))
                .andExpect(jsonPath("$.limits[0].type.id", is(1)))
                .andExpect(jsonPath("$.limits[0].type.name", is("On-Demand Services")))
                .andExpect(jsonPath("$.limits[0].user").doesNotExist())
                .andExpect(jsonPath("$.limits[1].size", is(20)))
                .andExpect(jsonPath("$.limits[1].type.id", is(2)))
                .andExpect(jsonPath("$.limits[1].type.name", is("SSD volume storage (TiB)")))
                .andExpect(jsonPath("$.limits[1].user").doesNotExist())
                .andExpect(jsonPath("$.limits[2].size", is(20)))
                .andExpect(jsonPath("$.limits[2].type.id", is(3)))
                .andExpect(jsonPath("$.limits[2].type.name", is("Magnetic volume storgae (TiB)")))
                .andExpect(jsonPath("$.limits[2].user").doesNotExist())
                .andExpect(jsonPath("$.limits[3].size", is(50)))
                .andExpect(jsonPath("$.limits[3].type.id", is(4)))
                .andExpect(jsonPath("$.limits[3].type.name", is("Rules per security group")))
                .andExpect(jsonPath("$.limits[3].user").doesNotExist())
                .andExpect(jsonPath("$.limits[4].size", is(20)))
                .andExpect(jsonPath("$.limits[4].type.id", is(5)))
                .andExpect(jsonPath("$.limits[4].type.name", is("Auto Scaling Groups")))
                .andExpect(jsonPath("$.limits[4].user").doesNotExist())
                .andExpect(jsonPath("$.userId", is(1)));
    }



    @Test
    public void getUserOverview() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/1/overview")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userid", is(1)))
                .andExpect(jsonPath("$.usergroups", is(2)))
                .andExpect(jsonPath("$.instances", is(3)))
                .andExpect(jsonPath("$.volumes", is(3)))
                .andExpect(jsonPath("$.snapshots", is(3)))
                .andExpect(jsonPath("$.loadbalancers", is(0)))
                .andExpect(jsonPath("$.securitygroups", is(3)));

        mvc.perform(MockMvcRequestBuilders.get("/users/3/overview")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userid", is(3)))
                .andExpect(jsonPath("$.usergroups", is(1)))
                .andExpect(jsonPath("$.instances", is(3)))
                .andExpect(jsonPath("$.volumes", is(3)))
                .andExpect(jsonPath("$.snapshots", is(3)))
                .andExpect(jsonPath("$.loadbalancers", is(0)))
                .andExpect(jsonPath("$.securitygroups", is(1)));
    }
}

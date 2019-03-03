package com.masterpeace.services;

import com.masterpeace.atmosphere.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
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
 * Integration test for InstanceService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
@Sql({"classpath:fixtures/reset.sql"})
public class InstanceServiceIT {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;


    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    public void getInstancesByGroup() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/groups/1/instances").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("dev")))
                .andExpect(jsonPath("$[0].ip[0].value", is("52.11.145.195")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("production")))
                .andExpect(jsonPath("$[1].ip[0].value", is("52.11.145.196")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("secondary_dev")))
                .andExpect(jsonPath("$[2].ip[0].value", is("52.11.145.194")));
    }



    @Test
    @Sql({"classpath:fixtures/reset.sql", "classpath:fixtures/instance-group2.sql"})
    public void getInstancesByGroups() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/groups/instances?ids=1&ids=2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("dev")))
                .andExpect(jsonPath("$[0].ip[0].value", is("52.11.145.195")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("production")))
                .andExpect(jsonPath("$[1].ip[0].value", is("52.11.145.196")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("secondary_dev")))
                .andExpect(jsonPath("$[2].ip[0].value", is("52.11.145.194")))
                .andExpect(jsonPath("$[3].id", is(10)))
                .andExpect(jsonPath("$[3].name", is("myGreatInstance")));
    }


    @Test
    public void getInstanceById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/instances/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("dev")))
                .andExpect(jsonPath("$.ip[0].value", is("52.11.145.195")));
    }


    @Test
    @Sql({"classpath:fixtures/reset.sql", "classpath:fixtures/detach-volume.sql"})
    public void checkCircularReferences() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/instances/10").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(10)))
                .andExpect(jsonPath("$.name", is("myGreatInstance")))
                .andExpect(jsonPath("$.volumes", hasSize(1)))
                .andExpect(jsonPath("$.volumes[0].id", is(4)))
                .andExpect(jsonPath("$.volumes[0].name", is("My Worst Volume")))
                .andExpect(jsonPath("$.volumes[0].instance.id", is(10)))
                .andExpect(jsonPath("$.volumes[0].instance.name", is("myGreatInstance")))
                .andExpect(jsonPath("$.volumes[0].instance.volumes", isEmptyOrNullString()));
    }




    @Test
    public void addInstances() throws Exception {
        String json = "[" +
                    "{" +
                    "\"id\":4," +
                    "\"name\":\"demo\"," +
                    "\"userGroup\":{\"id\":1}," +
                    "\"securityGroups\":[{\"id\":1}, {\"id\":2}]," +
                    "\"type\":{\"id\":1}," +
                    "\"region\":{\"id\":1}," +
                    "\"state\":{\"id\":1}," +
                    "\"dns\":[{\"exposed\":false,\"value\":\"ec2-52-11-145-197.us-west-2.atmosphere.com\"}]," +
                    "\"ip\":[{\"value\":\"52.11.145.197\",\"exposed\":false}]," +
                    "\"keyPair\":{\"id\":1}," +
                    "\"provider\":{\"id\":2}," +
                    "\"monitoringEnabled\":false" +
                    "}," +
                    "{" +
                    "\"id\":5," +
                    "\"name\":\"demo2\"," +
                    "\"userGroup\":{\"id\":1}," +
                    "\"securityGroups\":[{\"id\":2} ]," +
                    "\"type\":{\"id\":1}," +
                    "\"region\":{\"id\":1}," +
                    "\"state\":{\"id\":1}," +
                    "\"dns\":[{\"exposed\":false,\"value\":\"ec2-52-11-145-198.us-west-2.amazon.com\"}]," +
                    "\"ip\":[{\"value\":\"52.11.145.198\",\"exposed\":false}]," +
                    "\"keyPair\":{\"id\":1,\"value\":\"AngelEyes-Oregon\"}," +
                    "\"provider\":{\"id\":1}," +
                    "\"monitoringEnabled\":true" +
                    "}" +
                "]";


        mvc.perform(MockMvcRequestBuilders.post("/instances")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/groups/1/instances")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                // The first instance
                .andExpect(jsonPath("$[3].id", is(4)))
                .andExpect(jsonPath("$[3].name", is("demo")))
                .andExpect(jsonPath("$[3].userGroup.id", is(1)))
                .andExpect(jsonPath("$[3].userGroup.name", is("mps")))
                .andExpect(jsonPath("$[3].userGroup.users", hasSize(3)))
                .andExpect(jsonPath("$[3].type.id", is(1)))
                .andExpect(jsonPath("$[3].type.value", is("small")))
                .andExpect(jsonPath("$[3].provider.name", is("GCE")))
                .andExpect(jsonPath("$[3].region.id", is(1)))
                .andExpect(jsonPath("$[3].region.value", is("us-west")))
                .andExpect(jsonPath("$[3].state.id", is(1)))
                .andExpect(jsonPath("$[3].state.value", is("on")))
                .andExpect(jsonPath("$[3].status", isEmptyOrNullString()))
                .andExpect(jsonPath("$[3].securityGroups", hasSize(2)))
                .andExpect(jsonPath("$[3].monitoringEnabled", is(false)))
                // The second instance
                .andExpect(jsonPath("$[4].id", is(5)))
                .andExpect(jsonPath("$[4].name", is("demo2")))
                .andExpect(jsonPath("$[4].userGroup.id", is(1)))
                .andExpect(jsonPath("$[4].userGroup.name", is("mps")))
                .andExpect(jsonPath("$[4].userGroup.users", hasSize(3)))
                .andExpect(jsonPath("$[4].type.id", is(1)))
                .andExpect(jsonPath("$[4].type.value", is("small")))
                .andExpect(jsonPath("$[4].provider.id", is(1)))
                .andExpect(jsonPath("$[4].region.id", is(1)))
                .andExpect(jsonPath("$[4].region.value", is("us-west")))
                .andExpect(jsonPath("$[4].state.id", is(1)))
                .andExpect(jsonPath("$[4].state.value", is("on")))
                .andExpect(jsonPath("$[4].status", isEmptyOrNullString()))
                .andExpect(jsonPath("$[4].securityGroups", hasSize(1)))
                .andExpect(jsonPath("$[4].monitoringEnabled", is(true)));

    }


    /**
     * Ensures that updates are made to the correct records and that properties that are not
     * sent from the client are not nulled out.
     *
     * @throws Exception
     */
    @Test
    public void stop() throws Exception {


        mvc.perform(MockMvcRequestBuilders.put("/instances/1/state/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/groups/1/instances")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("dev")))
                .andExpect(jsonPath("$[0].state.id", is(0)))
                .andExpect(jsonPath("$[0].state.value", is("off")))
                .andExpect(jsonPath("$[0].userGroup.id", is(1)))
                .andExpect(jsonPath("$[0].userGroup.name", is("mps")))
                .andExpect(jsonPath("$[0].userGroup.users", hasSize(3)))
                .andExpect(jsonPath("$[0].type.id", is(1)))
                .andExpect(jsonPath("$[0].type.value", is("small")))
                .andExpect(jsonPath("$[0].provider.name", is("AWS")))
                .andExpect(jsonPath("$[0].region.id", is(1)))
                .andExpect(jsonPath("$[0].region.value", is("us-west")))
                .andExpect(jsonPath("$[0].status.value", is("default")))
                .andExpect(jsonPath("$[0].securityGroups", hasSize(2)))
                .andExpect(jsonPath("$[0].ip[0].value", is("52.11.145.195")))
                .andExpect(jsonPath("$[0].ip[0].exposed", is(true)))
                .andExpect(jsonPath("$[0].monitoringEnabled", is(false)));
    }


    /**
     * Ensures that updates are made to the correct records and that properties that are not
     * sent from the client are not nulled out.
     *
     * @throws Exception
     */
    @Test
    public void start() throws Exception {

        mvc.perform(MockMvcRequestBuilders.put("/instances/1/state/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/groups/1/instances")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("dev")))
                .andExpect(jsonPath("$[0].state.id", is(1)))
                .andExpect(jsonPath("$[0].state.value", is("on")))
                .andExpect(jsonPath("$[0].userGroup.id", is(1)))
                .andExpect(jsonPath("$[0].userGroup.name", is("mps")))
                .andExpect(jsonPath("$[0].userGroup.users", hasSize(3)))
                .andExpect(jsonPath("$[0].type.id", is(1)))
                .andExpect(jsonPath("$[0].type.value", is("small")))
                .andExpect(jsonPath("$[0].provider.name", is("AWS")))
                .andExpect(jsonPath("$[0].region.id", is(1)))
                .andExpect(jsonPath("$[0].region.value", is("us-west")))
                .andExpect(jsonPath("$[0].status.value", is("default")))
                .andExpect(jsonPath("$[0].securityGroups", hasSize(2)))
                .andExpect(jsonPath("$[0].ip[0].value", is("52.11.145.195")))
                .andExpect(jsonPath("$[0].ip[0].exposed", is(true)))
                .andExpect(jsonPath("$[0].monitoringEnabled", is(false)));
    }


    @Test
    @Sql({"classpath:fixtures/reset.sql", "classpath:fixtures/insert.sql"})
    public void deleteNewInstance() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/instances/10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/groups/1/instances").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("dev")))
                .andExpect(jsonPath("$[0].ip[0].value", is("52.11.145.195")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("production")))
                .andExpect(jsonPath("$[1].ip[0].value", is("52.11.145.196")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("secondary_dev")))
                .andExpect(jsonPath("$[2].ip[0].value", is("52.11.145.194")));
    }


    @Test
    @Sql({"classpath:fixtures/reset.sql", "classpath:fixtures/detach-volume.sql"})
    public void deleteInstanceWithVolumeAttached() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/instances/10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/groups/1/instances").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[2].id", is(3)));
    }


}

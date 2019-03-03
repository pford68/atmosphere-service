package com.masterpeace.services;

import com.masterpeace.atmosphere.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the Volumes service
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
@Sql({"classpath:fixtures/reset.sql"})
public class VolumeServiceIT {

    private MockMvc mvc;


    @Autowired
    private WebApplicationContext context;


    @Autowired
    private TestingAuthenticationToken token;



    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }



    @Test
    public void getVolumesByGroupId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/groups/1/volumes")
                .principal(token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("My Great Volume")))
                .andExpect(jsonPath("$[0].size", is(8)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("My Great Volume II")))
                .andExpect(jsonPath("$[1].size", is(16)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("My Next Greatest Volume")))
                .andExpect(jsonPath("$[2].size", is(16)));
    }



    @Test
    public void getVolumeById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/volumes/1")
                .principal(token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("My Great Volume")))
                .andExpect(jsonPath("$.size", is(8)))
                .andExpect(jsonPath("$.userGroup.id", is(1)))
                .andExpect(jsonPath("$.userGroup.name", is("mps")))
                .andExpect(jsonPath("$.userGroup.users", hasSize(3)))
                .andExpect(jsonPath("$.type.id", is(1)))
                .andExpect(jsonPath("$.type.value", is("General Purpose (SSD)")))
                .andExpect(jsonPath("$.region.id", is(1)))
                .andExpect(jsonPath("$.region.value", is("us-west")))
                .andExpect(jsonPath("$.state.id", is(1)))
                .andExpect(jsonPath("$.state.value", is("on")))
                .andExpect(jsonPath("$.status", isEmptyOrNullString()))
                .andExpect(jsonPath("$.createdDate", is(1425240151000L)))
                .andExpect(jsonPath("$.monitoringEnabled", is(false)))
                .andExpect(jsonPath("$.encrypted", is(false)))
                .andExpect(jsonPath("$.snapshot.id", is(1)));
    }



    @Test
    public void createVolume() throws Exception {
        String json = "[{" +
                    "\"name\": \"Test Volume\", " +
                    "\"size\": 24, " +
                    "\"monitoringEnabled\": true, " +
                    "\"encrypted\": true, " +
                    "\"state\": { \"id\": 1 }, " +
                    "\"userGroup\": { \"id\": 1 }, " +
                    "\"region\": { \"id\": 1 }, " +
                    "\"type\": { \"id\": 1 }" +
                "}]";

        class MvcPerformer {
            void test(MockHttpServletRequestBuilder requestBuilder, String root) throws Exception{
                mvc.perform(requestBuilder)
                        .andExpect(status().isOk())
                        .andExpect(jsonPath(root + ".id", is(4)))
                        .andExpect(jsonPath(root + ".name", is("Test Volume")))
                        .andExpect(jsonPath(root + ".size", is(24)))
                        .andExpect(jsonPath(root + ".userGroup.id", is(1)))
                        .andExpect(jsonPath(root + ".userGroup.name", is("mps")))
                        .andExpect(jsonPath(root + ".userGroup.users", hasSize(3)))
                        .andExpect(jsonPath(root + ".type.id", is(1)))
                        .andExpect(jsonPath(root + ".type.value", is("General Purpose (SSD)")))
                        .andExpect(jsonPath(root + ".region.id", is(1)))
                        .andExpect(jsonPath(root + ".region.value", is("us-west")))
                        .andExpect(jsonPath(root + ".state.id", is(1)))
                        .andExpect(jsonPath(root + ".state.value", is("on")))
                        .andExpect(jsonPath(root + ".status", isEmptyOrNullString()))
                        .andExpect(jsonPath(root + ".monitoringEnabled", is(true)))
                        .andExpect(jsonPath(root + ".encrypted", is(true)));
            }
        }

        MockHttpServletRequestBuilder createVolume = MockMvcRequestBuilders.post("/volumes")
                .principal(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        MockHttpServletRequestBuilder retrieve = MockMvcRequestBuilders.get("/volumes/4")
                .principal(token)
                .accept(MediaType.APPLICATION_JSON);


        MvcPerformer mvcp = new MvcPerformer();
        mvcp.test(createVolume, "$[0]");  // Checking the return value
        mvcp.test(retrieve, "$");         // Ensuring that the volume persisted
    }


    @Test
    @Sql({"classpath:fixtures/reset.sql", "classpath:fixtures/detach-volume.sql"})
    public void detach() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/volumes/4/instances/10")
                .principal(token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/volumes/4")
                .principal(token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.state.id", is(0)))
                .andExpect(jsonPath("$.state.value", is("off")))
                .andExpect(jsonPath("$.instance", isEmptyOrNullString()));
    }



    @Test
    @Sql({"classpath:fixtures/reset.sql", "classpath:fixtures/insert.sql"})
    public void attach() throws Exception {
        String volume = "{" +
                "\"id\": 4," +
                "\"name\": \"Test Volume\", " +
                "\"size\": 24, " +
                "\"monitoringEnabled\": true, " +
                "\"encrypted\": true, " +
                "\"state\": { \"id\": 1 }, " +
                "\"userGroup\": { \"id\": 1 }, " +
                "\"region\": { \"id\": 1 }, " +
                "\"type\": { \"id\": 1 }," +
                "\"instance\": { \"id\": 10 }" +
                "}";

        mvc.perform(MockMvcRequestBuilders.put("/volumes/4/instances/10")
                .principal(token)
                .contentType(MediaType.APPLICATION_JSON).content(volume))
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/volumes/4")
                .principal(token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.state.id", is(1)))
                .andExpect(jsonPath("$.state.value", is("on")))
                .andExpect(jsonPath("$.instance.id", is(10)));
    }


    @Test
    @Sql({"classpath:fixtures/reset.sql", "classpath:fixtures/insert.sql"})
    public void deleteVolume() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/volumes/4")
                .principal(token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Ensuring that the correct volume was deleted.
        mvc.perform(MockMvcRequestBuilders.get("/groups/1/volumes")
                .principal(token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[2].id", is(3)));
    }


    @Test
    @Sql({"classpath:fixtures/reset.sql", "classpath:fixtures/insert-volume-snapshots.sql"})
    public void deleteVolumeWithAttachedSnapshots() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/volumes/4")
                .principal(token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Ensuring that the correct volume was deleted.
        mvc.perform(MockMvcRequestBuilders.get("/groups/1/volumes")
                .principal(token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[2].id", is(3)));
    }
}

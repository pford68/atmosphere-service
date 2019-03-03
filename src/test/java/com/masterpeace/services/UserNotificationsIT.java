package com.masterpeace.services;

import com.masterpeace.atmosphere.Application;
import com.masterpeace.atmosphere.dao.NotificationRepository;
import com.masterpeace.atmosphere.model.Notification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collection;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.Assert.*;



/**
 * Integration Tests for NotificationService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
@Sql({"classpath:fixtures/reset-notifications.sql", "classpath:fixtures/insert-notifications.sql"})
public class UserNotificationsIT {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;


    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    public void getUnreadNotificationsForUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/1/notifications/unread").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(3)));

        mvc.perform(MockMvcRequestBuilders.get("/users/3/notifications/unread").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(4)))
                .andExpect(jsonPath("$[1].id", is(5)));

        mvc.perform(MockMvcRequestBuilders.get("/users/2/notifications/unread").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }


    @Test
    public void validateUnreadNotifications() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/1/notifications/unread").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].message", is("This is the first message for pford.")))
                .andExpect(jsonPath("$[0].opened", is(false)))
                .andExpect(jsonPath("$[1].id", is(3)))
                .andExpect(jsonPath("$[1].message", is("This is the third message for pford.")))
                .andExpect(jsonPath("$[1].opened", is(false)));
    }


    @Test
    public void getAllNotificationsForUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/1/notifications").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

        mvc.perform(MockMvcRequestBuilders.get("/users/2/notifications").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        mvc.perform(MockMvcRequestBuilders.get("/users/3/notifications").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }


    @Test
    public void validateReadNotifications() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/1/notifications").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].message", is("This is the first message for pford.")))
                .andExpect(jsonPath("$[0].opened", is(false)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].message", is("This is the second message for pford.")))
                .andExpect(jsonPath("$[1].opened", is(true)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].message", is("This is the third message for pford.")))
                .andExpect(jsonPath("$[2].opened", is(false)));
    }


    @Test
    public void getNotificationById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/1/notifications/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }


    @Test
    public void markAsRead() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/users/3/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[4,5]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(4)))
                .andExpect(jsonPath("$[0].opened", is(true)))
                .andExpect(jsonPath("$[0].openedDate", notNullValue()))
                .andExpect(jsonPath("$[1].id", is(5)))
                .andExpect(jsonPath("$[1].opened", is(true)))
                .andExpect(jsonPath("$[1].openedDate", notNullValue()));

        // Confirm that the number of unread messages decreased to zero
        mvc.perform(MockMvcRequestBuilders.get("/users/3/notifications/unread").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }


    @Test
    public void deleteMessage() throws Exception {
        // A control
        NotificationRepository repository = context.getBean(NotificationRepository.class);
        Iterable<Notification> allNotifications = repository.findAll();
        assertEquals(((Collection<?>) allNotifications).size(), 6);

        // Deleting the record with ID 10.
        mvc.perform(MockMvcRequestBuilders.delete("/users/1/notifications/10").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(10)));

        // Ensure that the delete occurred.
        allNotifications = repository.findAll();
        assertEquals(((Collection<?>)allNotifications).size(), 5);
        assertNull(repository.findOne(10L));
    }

}

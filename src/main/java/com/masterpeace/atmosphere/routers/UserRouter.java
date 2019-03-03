package com.masterpeace.atmosphere.routers;

import com.masterpeace.atmosphere.configuration.AtmosphereSecurityConfig;
import com.masterpeace.atmosphere.dto.UserLimitDTO;
import com.masterpeace.atmosphere.model.*;
import com.masterpeace.atmosphere.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RESTful services for accessing user information
 */
@RestController
public class UserRouter {

    private final UserService service;
    private final AtmosphereSecurityConfig securityConfig;

    @Autowired
    public UserRouter(UserService service, AtmosphereSecurityConfig securityConfig){
        this.service = service;
        this.securityConfig = securityConfig;
    }


    /**
     * Returns the User object with the specified userId.
     *
     * @param userId            The ID of this User
     * @return                  The User object containing this ID
     * @throws Exception
     */
    @RequestMapping(value="/users/{userId}", method= RequestMethod.GET)
    public User getUser(@PathVariable long userId) throws Exception {
        return this.service.getUserById(userId);
    }


    /**
     * Returns the User object with the specified userName.
     *
     * @param userName          The userName for this User
     * @return                  The User object that has this email
     * @throws Exception
     */
    @RequestMapping(value="/users", method= RequestMethod.GET)
    public User getUserByEmail(@RequestParam("username") String userName) throws Exception {
        return this.service.getUserByUserName(userName);
    }


    /**
     * Returns the specified user's groups.  The user is specified by userID.
     * @param userId            The ID of this user
     * @return                  The list of groups to which this user belongs
     * @throws Exception
     */
    @RequestMapping(value="/users/{userId}/groups", method= RequestMethod.GET)
    public Iterable<UserGroup> getGroupsByUserId(@PathVariable long userId) throws Exception {
        return this.service.getGroupsByUserId(userId);
    }


    /**
     * Returns members of the specified group, specified by group ID.
     * @param groupId           The ID of this group
     * @return                  The list of users in the group
     * @throws Exception
     */
    @RequestMapping(value="/groups/{groupId}/users", method= RequestMethod.GET)
    public Iterable<User> getUsersByGroupId(@PathVariable long groupId) throws Exception {
        return this.service.getUsersByGroupId(groupId);
    }


    /**
     * Returns the specified user's usage limits.
     * @param userId            The ID of this user
     * @return                  The user's usage limits plus his/her ID
     * @throws Exception
     */
    @RequestMapping(value="/users/{userId}/limits", method=RequestMethod.GET)
    public UserLimitDTO getLimitsForUser(@PathVariable long userId) throws Exception {
        List<Limit> limits = this.service.getLimitsForUser(userId);
        return new UserLimitDTO(userId, limits);
    }


    /**
     * Gets the overview of the things that the user can access--instnaces, volumes, etc.
     * @param userId            The user's ID
     * @return                  The overview of the things the user can access.
     * @throws Exception
     */
    @RequestMapping(value="/users/{userId}/overview", method=RequestMethod.GET)
    public Overview getUserOverview(@PathVariable long userId) throws Exception {
        return this.service.getUserOverview(userId);
    }


    @RequestMapping(value="/users/{userId}/notifications/unread", method= RequestMethod.GET)
    public List<Notification> getUnreadNotificationsForUser(@PathVariable long userId) throws Exception {
        return this.service.getUserNotifications(userId, true);
    }


    @RequestMapping(value="/users/{userId}/notifications", method= RequestMethod.GET)
    public List<Notification> getAllNotificationsForUser(@PathVariable long userId) throws Exception {
        return this.service.getUserNotifications(userId, false);
    }


    @RequestMapping(value="/users/{userId}/notifications/{noteId}", method= RequestMethod.GET)
    public Notification getNotificationById(@PathVariable long userId, @PathVariable long noteId) throws Exception {
        /*
        PF (2015/04/11)
        Note that the userId in the path is not used.  It is there for a sensible REST URL.
         */
        return this.service.getNotificationById(noteId);
    }


    @RequestMapping(value="/users/{userId}/notifications", method=RequestMethod.PUT)
    public Iterable<Notification> markAsRead(@PathVariable long userId, @RequestBody List<Long> ids) throws Exception {
        /*
        PF (2015/04/11)
        Note that the userId in the path is not used.  It is there for a sensible REST URL.
         */
        return this.service.markMessagesAsRead(ids);
    }


    @RequestMapping(value="/users/{userId}/notifications/{noteId}", method= RequestMethod.DELETE)
    public Long deleteMessage(@PathVariable long userId, @PathVariable long noteId) throws Exception {
        /*
        PF (2015/04/11)
        Note that the userId in the path is not used.  It is there for a sensible REST URL.
         */
        return this.service.deleteMessage(noteId);
    }


    @RequestMapping(value = "/users/login", method = RequestMethod.GET)
    public boolean isUserAuthenticated() throws Exception {
        return securityConfig.currentUser() != null;
    }

}

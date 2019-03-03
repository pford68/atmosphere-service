package com.masterpeace.atmosphere.services;

import com.masterpeace.atmosphere.dao.*;
import com.masterpeace.atmosphere.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Business tier services for accessing user information
 */
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final LimitRepository limitRepository;
    private final UserGroupRepository userGroupRepository;
    private final OverviewRepository overviewRepository;
    private final NotificationRepository notificationRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserGroupRepository userGroupRepository,
                       LimitRepository limitRepository, OverviewRepository overviewRepository,
                       NotificationRepository notificationRepository){
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;
        this.limitRepository = limitRepository;
        this.overviewRepository = overviewRepository;
        this.notificationRepository = notificationRepository;
    }


    /**
     * Returns the User object with the specified userId.
     *
     * @param userId            The ID of this User
     * @return                  The User object containing this ID
     * @throws Exception
     */
    public User getUserById(long userId) throws Exception {
        return this.userRepository.findOne(userId);
    }


    /**
     * Returns the User object with the specified userName.
     *
     * @param userName          The userName for this User
     * @return                  The User object that has this email
     * @throws Exception
     */
    public User getUserByUserName(String userName) throws Exception {
        return this.userRepository.findByEmail(userName);
    }



    /**
     * Returns the specified user's groups.  The user is specified by userID.
     * @param userId            The ID of this user
     * @return                  The list of groups to which this user belongs
     * @throws Exception
     */
    public Iterable<UserGroup> getGroupsByUserId(long userId) throws Exception {
        User user = this.userRepository.findOne(userId);
        return user.getUserGroups();
    }


    /**
     * Returns members of the specified group, specified by group ID.
     * @param groupId           The ID of this group
     * @return                  The list of users in the group
     * @throws Exception
     */
    public Iterable<User> getUsersByGroupId(long groupId) throws Exception {
        UserGroup group = this.userGroupRepository.findOne(groupId);
        return group.getUsers();
    }


    /**
     * Returns the specified user's usage limits.
     * @param userId            The ID of this user
     * @return                  The user's usage limits plus his/her ID
     * @throws Exception
     */
    public List<Limit> getLimitsForUser(long userId) throws Exception {
        return this.limitRepository.findByUserId(userId);
    }


    /**
     * Gets the overview of the things that the user can access--instnaces, volumes, etc.
     * @param userId            The user's ID
     * @return                  The overview of the things the user can access.
     * @throws Exception
     */
    public Overview getUserOverview(long userId) throws Exception {
        return this.overviewRepository.findByUserId(userId);
    }



    public Set<User> getGroupAdmins(Protectable protectable){
        Iterable<User> users = protectable.getUserGroup().getUsers();
        Set<User> admins = new HashSet<User>();
        for (User user : users){
            if (user.isGroupAdmin(protectable)){
                admins.add(user);
            }
        }
        return admins;
    }



    @Transactional
    public Notification notify(String text, User recipient){
        Notification message = new Notification.NotificationBuilder()
                .setMessage(text)
                .setUser(recipient).build();
        return this.notificationRepository.save(message);
    }


    @Transactional
    public void notifyAll(String text, Set<User> recipients){
        for (User user : recipients){
            this.notify(text, user);
        }
    }



    public List<Notification> getUserNotifications(long userId, boolean unreadOnly) throws Exception {
        if (unreadOnly) {
            return this.notificationRepository.findByUserIdAndOpened(userId, false);
        } else {
            return this.notificationRepository.findByUserId(userId);
        }
    }


    public Notification getNotificationById(long noteId) throws Exception {
        return this.notificationRepository.findOne(noteId);
    }


    @Transactional
    public Iterable<Notification> markMessagesAsRead(List<Long> ids) throws Exception {
        Iterable<Notification> notifications = this.notificationRepository.findAll(ids);
        List<Notification> _notifications = new ArrayList<Notification>();
        for (Notification n : notifications){
            _notifications.add(new Notification.NotificationBuilder(n).setOpened(true).build());
        }
        return this.notificationRepository.save(_notifications);
    }


    @Transactional
    public Long deleteMessage(long noteId) throws Exception {
        this.notificationRepository.delete(noteId);
        return noteId;
    }
}

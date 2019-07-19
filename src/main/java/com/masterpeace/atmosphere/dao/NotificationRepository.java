package com.masterpeace.atmosphere.dao;

import com.masterpeace.atmosphere.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Implemented by classes that access the Notifications data store.
 */
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdAndOpened(long userId, boolean opened);
    List<Notification> findByUserId(long userId);
}

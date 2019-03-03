package com.masterpeace.atmosphere.dao;

import com.masterpeace.atmosphere.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 *  Implemented by classes that access the UserGroup data store.
 */
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

    @Query(value = "select user_group.* from user_group left join user_group_users on user_group.id = user_group_users.user_group where user_group_users.users = :userId", nativeQuery = true)
    List<UserGroup> findByUserId(@Param("userId") long userId);
}

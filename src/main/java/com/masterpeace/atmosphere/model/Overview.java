package com.masterpeace.atmosphere.model;

import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import javax.persistence.*;

/**
 * Represents a user's access overview:  the instances, volumes, etc. that he/she can access.
 */
@Entity
// Note that the aliases (and corresponding Overview property naems cannot be camel case, because the JPA queries expect
// to match those properties with column names that have underscores between syllables.
@Subselect("select user.id as userid," +
        "(select count(instance.id) from user left join user_group_users on user_group_users.users = user.id left join user_group on user_group.id = user_group_users.user_group left join instance on instance.user_group_id = user_group.id where user.id = userid) as instances, " +
        "(select count(volume.id) from user left join user_group_users on user_group_users.users = user.id left join user_group on user_group.id = user_group_users.user_group left join volume on volume.user_group_id = user_group.id where user.id = userid) as volumes, " +
        "(select count(user_security_groups.user_id) from user left join user_security_groups on user_security_groups.user_id = user.id where user.id = userid) as securitygroups, " +
        "(select count(load_balancer.id) from user left join user_group_users on user_group_users.users = user.id left join user_group on user_group.id = user_group_users.user_group left join load_balancer on load_balancer.user_group_id = user_group.id where user.id = userid) as loadbalancers, " +
        "(select count(user_group_users.users) from user_group_users where user_group_users.users = userid group by user_group_users.users) as usergroups," +
        "(select count(snapshot.id) from user left join user_group_users on user_group_users.users = user.id left join user_group on user_group.id = user_group_users.user_group left join volume on volume.user_group_id = user_group.id left join snapshot on volume.snapshot_id = snapshot.id where user.id = userid) as snapshots " +
        "from user left join user_group_users on user_group_users.users = user.id left join user_group on user_group.id = user_group_users.user_group group by user.id")
@Synchronize({"user", "user_group", "instance", "volume", "security_group", "load_balancer", "snapshot"})
public class Overview {

    @Id
    private long userid;
    private int instances;
    private int volumes;
    private int securitygroups;
    private int snapshots;
    private int loadbalancers;
    private int usergroups;

    protected Overview(){}

    public Overview(long userid, int instances, int volumes, int securitygroups, int snapshots, int loadbalancers, int usergroups) {
        this.userid = userid;
        this.instances = instances;
        this.volumes = volumes;
        this.securitygroups = securitygroups;
        this.snapshots = snapshots;
        this.loadbalancers = loadbalancers;
        this.usergroups = usergroups;
    }

    public long getUserid() {
        return userid;
    }

    public int getInstances() {
        return instances;
    }

    public int getVolumes() {
        return volumes;
    }

    public int getSecuritygroups() {
        return securitygroups;
    }

    public int getSnapshots() {
        return snapshots;
    }

    public int getLoadbalancers() {
        return loadbalancers;
    }

    public int getUsergroups() {
        return usergroups;
    }
}

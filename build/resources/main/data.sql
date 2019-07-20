/*
Seed data for the development and test databases
 */

-- Providers
insert into provider(id, name, full_name) values(1, "AWS", "Amazon Web Services");
insert into provider(id, name, full_name) values(2, "GCE", "Google Compute Engine");



-- Instance Types
insert into instance_type(id, value, description) values(1, "small", "This is the default free type.");
insert into instance_type(id, value, description) values(2, "medium", "This costs more than free");
insert into instance_type(id, value, description) values(3, "large", "This is definitely not free");


-- Volume Types
insert into volume_type(id, value) values(1, "General Purpose (SSD)");
insert into volume_type(id, value) values(2, "Magnetic");


-- Alarms
insert into alarm(id, value) values(1, "none");


-- Statuses
insert into status(id, value) values(1, "default");
insert into status(id, value, rules) values(2, "protected", "SAVE,UPDATE");


-- States
insert into state(id, value, description) values(-1, "out", "Flags the item as deleted, scheduled for deletion, or suspended");
insert into state(id, value, description) values(0, "off", "Indicates that the item is deactivated");
insert into state(id, value, description) values(1, "on", "Indicates that the item is running, up, active, etc.");


-- Timezomes
insert into region(id, value) values(1, "us-west");
insert into region(id, value) values(2, "us-mountain");
insert into region(id, value) values(3, "us-central");
insert into region(id, value) values(4, "us-east");


-- Key pairs
insert into key_pair (id, value, provider_id) values(1, "AngelEyes-Oregon", 1);


-- Groups
insert into user_group(id, name, created_date) values(1, "mps", UNIX_TIMESTAMP('2015-03-01 11:19:02') * 1000);
insert into user_group(id, name, created_date) values(2, "atmosphere-dev", UNIX_TIMESTAMP('2015-03-01 12:15:02') * 1000);


-- Security groups:  must be added after User Groups, but before Users.
insert into security_group(id, value, user_group_id) values(1, "default", 1);
insert into security_group(id, value, user_group_id, permissions) values(2, "administrators", 1, "SELECT,INSERT,UPDATE,DELETE");
insert into security_group(id, value, user_group_id, permissions) values(3, "admin", 2, "SELECT,INSERT,UPDATE,DELETE");


-- Users
insert into user(id, first, last, email, password) values(1, "Philip", "Ford", "pford@gmail.com", "{noop}password1");
insert into user(id, first, last, email, password) values(2, "Drew", "Cohen", "dcohen@gmail.com", "{noop}password1");
insert into user(id, first, last, email, password) values(3, "John", "Smith", "jsmith@gmail.com", "{noop}password1");


-- User Security Groups
insert into user_security_groups(user_id, security_groups_id) values(1, 1);
insert into user_security_groups(user_id, security_groups_id) values(1, 2);
insert into user_security_groups(user_id, security_groups_id) values(1, 3);
insert into user_security_groups(user_id, security_groups_id) values(2, 1);
insert into user_security_groups(user_id, security_groups_id) values(2, 2);
insert into user_security_groups(user_id, security_groups_id) values(3, 1);



-- User Group Users
insert into user_group_users(user_group, users) values(1, 1);
insert into user_group_users(user_group, users) values(1, 2);
insert into user_group_users(user_group, users) values(1, 3);
insert into user_group_users(user_group, users) values(2, 1);



-- Snapshots
insert into snapshot(id, created_date) values(1, UNIX_TIMESTAMP('2015-04-04 15:33:18') * 1000);
insert into snapshot(id, created_date) values(2, UNIX_TIMESTAMP('2015-04-05 15:00:08') * 1000);
insert into snapshot(id, created_date) values(3, UNIX_TIMESTAMP('2015-04-06 15:10:01') * 1000);
insert into snapshot(id, created_date) values(4, UNIX_TIMESTAMP('2015-04-07 15:21:11') * 1000);




-- DNS
insert into dns(id, value, exposed) values(1, "ec2-52-11-145-195.us-west-2.amazon.com", 1);
insert into dns(id, value, exposed) values(2, "ec2-52-11-145-196.us-west-2.amazon.com", 1);
insert into dns(id, value, exposed) values(3, "ec2-52-11-145-194.us-west-2.amazon.com", 1);


-- IP Addresses
insert into ip_address(id, value, exposed) values(1, "52.11.145.195", 1);
insert into ip_address(id, value, exposed) values(2, "52.11.145.196", 1);
insert into ip_address(id, value, exposed) values(3, "52.11.145.194", 1);




-- Instances
insert into instance(id, name, region_id, state_id, status_id, created_date, key_pair_id, monitoring_enabled, type_id, user_group_id, provider_id) values (1, "dev", 1, 1, 1, UNIX_TIMESTAMP('2015-03-01 12:15:53') * 1000, 1, 0, 1, 1, 1);
insert into instance(id, name, region_id, state_id, status_id, created_date, key_pair_id, monitoring_enabled, type_id, user_group_id, provider_id) values (2, "production", 1, 1, 2, UNIX_TIMESTAMP('2015-03-02 13:56:12') * 1000, 1, 1, 1, 1, 1);
insert into instance(id, name, region_id, state_id, status_id, created_date, key_pair_id, monitoring_enabled, type_id, user_group_id, provider_id) values (3, "secondary_dev", 1, 1, 1, UNIX_TIMESTAMP('2015-03-05 15:01:12') * 1000, 1, 0, 1, 1, 2);



-- Instance Security Groups
insert into instance_security_groups(instance_id, security_groups_id) values(1, 1);
insert into instance_security_groups(instance_id, security_groups_id) values(1, 2);
insert into instance_security_groups(instance_id, security_groups_id) values(2, 2);
insert into instance_security_groups(instance_id, security_groups_id) values(3, 1);
insert into instance_security_groups(instance_id, security_groups_id) values(3, 2);



-- Volumes
insert into volume(id, name, type_id, size, region_id, state_id, alarm_status_id, created_date, monitoring_enabled, encrypted, user_group_id, snapshot_id) values(1, "My Great Volume", 1, 8, 1, 1, 1, UNIX_TIMESTAMP('2015-03-01 15:02:31') * 1000, 0, 0, 1, 1);
insert into volume(id, name, type_id, size, region_id, state_id, alarm_status_id, created_date, monitoring_enabled, encrypted, user_group_id, snapshot_id, instance_id) values(2, "My Great Volume II", 1, 16, 1, 1, 1, UNIX_TIMESTAMP('2015-03-02 08:19:02') * 1000, 0, 1, 1, 2, 2);
insert into volume(id, name, type_id, size, region_id, state_id, alarm_status_id, created_date, monitoring_enabled, encrypted, user_group_id, snapshot_id) values(3, "My Next Greatest Volume", 1, 16, 1, 1, 1, UNIX_TIMESTAMP('2015-03-03 08:00:05') * 1000, 1, 1, 1, 3);



-- Instance Volumes
insert into instance_volumes(instance_id, volumes_id) values(2, 2);


-- Instance DNS
insert into instance_dns(instance_id, dns_id) values(1, 1);
insert into instance_dns(instance_id, dns_id) values(2, 2);
insert into instance_dns(instance_id, dns_id) values(3, 3);



-- Instance IP
insert into instance_ip(instance_id, ip_id) values(1, 1);
insert into instance_ip(instance_id, ip_id) values(2, 2);
insert into instance_ip(instance_id, ip_id) values(3, 3);



-- Image Types
insert into image_type(id, value, description) values(1, "machine", "");
insert into image_type(id, value, description) values(2, "Ubuntu", "This is the free Ubuntu image.");
insert into image_type(id, value, description) values(3, "Red Hat", "This is not free.");



-- Images
insert into image(id, name, source, owner, type_id, exposed, state_id, platform, root_device, architecture, description, user_group_id) values(1, "kioko", "masterpeace", "", 2, 1, 1, "Ubuntu", "instance-store", "x86_64", "This is named after my cat.", 1);
insert into image(id, name, source, owner, type_id, exposed, state_id, platform, root_device, architecture, description, user_group_id) values(2, "atm_svc", "masterpeace", "", 3, 1, 1, "RedHat", "instance-store", "x86_64", "Image of atm services.", 1);
insert into image(id, name, source, owner, type_id, exposed, state_id, platform, root_device, architecture, description, user_group_id) values(3, "kioko 2", "masterpeace", "", 2, 1, 1, "Ubuntu", "instance-store", "x86_64", "This is named after my cat.", 1);


-- Limit Types
insert into usage_limit_type(id, name, family, size) values(1, "On-Demand Services", "instance", 20);
insert into usage_limit_type(id, name, family, size) values(2, "SSD volume storage (TiB)", "storage", 20);
insert into usage_limit_type(id, name, family, size) values(3, "Magnetic volume storgae (TiB)", "storage", 20);
insert into usage_limit_type(id, name, family, size) values(4, "Rules per security group", "networking", 50);
insert into usage_limit_type(id, name, family, size) values(5, "Auto Scaling Groups", "scaling", 20);


-- Limits
insert into usage_limit(id, type_id, size, user_id) values(1, 1, 20, 1);
insert into usage_limit(id, type_id, size, user_id) values(2, 2, 20, 1);
insert into usage_limit(id, type_id, size, user_id) values(3, 3, 20, 1);
insert into usage_limit(id, type_id, size, user_id) values(4, 4, 50, 1);
insert into usage_limit(id, type_id, size, user_id) values(5, 5, 20, 1);

insert into usage_limit(id, type_id, size, user_id) values(6, 1, 20, 2);
insert into usage_limit(id, type_id, size, user_id) values(7, 2, 20, 2);
insert into usage_limit(id, type_id, size, user_id) values(8, 3, 20, 2);
insert into usage_limit(id, type_id, size, user_id) values(9, 4, 50, 2);
insert into usage_limit(id, type_id, size, user_id) values(10, 5, 20, 2);

insert into usage_limit(id, type_id, size, user_id) values(11, 1, 20, 3);
insert into usage_limit(id, type_id, size, user_id) values(12, 2, 20, 3);
insert into usage_limit(id, type_id, size, user_id) values(13, 3, 20, 3);
insert into usage_limit(id, type_id, size, user_id) values(14, 4, 50, 3);
insert into usage_limit(id, type_id, size, user_id) values(15, 5, 20, 3);



-- Storage Options
insert into storage_option(id, type, device, snapshot_id, volume_type_id, size, iops, delete_on_termination, encrypted) values(1, "root", "/dev/xvda", NULL, 1, 16, "24/3000", true, false);
insert into storage_option(id, type, device, snapshot_id, volume_type_id, size, iops, delete_on_termination, encrypted) values(2, "root", "/dev/xvda", NULL, 2, 8, "N/A", true, false);


-- Instance Configurations
insert into instance_configuration_option(id, name, default_value) values(1, "numInstances", "1");
insert into instance_configuration_option(id, name, default_value) values(2, "spotInstances", "false");
insert into instance_configuration_option(id, name, default_value) values(3, "network", "pc-801db5e5 (173.31.0.0/16) (default)");
insert into instance_configuration_option(id, name, default_value) values(4, "subnet", "vpc-801db5e5 (173.31.0.0/16) (default)");
insert into instance_configuration_option(id, name, default_value) values(5, "autoAssignPublicIp", "Use subnet setting (Enable)");
insert into instance_configuration_option(id, name, default_value) values(6, "shutdownBehavior", "stop");
insert into instance_configuration_option(id, name, default_value) values(7, "protected", "false");
insert into instance_configuration_option(id, name, default_value) values(8, "monitoring", "false");


-- Instance Options
insert into instance_option(id, image_id, instance_id) values(1, 2, 1);
insert into instance_option(id, image_id, instance_id) values(2, 2, 2);
insert into instance_option(id, image_id, instance_id) values(3, 3, 1);




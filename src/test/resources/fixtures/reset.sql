
delete from instance_volumes;

-- Reset volumes
delete from volume where id > 3;
alter table volume AUTO_INCREMENT = 4;
update volume set state = 1 where id = 1;

-- Reset instances
delete from instance_dns where dns > 3;
delete from instance_ip where ip > 3;
delete from instance_security_groups where instance > 3;
delete from instance where id > 3;
alter table instance AUTO_INCREMENT = 4;
update instance set name = "dev" where id = 1;
update instance set name = "production" where id = 2;


-- Reset snapshots
delete from snapshot where id > 4;
alter table snapshot AUTO_INCREMENT = 5;


-- Rest limits
update usage_limit set size = 20 where id = 1;
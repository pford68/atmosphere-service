insert into notification(id, user_id, message, opened, created_date) values(1, 1, "This is the first message for pford.", 0, UNIX_TIMESTAMP(now()) * 1000);
insert into notification(id, user_id, message, opened, created_date) values(2, 1, "This is the second message for pford.", 1, UNIX_TIMESTAMP(now()) * 1000);
insert into notification(id, user_id, message, opened, created_date) values(3, 1, "This is the third message for pford.", 0, UNIX_TIMESTAMP(now()) * 1000);
insert into notification(id, user_id, message, opened, created_date) values(4, 3, "This is the first message for jsmith.", 0, UNIX_TIMESTAMP(now()) * 1000);
insert into notification(id, user_id, message, opened, created_date) values(5, 3, "This is the second message for jsmith.", 0, UNIX_TIMESTAMP(now()) * 1000);
insert into notification(id, user_id, message, opened, created_date) values(10, 2, "This will be deleted", 1, UNIX_TIMESTAMP(now()) * 1000);
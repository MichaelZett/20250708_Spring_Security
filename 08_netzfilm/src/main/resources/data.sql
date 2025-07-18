insert into users (ID, VERSION, UUID, USERNAME, USER_TYPE, LAST_NAME, NAME, PASSWORD, BIRTHDATE, VIP, failed_attempts)
values (nextval('USER_SEQ'), 0, '43131897-34be-4ab6-9c6a-f7280690fb2a', 'erik', 'STAFF',
        'Hansen', 'Erik',
        '$argon2id$v=19$m=16384,t=2,p=1$FbMwISNN7ntUJGk3E0aN7g$JJ6sq9eZ19S779eWoEPMbG8nY+puH9M5SF2vgQLeksQ',
        '2005-06-21', false, 0);
insert into users (ID, VERSION, UUID, USERNAME, USER_TYPE, LAST_NAME, NAME, PASSWORD, BIRTHDATE, VIP, failed_attempts)
values (nextval('USER_SEQ'), 0, '68111897-39bd-4db6-9c6a-f7080690fb2a', 'petra', 'STAFF',
        'Meyer', 'Petra',
        '$argon2id$v=19$m=16384,t=2,p=1$40ZpkQRdmK2wwDEdw5Jr0g$yl4ZI/TOqG0kPhS1+2WmBoz7cTuD0//DJkWHwNPwXg0',
        '1963-07-11', false, 0);
insert into users (ID, VERSION, UUID, USERNAME, USER_TYPE, LAST_NAME, NAME, PASSWORD, BIRTHDATE, VIP, failed_attempts)
values (nextval('USER_SEQ'), 0, '97eb8fc7-3a5f-4774-bdc7-aeeef0964ff9', 'frank', 'STAFF',
        'Schuhmacher', 'Frank',
        '$argon2id$v=19$m=16384,t=2,p=1$Uu0TMp9HDZMo8KXfWglMHA$3SKzkTFfLsx2nq+giJjUrzF3OuUUfR0ax4PS4a2ILq4',
        '1981-02-28', false, 0);
insert into users (ID, VERSION, UUID, USERNAME, USER_TYPE, LAST_NAME, NAME, PASSWORD, BIRTHDATE, VIP, failed_attempts)
values (nextval('USER_SEQ'), 0, '47c4f5af-b29c-4376-9c31-47de05f0f7d2', 'klara', 'STAFF',
        'Wucherpfennig', 'Klara',
        '$argon2id$v=19$m=16384,t=2,p=1$PG9hfYhils+nkX5Y4qBGxQ$k7euRMxgg/9Jmb7BXeJ93GygLBt4vH307coydrAYtKM',
        '2015-02-25', false, 0);
insert into users (ID, VERSION, UUID, USERNAME, USER_TYPE, LAST_NAME, NAME, PASSWORD, BIRTHDATE, VIP, failed_attempts)
values (nextval('USER_SEQ'), 0, '77c4f5dd-b29c-4976-9c30-47cc00f0f7d2', 'anke', 'CUST',
        'Müller', 'Anke',
        '$argon2id$v=19$m=16384,t=2,p=1$95Uq5TxggmpNn0ORMj965A$qqhcn0y2/tXKSc2ixnl785ni5T0nuUmHoFbCl+x2E5U',
        '1975-09-03', false, 0);
insert into users (ID, VERSION, UUID, USERNAME, USER_TYPE, LAST_NAME, NAME, PASSWORD, BIRTHDATE, VIP, failed_attempts)
values (nextval('USER_SEQ'), 0, '984008c7-afee-4067-955e-0b55a21de59e', 'werner', 'CUST',
        'Schmidt', 'Werner',
        '$argon2id$v=19$m=16384,t=2,p=1$Q7exlIVjQn9U2LvTRckw5g$n6d+bxN6AebwqB2Bth+IZkrEMiU243YkvMCvoI4xnac',
        '1999-12-23', true, 0);
insert into users (ID, VERSION, UUID, USERNAME, USER_TYPE, LAST_NAME, NAME, PASSWORD, BIRTHDATE, VIP, failed_attempts)
values (nextval('USER_SEQ'), 0, '77c4f5ef-b29c-4976-9c30-47de00f0f7d2', 'fritz', 'CUST',
        'Schleicher', 'Fritzchen',
        '$argon2id$v=19$m=16384,t=2,p=1$SokR4Axl9Z0CrPwK+xk7HA$3o79OHQiZikxAkvfFR6355Dg9cGK7z/NKGIPto4Oxp4',
        '2015-02-25', false, 2);

insert into user_roles (USER_ID, ROLE)
values ((select id from users where uuid = '43131897-34be-4ab6-9c6a-f7280690fb2a'), 'ADMIN');
insert into user_roles (USER_ID, ROLE)
values ((select id from users where uuid = '68111897-39bd-4db6-9c6a-f7080690fb2a'), 'STAFF');
insert into user_roles (USER_ID, ROLE)
values ((select id from users where uuid = '68111897-39bd-4db6-9c6a-f7080690fb2a'), 'ADMIN');
insert into user_roles (USER_ID, ROLE)
values ((select id from users where uuid = '97eb8fc7-3a5f-4774-bdc7-aeeef0964ff9'), 'STAFF');
insert into user_roles (USER_ID, ROLE)
values ((select id from users where uuid = '97eb8fc7-3a5f-4774-bdc7-aeeef0964ff9'), 'VIP_STAFF');
insert into user_roles (USER_ID, ROLE)
values ((select id from users where uuid = '47c4f5af-b29c-4376-9c31-47de05f0f7d2'), 'ACCOUNT');
insert into user_roles (USER_ID, ROLE)
values ((select id from users where uuid = '77c4f5dd-b29c-4976-9c30-47cc00f0f7d2'), 'CUSTOMER');
insert into user_roles (USER_ID, ROLE)
values ((select id from users where uuid = '984008c7-afee-4067-955e-0b55a21de59e'), 'CUSTOMER');
insert into user_roles (USER_ID, ROLE)
values ((select id from users where uuid = '77c4f5ef-b29c-4976-9c30-47de00f0f7d2'), 'CUSTOMER');

insert into movie (RELEASE_DATE, FSK, ID, VERSION, UUID, TITLE)
values ('1977-5-25', 'FSK_12', nextval('MOVIE_SEQ'), 0, '984008c7-afee-4067-955e-0b55a21de59f', 'A new hope');
insert into movie (RELEASE_DATE, FSK, ID, VERSION, UUID, TITLE)
values ('1980-5-17', 'FSK_12', nextval('MOVIE_SEQ'), 0, '984008c7-afee-4067-955e-0b55a21de58e',
        'The Empire strikes back');
insert into movie (RELEASE_DATE, FSK, ID, VERSION, UUID, TITLE)
values ('1983-5-25', 'FSK_12', nextval('MOVIE_SEQ'), 0, '984008c7-afee-4067-955e-0b55a21de58f', 'Return of the Jedi');
insert into movie (RELEASE_DATE, FSK, ID, VERSION, UUID, TITLE)
values ('1999-12-1', 'FSK_18', nextval('MOVIE_SEQ'), 0, '984008c7-afee-4067-955e-0b55a21de59d', 'Boese');
insert into movie (RELEASE_DATE, FSK, ID, VERSION, UUID, TITLE)
values ('2011-08-04', 'FSK_0', nextval('MOVIE_SEQ'), 0, '3f1d0e94-5b2a-4ec1-9c2c-4a5a1d2e3f4b', 'Die Schlümpfe');

insert into users (ID, VERSION, UUID, USERNAME, USER_TYPE, LAST_NAME, NAME, PASSWORD, BIRTHDATE)
values (nextval('USER_SEQ'), 0, '43131897-34be-4ab6-9c6a-f7280690fb2a', 'erik', 'STAFF',
        'Hansen', 'Erik', '$2a$10$L6zH.uy2.68EKAk2H0fwKuQs.O3pZmWCfr5mCD5RCdnEzPc8vErke', '2005-06-21');
insert into users (ID, VERSION, UUID, USERNAME, USER_TYPE, LAST_NAME, NAME, PASSWORD, BIRTHDATE)
values (nextval('USER_SEQ'), 0, '68111897-39bd-4db6-9c6a-f7080690fb2a', 'petra', 'STAFF',
        'Meyer', 'Petra', '$2a$10$oEIF1UxQGGeyabMpLozetesFxEji9NFi6JUrUrBbjZGfVADOkPVpC', '1963-07-11');
insert into users (ID, VERSION, UUID, USERNAME, USER_TYPE, LAST_NAME, NAME, PASSWORD, BIRTHDATE)
values (nextval('USER_SEQ'), 0, '97eb8fc7-3a5f-4774-bdc7-aeeef0964ff9', 'frank', 'STAFF',
        'Schuhmacher', 'Frank', '$2a$10$UnxwBlVyptkR4zizSuvbY.dbMVaWi90aUAyqRqybiw/f5v4/QJtPy','1981-02-28');
insert into users (ID, VERSION, UUID, USERNAME, USER_TYPE, LAST_NAME, NAME, PASSWORD, BIRTHDATE)
values (nextval('USER_SEQ'), 0, '77c4f5dd-b29c-4976-9c30-47cc00f0f7d2', 'anke', 'CUST',
        'Müller', 'Anke', '$2a$10$8qWQ50HNvfopZeeFAjIYN.12VC8NhOlF7X3y6FmaDOjH9rtCwMuOS', '1975-09-03');
insert into users (ID, VERSION, UUID, USERNAME, USER_TYPE, LAST_NAME, NAME, PASSWORD, BIRTHDATE)
values (nextval('USER_SEQ'), 0, '984008c7-afee-4067-955e-0b55a21de59e', 'werner', 'CUST',
        'Schmidt', 'Werner', '$2a$10$fttUBJWQJWQGw5kHYA/UluRGxMsakJHAH84Im0R4cS2d9j8zb4CPi','1999-12-23');

insert into user_roles (USER_ID,ROLE) values ((select id from users where uuid = '43131897-34be-4ab6-9c6a-f7280690fb2a'),'ADMIN');
insert into user_roles (USER_ID,ROLE) values ((select id from users where uuid = '68111897-39bd-4db6-9c6a-f7080690fb2a'),'STAFF');
insert into user_roles (USER_ID,ROLE) values ((select id from users where uuid = '68111897-39bd-4db6-9c6a-f7080690fb2a'),'ADMIN');
insert into user_roles (USER_ID,ROLE) values ((select id from users where uuid = '97eb8fc7-3a5f-4774-bdc7-aeeef0964ff9'),'STAFF');
insert into user_roles (USER_ID,ROLE) values ((select id from users where uuid = '77c4f5dd-b29c-4976-9c30-47cc00f0f7d2'),'CUSTOMER');
insert into user_roles (USER_ID,ROLE) values ((select id from users where uuid = '984008c7-afee-4067-955e-0b55a21de59e'),'CUSTOMER');

insert into movie (RELEASE_DATE, FSK, ID, VERSION, UUID, TITLE)
values ('1977-5-25', 'FSK_12', nextval('MOVIE_SEQ'), 0, '984008c7-afee-4067-955e-0b55a21de59f', 'A new hope');
insert into movie (RELEASE_DATE, FSK, ID, VERSION, UUID, TITLE)
values ('1980-5-17', 'FSK_12', nextval('MOVIE_SEQ'), 0, '984008c7-afee-4067-955e-0b55a21de58e',
        'The Empire strikes back');
insert into movie (RELEASE_DATE, FSK, ID, VERSION, UUID, TITLE)
values ('1983-5-25', 'FSK_12', nextval('MOVIE_SEQ'), 0, '984008c7-afee-4067-955e-0b55a21de58f', 'Return of the Jedi');
insert into movie (RELEASE_DATE, FSK, ID, VERSION, UUID, TITLE)
values ('1999-12-1', 'FSK_18', nextval('MOVIE_SEQ'), 0, '984008c7-afee-4067-955e-0b55a21de59d', 'Boese');

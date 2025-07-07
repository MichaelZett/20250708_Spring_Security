insert into users (ID, VERSION, UUID, USERNAME, USER_TYPE, LAST_NAME, NAME, PASSWORD, BIRTHDATE)
values (nextval('USER_SEQ'), 0, '43131897-34be-4ab6-9c6a-f7280690fb2a', 'erik', 'STAFF',
        'Hansen', 'Erik',
        '$argon2id$v=19$m=16384,t=2,p=1$FbMwISNN7ntUJGk3E0aN7g$JJ6sq9eZ19S779eWoEPMbG8nY+puH9M5SF2vgQLeksQ',
        '2005-06-21');
insert into users (ID, VERSION, UUID, USERNAME, USER_TYPE, LAST_NAME, NAME, PASSWORD, BIRTHDATE)
values (nextval('USER_SEQ'), 0, '68111897-39bd-4db6-9c6a-f7080690fb2a', 'petra', 'STAFF',
        'Meyer', 'Petra',
        '$argon2id$v=19$m=16384,t=2,p=1$2DhnHlcnb2z1deZdalPPDQ$OBKas0ScGRNNfnvv36838vdWUQyZipSXZPtKPaimngs',
        '1963-07-11');
insert into users (ID, VERSION, UUID, USERNAME, USER_TYPE, LAST_NAME, NAME, PASSWORD, BIRTHDATE)
values (nextval('USER_SEQ'), 0, '97eb8fc7-3a5f-4774-bdc7-aeeef0964ff9', 'frank', 'STAFF',
        'Schuhmacher', 'Frank',
        '$argon2id$v=19$m=16384,t=2,p=1$XEYndbC9t5XvXlCXaFSMEQ$Ec0MZMvenzIhXV14/bdL42F+b3u4wFh0i4vZdWgGCCs',
        '1981-02-28');
insert into users (ID, VERSION, UUID, USERNAME, USER_TYPE, LAST_NAME, NAME, PASSWORD, BIRTHDATE)
values (nextval('USER_SEQ'), 0, '77c4f5dd-b29c-4976-9c30-47cc00f0f7d2', 'anke', 'CUST',
        'Müller', 'Anke',
        '$argon2id$v=19$m=16384,t=2,p=1$d9k6WhY1pc4KpjVKaHDjew$NNU3Ih1rKGNeqHDlgeZEO5KNgx7mvn8qe5dkjqsOXJk',
        '1975-09-03');
insert into users (ID, VERSION, UUID, USERNAME, USER_TYPE, LAST_NAME, NAME, PASSWORD, BIRTHDATE)
values (nextval('USER_SEQ'), 0, '984008c7-afee-4067-955e-0b55a21de59e', 'werner', 'CUST',
        'Schmidt', 'Werner',
        '$argon2id$v=19$m=16384,t=2,p=1$Q7exlIVjQn9U2LvTRckw5g$n6d+bxN6AebwqB2Bth+IZkrEMiU243YkvMCvoI4xnac',
        '1999-12-23');

insert into user_roles (USER_ID, ROLE)
values ((select id from users where uuid = '43131897-34be-4ab6-9c6a-f7280690fb2a'), 'ADMIN');
insert into user_roles (USER_ID, ROLE)
values ((select id from users where uuid = '68111897-39bd-4db6-9c6a-f7080690fb2a'), 'STAFF');
insert into user_roles (USER_ID, ROLE)
values ((select id from users where uuid = '68111897-39bd-4db6-9c6a-f7080690fb2a'), 'ADMIN');
insert into user_roles (USER_ID, ROLE)
values ((select id from users where uuid = '97eb8fc7-3a5f-4774-bdc7-aeeef0964ff9'), 'STAFF');
insert into user_roles (USER_ID, ROLE)
values ((select id from users where uuid = '77c4f5dd-b29c-4976-9c30-47cc00f0f7d2'), 'CUSTOMER');
insert into user_roles (USER_ID, ROLE)
values ((select id from users where uuid = '984008c7-afee-4067-955e-0b55a21de59e'), 'CUSTOMER');

insert into movie (RELEASE_DATE, FSK, ID, VERSION, UUID, TITLE)
values ('1977-5-25', 'FSK_12', nextval('MOVIE_SEQ'), 0, '984008c7-afee-4067-955e-0b55a21de59f', 'A new hope');
insert into movie (RELEASE_DATE, FSK, ID, VERSION, UUID, TITLE)
values ('1980-5-17', 'FSK_12', nextval('MOVIE_SEQ'), 0, '984008c7-afee-4067-955e-0b55a21de58e',
        'The Empire strikes back');
insert into movie (RELEASE_DATE, FSK, ID, VERSION, UUID, TITLE)
values ('1983-5-25', 'FSK_12', nextval('MOVIE_SEQ'), 0, '984008c7-afee-4067-955e-0b55a21de58f', 'Return of the Jedi');
insert into movie (RELEASE_DATE, FSK, ID, VERSION, UUID, TITLE)
values ('1999-12-1', 'FSK_18', nextval('MOVIE_SEQ'), 0, '984008c7-afee-4067-955e-0b55a21de59d', 'Boese');

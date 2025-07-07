insert into users (ID, VERSION, UUID, USERNAME, USER_TYPE, LAST_NAME, NAME, PASSWORD, BIRTHDATE)
values (nextval('USER_SEQ'), 0, '68111897-39bd-4db6-9c6a-f7080690fb2a', 'petra', 'STAFF',
        'Meyer', 'Petra', '$2a$10$oEIF1UxQGGeyabMpLozetesFxEji9NFi6JUrUrBbjZGfVADOkPVpC', '1963-07-11');
insert into users (ID, VERSION, UUID, USERNAME, USER_TYPE, LAST_NAME, NAME, PASSWORD, BIRTHDATE)
values (nextval('USER_SEQ'), 0, '97eb8fc7-3a5f-4774-bdc7-aeeef0964ff9', 'frank', 'STAFF',
        'Schuhmacher', 'Frank', '$2a$10$UnxwBlVyptkR4zizSuvbY.dbMVaWi90aUAyqRqybiw/f5v4/QJtPy', '1981-02-28');

insert into movie (RELEASE_DATE, FSK, ID, VERSION, UUID, TITLE)
values ('1977-5-25', 'FSK_12', nextval('MOVIE_SEQ'), 0, '984008c7-afee-4067-955e-0b55a21de59f', 'A new hope');
insert into movie (RELEASE_DATE, FSK, ID, VERSION, UUID, TITLE)
values ('1980-5-17', 'FSK_12', nextval('MOVIE_SEQ'), 0, '984008c7-afee-4067-955e-0b55a21de58e',
        'The Empire strikes back');
insert into movie (RELEASE_DATE, FSK, ID, VERSION, UUID, TITLE)
values ('1983-5-25', 'FSK_12', nextval('MOVIE_SEQ'), 0, '984008c7-afee-4067-955e-0b55a21de58f', 'Return of the Jedi');
insert into movie (RELEASE_DATE, FSK, ID, VERSION, UUID, TITLE)
values ('1999-12-1', 'FSK_18', nextval('MOVIE_SEQ'), 0, '984008c7-afee-4067-955e-0b55a21de59d', 'Boese');

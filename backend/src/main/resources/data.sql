CREATE TABLE PERSON (

    ID IDENTITY NOT NULL PRIMARY KEY,

    AVATAR CHARACTER VARYING(2048),

    FIRST_NAME CHARACTER VARYING(255) NOT NULL,

    LAST_NAME CHARACTER VARYING(255) NOT NULL

);

--ALTER TABLE PERSON ADD CONSTRAINT CONSTRAINT_87 PRIMARY KEY("ID");

insert into person (first_name, last_name, avatar)  values ('Leo', 'Gudas', 'https://gravatar.com/avatar/99df1a5b2917db695be7ad69e46d9164?s=400&d=robohash&r=x');
insert into person (first_name, last_name, avatar) values ('Roman', 'Sikora', 'https://gravatar.com/avatar/99df1a5b2917db695be7ad69e46d9164?s=400&d=robohash&r=x');

drop table accident_rules;
drop table accident;
drop table rules;
drop table types;



CREATE TABLE types (
   id serial primary key,
   name varchar(225)
);

CREATE TABLE accident (
    id serial primary key,
    name varchar(2000),
    type_id int references types(id)
);



CREATE TABLE rules (
    id serial primary key,
    name varchar(225)
);

CREATE TABLE accident_rules (
    accident_id int references accident(id),
    rule_id int references rules(id)
);

insert into types(name) values ('Две машины');
insert into types(name) values ('Машина и человек');
insert into types(name) values ('Машина и велосипед');

insert into  rules (name) values ('Статья. 1');
insert into  rules (name) values ('Статья. 2');
insert into  rules (name) values ('Статья. 3');

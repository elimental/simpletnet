insert into account (firstName, email, password, role) values
('Dima', 'elimental@bk.ru', 'qwerty', 'USER'),
('Vasya', 'vasya@bk.ru', 'qwerty', 'USER'),
('Petya', 'petya@bk.ru', 'qwerty', 'ADMINISTRATOR'),
('Masha', 'masha@bk.ru', 'qwerty', 'USER'),
('Kolya', 'Kolya@bk.ru', 'qwerty', 'USER');

insert into friend_request (accepted, from_id, to_id) values
(true, 1, 2),
(false, 1, 3),
(true, 4, 1),
(false, 5, 1);

insert into personal_message (text, createDate, from_id, to_id) values
('hi', '2018-11-20 10:27:01.519000', 1, 2),
('hello', '2018-11-20 10:27:01.519000', 2, 1),
('hey', '2018-11-20 10:27:01.519000', 2, 3);

insert into community (name, creatorId) values
('group1', 5),
('group2', 6);

insert into community_request (from_id, community_id, accepted) values
(1, 1, false),
(1, 2, true);









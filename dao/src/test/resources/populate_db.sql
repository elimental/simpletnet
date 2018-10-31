-- account

INSERT INTO account (firstName, lastName, patronymicName, birthDay, email, icq, skype, additionalInfo, password,
regDate, photo, userRole)
VALUES
('Dima', 'Andreev', 'Borisovich', '1978-02-13', 'elimental@bk.ru', '49224940', 'elimetal13', 'adfasdfasdf',
'DSFGDSGFDSGFDSGFDSGFSASFASDF', '2018-01-01', NULL, 2),
('Vasya', 'Petrov', 'Alekseevich', '1980-05-12', 'vasya@bk.ru', '12345678', 'vasiliy44', 'agfsdgfdgfds',
'WTRWE%$#@$%GWERGWEGWEGWEGRWE', '2018-02-02', NULL, 1);

-- account_groups

INSERT INTO account_groups (userId, groupId, userRole)
VALUES
(1, 2, 4), (1, 4, 5), (2, 4, 4), (2, 6, 4);

-- groups

INSERT INTO groups (name, owner, picture, createDate, description)
VALUES
('group1', 1, NULL, '2018-01-01', 'group1_description'), ('group2', 2, NULL, '2018-02-01', 'group2_description');

-- message

INSERT INTO message (text, createDate, author, type, destination)
VALUES
('ни чего не происходит', '2018-01-01 14:12:00', 1, 1, 1), ('хорошая группа', '2018-02-01 12:00:00', 1, 2, 4),
('привет', '2018-03-02 11:01:00', 1, 3, 4), ('здарова', '2018-04-02 11:01:00', 2, 3, 1);

-- phone

INSERT INTO phone (number, type, owner) VALUES (5555555, 2, 1), (6666666, 1, 1), (7777777, 1, 2);

-- relationship

INSERT INTO relationship (userOneId, userTwoId, status, lastActionUser)
VALUES
(1, 2, 1, 1), (2, 3, 2, 3), (4, 1, 2, 4);
DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM restaurants;
DELETE
FROM dishes;
DELETE
FROM votes;

ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001),
       ('ROLE_USER', 100001);


INSERT INTO restaurants (name)
VALUES ('TOKIO-CITY'),
       ('Italia'),
       ('KFC'),
       ('Beer House');

INSERT INTO dishes (restaurant_id, name, date, price)
VALUES (100002, 'TOKIO-CITY_dish1', '2020-09-11', 500),
       (100002, 'TOKIO-CITY_dish2', '2020-09-11', 800),
       (100002, 'TOKIO-CITY_dish3', '2020-09-11', 1500),
       (100002, 'TOKIO-CITY_dish4', '2020-09-11', 300),
       (100002, 'TOKIO-CITY_dish5', '2020-09-11', 900),
       (100003, 'Italia_dish1', '2020-09-12', 200),
       (100003, 'Italia_dish2', '2020-09-12', 300),
       (100003, 'Italia_dish3', '2020-09-12', 450),
       (100004, 'KFC_dish1', '2020-09-11', 100),
       (100004, 'KFC_dish2', '2020-09-11', 200),
       (100004, 'KFC_dish3', '2020-09-11', 400),
       (100004, 'KFC_dish4', '2020-09-11', 150),
       (100005, 'Italia_dish1', '2020-09-11', 600),
       (100005, 'Italia_dish2', '2020-09-11', 500),
       (100005, 'Italia_dish3', '2020-09-11', 450),
       (100005, 'Italia_dish4', '2020-09-11', 300),
       (100005, 'Italia_dish5', '2020-09-11', 800);

INSERT INTO votes (user_id, date, restaurant_id)
VALUES ('100000', '2020-09-12', '100002'),
       ('100001', '2020-09-11', '100004'),
       ('100001', '2020-09-12', '100003'),
       ('100000', '2020-09-13', '100003'),
       ('100000', '2020-09-11', '100005'),
       ('100001', '2020-09-13', '100003'),
       ('100000', '2020-09-14', '100002'),
       ('100000', '2020-09-15', '100005'),
       ('100001', now(), '100002');





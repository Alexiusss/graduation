##Task

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

Build a voting system for deciding where to have lunch.

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and couple curl commands to test it.

-----------------------------

#### REST API for Users

##### get All Users
`curl -s http://localhost:8080/graduation/admin/users --user admin@gmail.com:admin`

##### get Users 100001
`curl -s http://localhost:8080/graduation/admin/users/100001 --user admin@gmail.com:admin`

##### register Users
`curl -s -i -X POST -d '{"name":"New User","email":"newmail@ya.ru","password":"newPassword"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/profile/register`

##### get Profile
`curl -s http://localhost:8080/graduation/profile --user user@yandex.ru:password`

<br>

#### REST API for Votes

##### get All votes for authorized User
`curl -s http://localhost:8080/graduation/votes --user user@yandex.ru:password`

##### vote for Restaurant 100003
`curl -s POST -d 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/votes?restaurantId=100003 --user user@yandex.ru:password`

##### re-vote for Restaurant 100004
`curl -s POST -d 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/votes?restaurantId=100004 --user admin@gmail.com:admin`

<br>

#### REST API for Restaurants

##### get All Restaurants
`curl -s http://localhost:8080/graduation/admin/restaurants --user admin@gmail.com:admin`

##### get Restaurants 100003
`curl -s http://localhost:8080/graduation/admin/restaurants/100003 --user admin@gmail.com:admin`

##### get All Restaurants with today's menu 
`curl -s http://localhost:8080/graduation/profile/restaurants/menu --user user@yandex.ru:password`

##### get Restaurants 100003 with today's menu
`curl -s http://localhost:8080/graduation/profile/restaurants/100003/menu --user user@yandex.ru:password`

##### get Restaurants not found
`curl -s -v http://localhost:8080/graduation/profile/restaurants/100008/menu --user user@yandex.ru:password`

##### create Restaurants
`curl -s -i -X POST -d '{"name":"NewRestaurant"}' -H 'Content-Type:application/json' http://localhost:8080/graduation/admin/restaurants --user admin@gmail.com:admin`

##### update Restaurants 100003
`curl -s -i -X PUT -d '{"name":"UpdatedRestaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/admin/restaurants/100003 --user admin@gmail.com:admin`

##### delete Restaurants
`curl -s -X DELETE http://localhost:8080/graduation/admin/restaurants/100004 --user admin@gmail.com:admin`

<br>


#### REST API for Dishes


##### get All Dishes for Restaurant 100002
`curl -s http://localhost:8080/graduation/admin/restaurants/100002/dishes --user admin@gmail.com:admin`
##### create a new Dish for Restaurant 100002
`curl -s -i -X POST -d '{"name":"NewDish","date":"2020-09-11","price":100}' -H 'Content-Type:application/json' http://localhost:8080/graduation/admin/restaurants/100002/dishes/ --user admin@gmail.com:admin`
##### update Dishes 
`curl -s -i -X PUT -d '{"id":100011,"name":"UpdatedDish","date":"2020-09-12","price":300}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/admin/restaurants/100003/dishes/100011 --user admin@gmail.com:admin`
##### delete Dishes
`curl -s -X DELETE http://localhost:8080/graduation/admin/restaurants/100003/dishes/100011 --user admin@gmail.com:admin`

<br>

#### validate with Error
`curl -s -X POST -d '{}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/graduation/admin/users --user admin@gmail.com:admin`
`curl -s -i -X PUT -d '{"name":" "}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/admin/restaurants/100003 --user admin@gmail.com:admin`
`curl -s -i -X PUT -d '{"id":100011,"name":" ","date":"2020-09-12","price":0}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/admin/restaurants/100003/dishes/100011 --user admin@gmail.com:admin`
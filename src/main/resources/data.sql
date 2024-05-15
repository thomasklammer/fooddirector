insert into tb_addresses(id, city, zipcode, street, housenumber) values(1001, 'Innsbruck', '6020', 'Universitätsstraße', '15');
insert into tb_addresses(id, city, zipcode, street, housenumber) values(1002, 'Sillian', '9920', 'Dorf', '92G/14');
insert into tb_addresses(id, city, zipcode, street, housenumber) values(1003, 'Sillian', '9920', 'Dorf', '185 h');
insert into tb_addresses(id, city, zipcode, street, housenumber) values(1004, 'Lienz', '9900', 'Emanuel v. Hiblerstrasse', '3 a');

INSERT INTO tb_users(id, email, password, firstname, lastname, deliveryaddress_id, isadmin) values
(1001, 'test@test.at', '123456', 'Max', 'Mustermann', 1001, 0),
(1002, 'admin@test.at', '123456', 'Sieglinde', 'Musterfrau', 1004, 1);

insert into tb_articles(id, name, netprice, taxrate, description, articlecategory, dailyoffer, discount) values
(1001, 'Wiener Schnitzel', 10.99, 20, 'Das ist ein Wiener Schnitzel', 1, 0, 0),
(1002, 'Knoblauchcremesuppe', 5.30, 20, 'Knutschen ist danach nicht mehr drin', 0, 0, 0),
(1003, 'Dessertvariation', 7.80, 20, 'Sehr köstlich', 2, 0, 0),
(1004, 'Jägerschnitzel', 14.70, 20, 'Ein Schnitzel nur für Jäger', 1, 1, 10),
(1005, 'Nudelpfanne', 8.50, 20, 'Beste Nudelpfanne in der Stadt', 1, 1, 20),
(1006, 'Sprite', 2.99, 10, 'Kaltes Getränk', 4, 0, 0),
(1007, 'Pommes', 4.50, 20, 'Fritten dazu?', 3, 0, 0);


insert into tb_orders(id, orderdate, paymentmethod, orderstatus, user_id, deliveryaddress_id) values
(1001, '2024-04-14 16:22:10', 0, 0, 1001, 1002),
(1002, '2024-05-14 17:22:10', 2, 1, 1001, 1003);


insert into tb_order_details(id, order_id, article_id, netvalue, taxrate, amount, note) values
(1001, 1001, 1001, 16.22, 20, 2, 'Ohne Tomaten'),
(1002, 1001, 1007, 4.50, 20, 1, ''),
(1003, 1001, 1006, 2.99, 10, 2, 'Ohne Sprudel'),
(1004, 1002, 1002, 3.33, 20, 1, 'Ohne Tomaten'),
(1005, 1002, 1003, 4.44, 20, 1, 'Ohne Tomaten');
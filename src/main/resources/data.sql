insert into tb_addresses(id, city, zipcode, street, housenumber) values(1, 'Innsbruck', '6020', 'Universitätsstraße', '15');
insert into tb_addresses(id, city, zipcode, street, housenumber) values(2, 'Sillian', '9920', 'Dorf', '92G/14');
insert into tb_addresses(id, city, zipcode, street, housenumber) values(3, 'Sillian', '9920', 'Dorf', '185 h');

INSERT INTO tb_users(id, email, password, firstname, lastname, deliveryaddress_id, isadmin)
values(1, 'test@test.at', '123456', 'Max', 'Mustermann', 1, 1);

insert into tb_articles(id, name, netprice, taxrate, description, articlecategory, dailyoffer) values
(1, 'Wiener Schnitzel', 10.99, 20, 'Das ist ein Wiener Schnitzel', 1, 0),
(2, 'Knoblauchcremesuppe', 5.30, 20, 'Knutschen ist danach nicht mehr drin', 0, 0),
(3, 'Dessertvariation', 7.80, 20, 'Sehr köstlich', 2, 0),
(4, 'Jägerschnitzel', 14.70, 20, 'Ein Schnitzel nur für Jäger', 1, 1),
(5, 'Nudelpfanne', 8.50, 20, 'Beste Nudelpfanne in der Stadt', 1, 1),
(6, 'Sprite', 2.99, 10, 'Kaltes Getränk', 4, 0),
(7, 'Pommes', 4.50, 20, 'Fritten dazu?', 3, 0);


insert into tb_orders(id, orderdate, paymentmethod, orderstatus, user_id, deliveryaddress_id) values
(1, '2024-05-14 16:22:10', 0, 0, 1, 2),
(2, '2024-05-14 17:22:10', 2, 1, 1, 3);


insert into tb_order_details(id, order_id, article_id, netvalue, taxrate, amount, note) values
(1, 1, 1, 16.22, 20, 2, 'Ohne Tomaten'),
(2, 1, 7, 4.50, 20, 1, ''),
(3, 1, 6, 2.99, 10, 2, 'Ohne Sprudel'),
(4, 2, 2, 3.33, 20, 1, 'Ohne Tomaten'),
(5, 2, 3, 4.44, 20, 1, 'Ohne Tomaten');
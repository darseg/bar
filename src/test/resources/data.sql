delete from order_offer;
delete from orders;
delete from auth_info;
delete from user;
delete from recipe_row;
delete from table_image;
delete from tables;
delete from offer_param;
delete from storehouse;
delete from ingredient;
delete from offer;


insert into user values (1, 'Dars', 'Kim Den', '12345', 0),
	(2, 'BenDelat', 'Быстрый Бен', '12346', 1),
	(3, 'LadyEnvy', 'Леди Зависть', '1234', 1),
	(4, 'LadySpite', 'Леди Злоба', '1234123', 1),
	(5, 'KalamMekhar', 'Калам Мекхар', '12', 1),
	(6, 'OnosToolan', 'Онос Тоолан', '123', 1),
	(7, 'TavoreParan', 'Тавора Паран', '312', 1);

insert into auth_info values (1, 'Dars', '$2a$10$59kGaYi3CCyCG/0fgj4Op.kUbTAFA3FMvPKWhSu/3CNZAApxn9W8u', 1), 
	(2, 'BenDelat', '$2a$10$lM8ei5bTFQztDDqfKbx9YuI9EiocpWQJvmqFp/lTjnLHMZKatVD8O', 2),
	(3, 'LadyEnvy', '$2a$10$5BVE3mw1h7rD1U5w.qesWOte71ogJGMHdmwb6yziOnMnTsMAgJdtG', 3),
	(4, 'LadySpite', '$2a$10$kJ3QrkijA1M8GE8ZtibiNuyt52vSJ2goLVGM5.i3wxNSB2oQs7llq', 4),
	(5, 'KalamMekhar', '$2a$10$aFI4P8HOtz1rBIbZuMx4PeuYd/Bxx8jg90eu8NerWhKAO7roltzBS', 5),
	(6, 'OnosToolan', '$2a$10$q1bfr7RVR2LJdRK2fAiOx.PVP6DUaxndkK59Tfqhc3yb5dvX/5o8a', 6),
	(7, 'TavoreParan', '$2a$10$HDQr6KgmupAueaZN8pEv7.exbAulbxCDETVNhCFjhRWB.LL.olsGS', 7);
	/*
	atata
	ototo
	piupiu
	atato
	otota
	atatata
	aaa
	*/

insert into tables values (1, 'table 1', 'at window', 4, true),
	(2, 'table 2', 'true table', 5, true),
	(3, 'table 3', 'near kitchen', 3, true),
	(4, 'table 4', 'at window', 3, true),
	(5, 'bar rack', 'atata', 5, false);

insert into table_image values (1, 1, 'img1'), (2, 1, 'img2'), (3, 1, 'img3'), 
	(4, 2, 'img4'), (5, 2, 'img5'), (6, 2, 'img6'), (7, 2, 'img7'), 
	(8, 3, 'img8'), (9, 3, 'img9'), (10, 3, 'img10'), (11, 4, 'img11');

insert into ingredient values
	(1, 'Жигули', '5.6'),
	(2, 'Ноги', '1'),
	(3, 'Панировка для ног', '0.5'),
	(4, 'Old Bobby', '4'),
	(5, 'Сыр Чеддер', '20');

insert into storehouse values
	(1, 1, '560', '600'),
	(2, 2, '26', '500'),
	(3, 3, '1000', '1030'),
	(4, 4, '2', '40'),
	(5, 5, '3', '500');

insert into offer values (1, 0, 'Жигули', 'Четкое пиво', 20), 
	(2, 0, 'Old bobby', 'не такое четкое пиво', 12),
	(3, 1, 'Ноги Буша', 'Так себе закусь', 10),
	(4, 1, 'Сырная нарезка', 'Хороша', 12);

insert into offer_param values (1, 1, 'param1', '1'),
	(2, 1, 'param2', '2'),
	(3, 2, 'param1', '3'),
	(4, 3, 'calories', 'много'),
	(5, 4, 'calories', 'очень много');

insert into recipe_row values (1, 1, 1), (2, 4, 1),
	(3, 2, 4), (3, 3, 1), (4, 5, 1);

insert into orders values (2, 2, 5, '2020-02-26T19:00', '2020-02-26T23:00', false);


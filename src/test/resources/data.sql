insert into user values (1, 'Kim Den', '1234', 0),
	(2, 'Ben Delat', '12346', 1),
	(3, 'Lady Envy', '12345', 1),
	(4, 'Lady Spite', '1234123', 1);

insert into auth_info values (1, 'Dars', 'atata', 1),
	(2, 'BenDelat', 'ototo', 2),
	(3, 'LadyEnvy', 'piupiu', 3),
	(4, 'LadySpite', 'atato', 4);

insert into tables values (1, 'table 1', 'at window', 4, true),
	(2, 'table 2', 'true table', 5, true),
	(3, 'table 3', 'near kitchen', 3, true),
	(4, 'table 4', 'at window', 3, true),
	(5, 'bar rack', 'atata', 5, false);

insert into table_image values (1, 1, "img1"), (2, 1, "img2"), (3, 1, "img3"),
	(4, 2, "img4"), (5, 2, "img5"), (6, 2, "img6"), (7, 2, "img7"),
	(8, 3, "img8"), (9, 3, "img9"), (10, 3, "img10"), (11, 4, "img11");

insert into ingredient values
	(1, 'Жигули', '5.6', '560', '600'),
	(2, 'Ноги', '1', '26', '500'),
	(3, 'Панировка для ног', '0.5', '1000', '1030'),
	(4, 'Old Bobby', '4', '2', '40'),
	(5, 'Сыр Чеддер', '20', '3', '500');

insert into offer values (1, 0, 'Жигули', 'Четкое пиво', 20),
	(2, 0, 'Old bobby', 'не такое четкое пиво', 12),
	(3, 1, 'Ноги Буша', 'Так себе закусь', 10),
	(4, 1, 'Сырная нарезка', 'Хороша', 12);

insert into offer_param values (1, 1, 'param1', '1'),
	(2, 1, 'param2', '2'),
	(3, 2, 'param1', '3'),
	(4, 3, 'calories', 'много'),
	(5, 4, 'calories', 'очень много');

insert into recipe_row values (1, 1, 1, 1), (2, 2, 4, 1),
	(3, 3, 2, 4), (4, 3, 3, 1), (5, 4, 5, 1);

insert into orders values (1, 5, 2, '2020-02-25T19:00', '2020-02-25T23:00', false),
	(2, 5, 3, '2020-02-25T19:00', '2020-02-25T23:00', false);
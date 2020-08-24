drop table if exists location;
create table location (
	id serial primary key,
	name varchar(255),
	description varchar(2000)
);

drop table if exists category;
create table category (
	id serial primary key,
	name varchar(255),
	description varchar(2000)
);

drop table if exists area;
create table area (
	id serial primary key,
	name varchar(255),
	description varchar(2000),
	location_id int
);

drop table if exists score;
create table score (
	id serial primary key,
	area_id int,
	category_id int,
	location_id int,
	score int
);

insert into location values (1, 'Stockholm', 'Capital of Sweden');
insert into location values (2, 'Barcelona', 'Capital of Catalonia, Spain');

insert into category values (1, 'Communication', 'How close to buses, trains, metro');
insert into category values (2, 'Cost of living', 'How high the cost of living');
insert into category values (3, 'Family friendly', 'How suitable for kids, how quiet, quality of schools');
insert into category values (4, 'Green areas', 'How close to green areas, parks, nature');
insert into category values (5, 'Nightlife', 'How many restaurants, bars, clubs, theaters, shows');
insert into category values (6, 'Sea view', 'How close to the water, beach');

-- https://en.wikipedia.org/wiki/Danderyd_Municipality
insert into area values (1, 'Danderyd', 'Affluent municipality north of Stockholm', 1);
-- https://en.wikipedia.org/wiki/Ekerö_Municipality
insert into area values (2, 'Ekerö', 'Rural municipality of islands east of Stockholm. Summer home of the royal family', 1);
-- https://en.wikipedia.org/wiki/Gamla_stan
insert into area values (3, 'Gamla Stan', 'The old town central area of Stockholm', 1);
-- https://en.wikipedia.org/wiki/Hammarby_Sjöstad
insert into area values (4, 'Hammarby Sjöstad', 'Newly developed district of central Stockholm. Middle to high income', 1);
-- https://en.wikipedia.org/wiki/Kungsholmen
insert into area values (5, 'Kungsholmen', 'Classic middle to high income area in central Stockholm', 1);
-- https://en.wikipedia.org/wiki/Lidingö
insert into area values (6, 'Lidingö', 'Island municipality north of Stockholm. Third wealthiest after Danderyd and Täby', 1);
-- https://en.wikipedia.org/wiki/Nacka_Municipality
insert into area values (7, 'Nacka', 'Coastal municipality west of Stockholm. Area with high income and eduation', 1);
-- https://en.wikipedia.org/wiki/Norrmalm
insert into area values (8, 'Norrmalm', 'Exclusive city district of Stockholm. Mostly shopping and office buildings', 1);
-- https://en.wikipedia.org/wiki/Södermalm
insert into area values (9, 'Södermalm', 'Island city district of southern Stockholm. Famously "bohemian", but now gentrified', 1);
-- https://en.wikipedia.org/wiki/Täby_Municipality
insert into area values (10, 'Täby', 'Wealthy municipality, second after Danderyd. Suburb in the north of Stockholm', 1);
-- https://en.wikipedia.org/wiki/Östermalm
insert into area values (11, 'Östermalm', 'Exclusive central Stockholm city district. Highest housing prices in Sweden', 1);

-- Categories (score 1-5):
-- 1. Communication - How close to buses, trains, metro
-- 2. Cost of living - How high the cost of living
-- 3. Family friendly - How suitable for kids, how quiet, quality of schools
-- 4. Green areas - How close to green areas, parks, nature
-- 5. Nightlife - How many restaurants, bars, clubs, theaters, shows
-- 6. Sea view - How close to the water, beach

-- Danderyd
insert into score values (1, 1, 1, 1, 3);
insert into score values (2, 1, 2, 1, 5);
insert into score values (3, 1, 3, 1, 5);
insert into score values (4, 1, 4, 1, 3);
insert into score values (5, 1, 5, 1, 1);
insert into score values (6, 1, 6, 1, 1);

-- Ekerö
insert into score values (7, 2, 1, 1, 2);
insert into score values (8, 2, 2, 1, 4);
insert into score values (9, 2, 3, 1, 4);
insert into score values (10, 2, 4, 1, 5);
insert into score values (11, 2, 5, 1, 1);
insert into score values (12, 2, 6, 1, 5);

-- Gamla Stan
insert into score values (13, 3, 1, 1, 5);
insert into score values (14, 3, 2, 1, 4);
insert into score values (15, 3, 3, 1, 3);
insert into score values (16, 3, 4, 1, 1);
insert into score values (17, 3, 5, 1, 4);
insert into score values (18, 3, 6, 1, 3);

-- Hammarby Sjöstad
insert into score values (19, 4, 1, 1, 4);
insert into score values (20, 4, 2, 1, 4);
insert into score values (21, 4, 3, 1, 4);
insert into score values (22, 4, 4, 1, 3);
insert into score values (23, 4, 5, 1, 3);
insert into score values (24, 4, 6, 1, 5);

-- Kungsholmen
insert into score values (25, 5, 1, 1, 5);
insert into score values (26, 5, 2, 1, 4);
insert into score values (27, 5, 3, 1, 4);
insert into score values (28, 5, 4, 1, 3);
insert into score values (29, 5, 5, 1, 5);
insert into score values (30, 5, 6, 1, 3);

-- Lidingö
insert into score values (31, 6, 1, 1, 3);
insert into score values (32, 6, 2, 1, 5);
insert into score values (33, 6, 3, 1, 5);
insert into score values (34, 6, 4, 1, 4);
insert into score values (35, 6, 5, 1, 2);
insert into score values (36, 6, 6, 1, 5);

-- Nacka
insert into score values (37, 7, 1, 1, 3);
insert into score values (38, 7, 2, 1, 4);
insert into score values (39, 7, 3, 1, 5);
insert into score values (40, 7, 4, 1, 5);
insert into score values (41, 7, 5, 1, 2);
insert into score values (42, 7, 6, 1, 5);

-- Norrmalm
insert into score values (43, 8, 1, 1, 5);
insert into score values (44, 8, 2, 1, 5);
insert into score values (45, 8, 3, 1, 3);
insert into score values (46, 8, 4, 1, 2);
insert into score values (47, 8, 5, 1, 5);
insert into score values (48, 8, 6, 1, 1);

-- Södermalm
insert into score values (49, 9, 1, 1, 5);
insert into score values (50, 9, 2, 1, 4);
insert into score values (51, 9, 3, 1, 4);
insert into score values (52, 9, 4, 1, 4);
insert into score values (53, 9, 5, 1, 5);
insert into score values (54, 9, 6, 1, 2);

-- Täby
insert into score values (55, 10, 1, 1, 3);
insert into score values (56, 10, 2, 1, 5);
insert into score values (57, 10, 3, 1, 5);
insert into score values (58, 10, 4, 1, 4);
insert into score values (59, 10, 5, 1, 2);
insert into score values (60, 10, 6, 1, 3);

-- Östermalm
insert into score values (61, 11, 1, 1, 5);
insert into score values (62, 11, 2, 1, 5);
insert into score values (63, 11, 3, 1, 4);
insert into score values (64, 11, 4, 1, 3);
insert into score values (65, 11, 5, 1, 5);
insert into score values (66, 11, 6, 1, 1);

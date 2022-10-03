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

insert into location (name, description) values ('Stockholm', 'Capital of Sweden');
insert into location (name, description) values ('Barcelona', 'Capital of Catalonia, Spain');

insert into category (name, description) values ('Communication', 'How close to buses, trains, metro');
insert into category (name, description) values ('Cost of living', 'How high the cost of living');
insert into category (name, description) values ('Family friendly', 'How suitable for kids, how quiet, quality of schools');
insert into category (name, description) values ('Green areas', 'How close to green areas, parks, nature');
insert into category (name, description) values ('Nightlife', 'How many restaurants, bars, clubs, theaters, shows');
insert into category (name, description) values ('Sea view', 'How close to the water, beach');

-- Note: Citation characters (', ") not allowed in description field.
-- https://en.wikipedia.org/wiki/Danderyd_Municipality
insert into area (name, description, location_id) values ('Danderyd', 'Affluent municipality north of Stockholm<br/><br/><a href=https://en.wikipedia.org/wiki/Danderyd_Municipality>More &raquo;</a>', 1);
-- https://en.wikipedia.org/wiki/Ekerö_Municipality
insert into area (name, description, location_id) values ('Ekerö', 'Rural municipality of islands east of Stockholm. Summer home of the royal family<br/><br/><a href=https://en.wikipedia.org/wiki/Ekerö_Municipality>More &raquo;</a>', 1);
-- https://en.wikipedia.org/wiki/Gamla_stan
insert into area (name, description, location_id) values ('Gamla Stan', 'The old town central area of Stockholm<br/><br/><a href=https://en.wikipedia.org/wiki/Gamla_stan>More &raquo;</a>', 1);
-- https://en.wikipedia.org/wiki/Hammarby_Sjöstad
insert into area (name, description, location_id) values ('Hammarby Sjöstad', 'Newly developed district of central Stockholm. Middle to high income<br/><br/><a href=https://en.wikipedia.org/wiki/Hammarby_Sjöstad>More &raquo;</a>', 1);
-- https://en.wikipedia.org/wiki/Kungsholmen
insert into area (name, description, location_id) values ('Kungsholmen', 'Classic middle to high income area in central Stockholm<br/><br/><a href=https://en.wikipedia.org/wiki/Kungsholmen>More &raquo;</a>', 1);
-- https://en.wikipedia.org/wiki/Lidingö
insert into area (name, description, location_id) values ('Lidingö', 'Island municipality north of Stockholm. Third wealthiest after Danderyd and Täby<br/><br/><a href=https://en.wikipedia.org/wiki/Lidingö>More &raquo;</a>', 1);
-- https://en.wikipedia.org/wiki/Nacka_Municipality
insert into area (name, description, location_id) values ('Nacka', 'Coastal municipality west of Stockholm. Area with high income and eduation<br/><br/><a href=https://en.wikipedia.org/wiki/Nacka_Municipality>More &raquo;</a>', 1);
-- https://en.wikipedia.org/wiki/Norrmalm
insert into area (name, description, location_id) values ('Norrmalm', 'Exclusive city district of Stockholm. Mostly shopping and office buildings<br/><br/><a href=https://en.wikipedia.org/wiki/Norrmalm>More &raquo;</a>', 1);
-- https://en.wikipedia.org/wiki/Södermalm
insert into area (name, description, location_id) values ('Södermalm', 'Island city district of southern Stockholm. Famously bohemian, but now gentrified<br/><br/><a href=https://en.wikipedia.org/wiki/Södermalm>More &raquo;</a>', 1);
-- https://en.wikipedia.org/wiki/Täby_Municipality
insert into area (name, description, location_id) values ('Täby', 'Wealthy municipality, second after Danderyd. Suburb in the north of Stockholm<br/><br/><a href=https://en.wikipedia.org/wiki/Täby_Municipality>More &raquo;</a>', 1);
-- https://en.wikipedia.org/wiki/Östermalm
insert into area (name, description, location_id) values ('Östermalm', 'Exclusive central Stockholm city district. Highest housing prices in Sweden<br/><br/><a href=https://en.wikipedia.org/wiki/Östermalm>More &raquo;</a>', 1);

-- Categories (score 1-5):
-- 1. Communication - How close to buses, trains, metro
-- 2. Cost of living - How high the cost of living
-- 3. Family friendly - How suitable for kids, how quiet, quality of schools
-- 4. Green areas - How close to green areas, parks, nature
-- 5. Nightlife - How many restaurants, bars, clubs, theaters, shows
-- 6. Sea view - How close to the water, beach

-- Danderyd
insert into score (area_id, category_id, location_id, score) values (1, 1, 1, 3);
insert into score (area_id, category_id, location_id, score) values (1, 2, 1, 5);
insert into score (area_id, category_id, location_id, score) values (1, 3, 1, 5);
insert into score (area_id, category_id, location_id, score) values (1, 4, 1, 3);
insert into score (area_id, category_id, location_id, score) values (1, 5, 1, 1);
insert into score (area_id, category_id, location_id, score) values (1, 6, 1, 1);

-- Ekerö
insert into score (area_id, category_id, location_id, score) values (2, 1, 1, 2);
insert into score (area_id, category_id, location_id, score) values (2, 2, 1, 4);
insert into score (area_id, category_id, location_id, score) values (2, 3, 1, 4);
insert into score (area_id, category_id, location_id, score) values (2, 4, 1, 5);
insert into score (area_id, category_id, location_id, score) values (2, 5, 1, 1);
insert into score (area_id, category_id, location_id, score) values (2, 6, 1, 5);

-- Gamla Stan
insert into score (area_id, category_id, location_id, score) values (3, 1, 1, 5);
insert into score (area_id, category_id, location_id, score) values (3, 2, 1, 4);
insert into score (area_id, category_id, location_id, score) values (3, 3, 1, 3);
insert into score (area_id, category_id, location_id, score) values (3, 4, 1, 1);
insert into score (area_id, category_id, location_id, score) values (3, 5, 1, 4);
insert into score (area_id, category_id, location_id, score) values (3, 6, 1, 3);

-- Hammarby Sjöstad
insert into score (area_id, category_id, location_id, score) values (4, 1, 1, 4);
insert into score (area_id, category_id, location_id, score) values (4, 2, 1, 4);
insert into score (area_id, category_id, location_id, score) values (4, 3, 1, 4);
insert into score (area_id, category_id, location_id, score) values (4, 4, 1, 3);
insert into score (area_id, category_id, location_id, score) values (4, 5, 1, 3);
insert into score (area_id, category_id, location_id, score) values (4, 6, 1, 5);

-- Kungsholmen
insert into score (area_id, category_id, location_id, score) values (5, 1, 1, 5);
insert into score (area_id, category_id, location_id, score) values (5, 2, 1, 4);
insert into score (area_id, category_id, location_id, score) values (5, 3, 1, 4);
insert into score (area_id, category_id, location_id, score) values (5, 4, 1, 3);
insert into score (area_id, category_id, location_id, score) values (5, 5, 1, 5);
insert into score (area_id, category_id, location_id, score) values (5, 6, 1, 3);

-- Lidingö
insert into score (area_id, category_id, location_id, score) values (6, 1, 1, 3);
insert into score (area_id, category_id, location_id, score) values (6, 2, 1, 5);
insert into score (area_id, category_id, location_id, score) values (6, 3, 1, 5);
insert into score (area_id, category_id, location_id, score) values (6, 4, 1, 4);
insert into score (area_id, category_id, location_id, score) values (6, 5, 1, 2);
insert into score (area_id, category_id, location_id, score) values (6, 6, 1, 5);

-- Nacka
insert into score (area_id, category_id, location_id, score) values (7, 1, 1, 3);
insert into score (area_id, category_id, location_id, score) values (7, 2, 1, 4);
insert into score (area_id, category_id, location_id, score) values (7, 3, 1, 5);
insert into score (area_id, category_id, location_id, score) values (7, 4, 1, 5);
insert into score (area_id, category_id, location_id, score) values (7, 5, 1, 2);
insert into score (area_id, category_id, location_id, score) values (7, 6, 1, 5);

-- Norrmalm
insert into score (area_id, category_id, location_id, score) values (8, 1, 1, 5);
insert into score (area_id, category_id, location_id, score) values (8, 2, 1, 5);
insert into score (area_id, category_id, location_id, score) values (8, 3, 1, 3);
insert into score (area_id, category_id, location_id, score) values (8, 4, 1, 2);
insert into score (area_id, category_id, location_id, score) values (8, 5, 1, 5);
insert into score (area_id, category_id, location_id, score) values (8, 6, 1, 1);

-- Södermalm
insert into score (area_id, category_id, location_id, score) values (9, 1, 1, 5);
insert into score (area_id, category_id, location_id, score) values (9, 2, 1, 4);
insert into score (area_id, category_id, location_id, score) values (9, 3, 1, 4);
insert into score (area_id, category_id, location_id, score) values (9, 4, 1, 4);
insert into score (area_id, category_id, location_id, score) values (9, 5, 1, 5);
insert into score (area_id, category_id, location_id, score) values (9, 6, 1, 2);

-- Täby
insert into score (area_id, category_id, location_id, score) values (10, 1, 1, 3);
insert into score (area_id, category_id, location_id, score) values (10, 2, 1, 5);
insert into score (area_id, category_id, location_id, score) values (10, 3, 1, 5);
insert into score (area_id, category_id, location_id, score) values (10, 4, 1, 4);
insert into score (area_id, category_id, location_id, score) values (10, 5, 1, 2);
insert into score (area_id, category_id, location_id, score) values (10, 6, 1, 3);

-- Östermalm
insert into score (area_id, category_id, location_id, score) values (11, 1, 1, 5);
insert into score (area_id, category_id, location_id, score) values (11, 2, 1, 5);
insert into score (area_id, category_id, location_id, score) values (11, 3, 1, 4);
insert into score (area_id, category_id, location_id, score) values (11, 4, 1, 3);
insert into score (area_id, category_id, location_id, score) values (11, 5, 1, 5);
insert into score (area_id, category_id, location_id, score) values (11, 6, 1, 1);

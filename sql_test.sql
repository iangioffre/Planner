/*
	Programmers: Ian Gioffre, Kelsey Lally, Bryan Fischer, Alex Weaver
	Course: Database Management Systems - CPSC 321
	Assignment: Project part 3
	Date: 11/12/2019
	Description: Testing SQL statements in sqlite3
*/
DROP TABLE IF EXISTS assignments;
DROP TABLE IF EXISTS meetings;
DROP TABLE IF EXISTS course;

CREATE TABLE course (
	name VARCHAR(50),
	on_monday BOOLEAN,
	on_tuesday BOOLEAN, 
	on_wednesday BOOLEAN,
	on_thursday BOOLEAN,
	on_friday BOOLEAN,
	start_time DATETIME,
	end_time DATETIME,
	PRIMARY KEY (name)	
);

CREATE TABLE assignments (
	title VARCHAR(100),
	date_time DATETIME,
	course VARCHAR(50),
	priority INT UNSIGNED,
	is_done BOOLEAN, 
	notes VARCHAR(255),
	PRIMARY KEY (title, course),
	FOREIGN KEY (course) REFERENCES course (name)
);

CREATE TABLE meetings (
	title VARCHAR(50),
	date_time DATETIME,
	priority INT UNSIGNED,
	notes VARCHAR(255),
	PRIMARY KEY (title, date_time),
	FOREIGN KEY (course) REFERENCES course (name)
);

INSERT INTO course VALUES
	("Databases", FALSE, TRUE, FALSE, TRUE, FALSE, '1970-01-01 12:25:00', '1970-01-01 13:40:00'),
	("Choir", TRUE, FALSE, TRUE, FALSE, TRUE, '1970-01-01 14:10:00', '1970-01-01 15:00:00'), 
	("Software Engineering", FALSE, TRUE, FALSE, TRUE, FALSE, '1970-01-01 13:50:00', '1970-01-01 15:05:00'),
	("Communications", FALSE, TRUE, FALSE, TRUE, FALSE, '1970-01-01 09:25:00','1970-01-01 10:40:00');
	
INSERT INTO assignments VALUES
	("Homework #8", '2019-11-14 12:25:00', "Databases", 10, FALSE, "professor suggested starting early"),
	("Read Chapter 3", '2019-11-13 16:10:00', "Software Engineering", 3, FALSE, "15 pages"),
	("Group Project Part 3", '2019-11-14 12:25:00', "Databases", 8, TRUE, ""),
	("Communications Essay", '2019-11-15 1:00:00', "Communications", 5, FALSE, ""),
	("Project Review", '2019-11-19 1:00:00', "Databases", 6, FALSE, "");
	
INSERT INTO meetings VALUES
	("Senior Design Meeting", '2019-11-19 10:00:00', 9, "Finish app screens before meeting"),
	("Faculty Senior Design Meeting", '2019-11-14 8:30:00', 10, "Ask about due date for project"),
	("Meet friend for coffee", '2019-11-19 1:00:00', 5, "At that Starbucks");

SELECT * FROM course;
SELECT 1;
SELECT * FROM assignments;
SELECT 1;
SELECT * FROM meetings;
	
SELECT 1;
/* The user will be able to enter/modify unique events as well as dates, times, and details */
UPDATE meetings
SET title = "Meet JENNY for coffee",
	notes = "At the Starbucks in Hemmingson"
WHERE title = "Meet friend for coffee" AND date_time = '2019-11-19 1:00:00';

SELECT * FROM meetings;

SELECT 1;
/* The user will be able to view upcoming events/deadlines */
SELECT m.title, m.date_time
FROM meetings m
UNION
SELECT a.title, a.due_date
FROM assignments a
WHERE NOT is_done
ORDER BY a.date_time DESC;

SELECT 1;
/* The user will be able to delete events and assignments */
DELETE FROM assignments
WHERE title = "Homework #8";

SELECT * FROM assignments;

SELECT 1;
/* The user will be able to view upcoming assignments by level of importance/ amount of time they will take */
SELECT *
FROM assignments a
GROUP BY a.title
ORDER BY a.priority DESC;

SELECT 1;
/* The user will be able to due dates across a span of time */
SELECT * 
FROM assignments a 
WHERE a.date_time > '2019-11-13 23:59:59'
	AND a.date_time <= '2019-11-20 23:59:59';

SELECT 1;
/* The user will able to select all assignments for a particular class and then view sub selections of that data (either by type, or by week) */
SELECT * 
FROM assignments a
WHERE a.course = 'Databases' AND a.priority > 7;
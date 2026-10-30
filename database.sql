-- Run once in MySQL Workbench. If the database already exists, do not rerun the INSERT statements unless you want duplicate sample events.
CREATE DATABASE IF NOT EXISTS event_registration_db;
USE event_registration_db;

CREATE TABLE IF NOT EXISTS events (
  event_id INT PRIMARY KEY AUTO_INCREMENT,
  event_name VARCHAR(100) NOT NULL,
  description VARCHAR(500),
  event_date DATE NOT NULL,
  venue VARCHAR(150) NOT NULL,
  image_url VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS `register` (
  registration_id INT PRIMARY KEY AUTO_INCREMENT,
  event_id INT NOT NULL,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(150) NOT NULL,
  phone VARCHAR(15) NOT NULL,
  registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_register_event FOREIGN KEY (event_id) REFERENCES events(event_id)
);

-- Sample events (run only once; check SELECT * FROM events first)
INSERT INTO events (event_name, description, event_date, venue, image_url)
SELECT 'Web Development Workshop','Learn HTML, CSS, JavaScript and Java web development.','2026-11-15','Computer Lab 1','images/web-workshop.svg'
WHERE NOT EXISTS (SELECT 1 FROM events WHERE event_name='Web Development Workshop');
INSERT INTO events (event_name, description, event_date, venue, image_url)
SELECT 'Technical Seminar','Explore modern technologies and software development trends.','2026-11-20','Seminar Hall','images/technical-seminar.svg'
WHERE NOT EXISTS (SELECT 1 FROM events WHERE event_name='Technical Seminar');
INSERT INTO events (event_name, description, event_date, venue, image_url)
SELECT 'Annual College Fest','Enjoy cultural activities, competitions and performances.','2026-11-25','College Auditorium','images/college-fest.svg'
WHERE NOT EXISTS (SELECT 1 FROM events WHERE event_name='Annual College Fest');

-- Verify data:
SELECT * FROM events;
SELECT * FROM `register`;

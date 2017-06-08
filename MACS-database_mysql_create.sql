DROP SCHEMA IF EXISTS `final_project`;
CREATE SCHEMA `final_project`; 
USE `final_project`;

DROP TABLE IF EXISTS `seminar-seminarists`;
DROP TABLE IF EXISTS `section-section_leader`;
DROP TABLE IF EXISTS `student-seminar`;
DROP TABLE IF EXISTS `student-section`;
DROP TABLE IF EXISTS `sections`;
DROP TABLE IF EXISTS `seminars_timetable`;
DROP TABLE IF EXISTS `seminars`;
DROP TABLE IF EXISTS `classroom_students`;
DROP TABLE IF EXISTS `classroom_section_leaders`;
DROP TABLE IF EXISTS `classroom_seminarists`;
DROP TABLE IF EXISTS `classroom_lecturers`;
DROP TABLE IF EXISTS `classrooms`;
DROP TABLE IF EXISTS `persons`;

CREATE TABLE `persons` (
	`person_id` INT NOT NULL AUTO_INCREMENT,
	`person_name` varchar(100) NOT NULL,
	`person_surname` varchar(100) NOT NULL,
	`person_email` varchar(100) NOT NULL,
	PRIMARY KEY (`person_id`)
);

CREATE TABLE `classrooms` (
	`classroom_id` INT NOT NULL,
	`classroom_name` varchar(100) NOT NULL,
	PRIMARY KEY (`classroom_id`)
);

CREATE TABLE `classroom_lecturers` (
	`classroom_id` INT NOT NULL,
	`person_id` INT NOT NULL,
	PRIMARY KEY (`classroom_id`,`person_id`),
	CONSTRAINT `classroom_lecturers_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms`(`classroom_id`),
	CONSTRAINT `classroom_lecturers_fk1` FOREIGN KEY (`person_id`) REFERENCES `persons`(`person_id`)
);

CREATE TABLE `classroom_seminarists` (
	`classroom_id` INT NOT NULL,
	`person_id` INT NOT NULL,
	PRIMARY KEY (`classroom_id`,`person_id`),
	CONSTRAINT `classroom_seminarists_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms`(`classroom_id`),
	CONSTRAINT `classroom_seminarists_fk1` FOREIGN KEY (`person_id`) REFERENCES `persons`(`person_id`)
);

CREATE TABLE `classroom_section_leaders` (
	`classroom_id` INT NOT NULL,
	`person_id` INT NOT NULL,
	PRIMARY KEY (`classroom_id`,`person_id`),
	CONSTRAINT `classroom_section_leaders_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms`(`classroom_id`),
	CONSTRAINT `classroom_section_leaders_fk1` FOREIGN KEY (`person_id`) REFERENCES `persons`(`person_id`)
);

CREATE TABLE `sections` (
	`section_id` INT NOT NULL AUTO_INCREMENT,
	`classroom_id` INT NOT NULL,
    	`section_name` varchar(100) NOT NULL,
	PRIMARY KEY (`section_id`, `classroom_id`),
	CONSTRAINT `sections_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms`(`classroom_id`)
);

CREATE TABLE `seminars` (
	`seminar_id` INT NOT NULL AUTO_INCREMENT,
	`classroom_id` INT NOT NULL,
    	`seminar_name` varchar(100) NOT NULL,
	PRIMARY KEY (`seminar_id`, `classroom_id`),
	CONSTRAINT `seminars_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms`(`classroom_id`)
);

CREATE TABLE `classroom_students` (
	`classroom_id` INT NOT NULL,
	`student_id` INT NOT NULL,
	PRIMARY KEY (`classroom_id`,`student_id`),
	CONSTRAINT `classroom_students_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classroom_seminarists`(`classroom_id`),
	CONSTRAINT `classroom_students_fk1` FOREIGN KEY (`student_id`) REFERENCES `persons`(`person_id`)
);

CREATE TABLE `seminars_timetable` (
	`seminar_id` INT NOT NULL,
	`seminar_name` varchar(100) NOT NULL,
	`seminar_location` varchar(100) NOT NULL,
<<<<<<< HEAD
    UNIQUE KEY `unique-key-timetable` (`seminar_id`, `seminar_name`, `seminar_location`)
=======
	CONSTRAINT `seminars_timetable_fk0` FOREIGN KEY (`seminar_id`) REFERENCES `seminars`(`seminar_id`)
>>>>>>> 16207a0a459897679f201667707c59f052fcb149
);

CREATE TABLE `student-seminar` (
	`classroom_id` INT NOT NULL,
	`student_id` INT NOT NULL,
	`seminar_id` INT NOT NULL,
	CONSTRAINT `student-seminar_fk0` FOREIGN KEY (`student_id`, `classroom_id`) REFERENCES `classroom_students`(`student_id`,`classroom_id`),
	CONSTRAINT `student-seminar_fk1` FOREIGN KEY (`seminar_id`, `classroom_id`) REFERENCES `seminars`(`seminar_id`, `classroom_id`)
);

CREATE TABLE `student-section` (
	`classroom_id` INT NOT NULL,
	`student_id` INT NOT NULL,
	`section_id` INT NOT NULL,
	CONSTRAINT `student-section_fk0` FOREIGN KEY (`student_id`, `classroom_id`) REFERENCES `classroom_students`(`student_id`, `classroom_id`),
	CONSTRAINT `student-section_fk1` FOREIGN KEY (`section_id`, `classroom_id`) REFERENCES `sections`(`section_id`, `classroom_id`)
);

CREATE TABLE `seminar-seminarists` (
	`seminar_id` INT NOT NULL,
	`classroom_id` INT NOT NULL,
	`person_id` INT NOT NULL,
	CONSTRAINT `seminar-seminarists_fk0` FOREIGN KEY (`seminar_id` , `classroom_id`) REFERENCES `seminars`(`seminar_id`, `classroom_id`),
	CONSTRAINT `seminar-seminarists_fk1` FOREIGN KEY (`classroom_id`, `person_id`) REFERENCES `classroom_seminarists`(`classroom_id` , `person_id`)
);

CREATE TABLE `section-section_leader` (
	`classroom_id` INT NOT NULL,
	`person_id` INT NOT NULL,
	`section_id` INT NOT NULL,
	CONSTRAINT `section-section_leader_fk0` FOREIGN KEY (`classroom_id`, `person_id`) REFERENCES `classroom_section_leaders`(`classroom_id`, `person_id`),
	CONSTRAINT `section-section_leader_fk1` FOREIGN KEY (`section_id`, `classroom_id`) REFERENCES `sections`(`section_id`, `classroom_id`)
);
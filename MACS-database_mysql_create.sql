DROP SCHEMA IF EXISTS `final_project`;
CREATE SCHEMA `final_project`; 
USE `final_project`;

DROP TABLE IF EXISTS `lectures`;
DROP TABLE IF EXISTS `seminar-seminarists`;
DROP TABLE IF EXISTS `section-section_leader`;
DROP TABLE IF EXISTS `student-seminar`;
DROP TABLE IF EXISTS `student-section`;
DROP TABLE IF EXISTS `sections`;
DROP TABLE IF EXISTS `seminars_timetable`;
DROP TABLE IF EXISTS `seminars`;
DROP TABLE IF EXISTS `classroom_posts`;
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
	UNIQUE (`person_email`),
	PRIMARY KEY (`person_id`)
);

CREATE TABLE `classrooms` (
	`classroom_id` INT NOT NULL AUTO_INCREMENT,
	`classroom_name` varchar(100) NOT NULL,
	PRIMARY KEY (`classroom_id`)
);

CREATE TABLE `classroom_posts` (
	`post_id` INT NOT NULL AUTO_INCREMENT,
	`classroom_id` INT NOT NULL,
	`person_id` INT NOT NULL,
	`post_text` TEXT NOT NULL,
	PRIMARY KEY (`post_id`),
	CONSTRAINT `classroom_posts_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms`(`classroom_id`),
	CONSTRAINT `classroom_posts_fk1` FOREIGN KEY (`person_id`) REFERENCES `persons`(`person_id`)
);

CREATE TABLE `post_comments` (
	`comment_id` INT NOT NULL AUTO_INCREMENT,
	`post_id` INT NOT NULL,
	`person_id` INT NOT NULL,
	`comment_text` TEXT NOT NULL,
	PRIMARY KEY (`comment_id`),
	CONSTRAINT `post_comments_fk0` FOREIGN KEY (`post_id`) REFERENCES `classroom_posts`(`post_id`),
	CONSTRAINT `post_comments_fk1` FOREIGN KEY (`person_id`) REFERENCES `persons`(`person_id`)
);

CREATE TABLE `classroom_materials` (
	`classroom_id` INT NOT NULL,
	`material_name` varchar(100) NOT NULL,
	PRIMARY KEY (`classroom_id`,`material_name`),
	CONSTRAINT `classroom_materials_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms`(`classroom_id`)
);

CREATE TABLE `classroom_lecturers` (
	`classroom_id` INT NOT NULL,
	`person_id` INT NOT NULL,
	PRIMARY KEY (`classroom_id`,`person_id`),
	CONSTRAINT `classroom_lecturers_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms`(`classroom_id`),
	CONSTRAINT `classroom_lecturers_fk1` FOREIGN KEY (`person_id`) REFERENCES `persons`(`person_id`)
);


CREATE TABLE `lectures` (
	`classroom_id` INT NOT NULL,
	`person_id` INT NOT NULL,
	`lecture_time`	varchar(100) NOT NULL,
	`lecture_location`	varchar(100) NOT NULL,
	`lecture_name`	varchar(100) NOT NULL,
	
	CONSTRAINT `lectures_fk0` FOREIGN KEY (`classroom_id`, `person_id`) REFERENCES 
					`classroom_lecturers` (`classroom_id`, `person_id`)		
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
    `section_n` INT NOT NULL,
    
	PRIMARY KEY (`section_id`, `classroom_id`),
	UNIQUE KEY `seminars_uk0` (`section_n`, `classroom_id`),
	CONSTRAINT `sections_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms`(`classroom_id`)
);

CREATE TABLE `seminars` (
	`seminar_id` INT NOT NULL AUTO_INCREMENT,
	`classroom_id` INT NOT NULL,
    `seminar_n`INT NOT NULL,
  
	PRIMARY KEY (`seminar_id`, `classroom_id`),
	UNIQUE KEY `seminars_uk0` (`seminar_n`, `classroom_id`),
	CONSTRAINT `seminars_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms`(`classroom_id`)
);

CREATE TABLE `classroom_students` (
	`classroom_id` INT NOT NULL,
	`person_id` INT NOT NULL,
	PRIMARY KEY (`classroom_id`,`person_id`),
	CONSTRAINT `classroom_students_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms`(`classroom_id`),
	CONSTRAINT `classroom_students_fk1` FOREIGN KEY (`person_id`) REFERENCES `persons`(`person_id`)
);

CREATE TABLE `student-seminar` (
	`classroom_id` INT NOT NULL,
	`person_id` INT NOT NULL,
	`seminar_id` INT NOT NULL,
	UNIQUE KEY `unique_key_student-seminar` (`person_id`, `classroom_id`),
	CONSTRAINT `student-seminar_fk0` FOREIGN KEY (`person_id`, `classroom_id`) REFERENCES `classroom_students`(`person_id`,`classroom_id`),
	CONSTRAINT `student-seminar_fk1` FOREIGN KEY (`seminar_id`, `classroom_id`) REFERENCES `seminars`(`seminar_id`, `classroom_id`)
);

CREATE TABLE `student-section` (
	`classroom_id` INT NOT NULL,
	`person_id` INT NOT NULL,
	`section_id` INT NOT NULL,
	UNIQUE KEY `unique_key_student-section` (`person_id`, `classroom_id`),
	CONSTRAINT `student-section_fk0` FOREIGN KEY (`person_id`, `classroom_id`) REFERENCES `classroom_students`(`person_id`, `classroom_id`),
	CONSTRAINT `student-section_fk1` FOREIGN KEY (`section_id`, `classroom_id`) REFERENCES `sections`(`section_id`, `classroom_id`)
);

CREATE TABLE `seminar-seminarists` (
	`seminar_id` INT NOT NULL,
	`classroom_id` INT NOT NULL,
	`person_id` INT NOT NULL,
	UNIQUE KEY `unique_key_seminar-seminarists` (`seminar_id`, `classroom_id`),
	CONSTRAINT `seminar-seminarists_fk0` FOREIGN KEY (`seminar_id` , `classroom_id`) REFERENCES `seminars`(`seminar_id`, `classroom_id`),
	CONSTRAINT `seminar-seminarists_fk1` FOREIGN KEY (`classroom_id`, `person_id`) REFERENCES `classroom_seminarists`(`classroom_id` , `person_id`)
);

CREATE TABLE `section-section_leader` (
	`classroom_id` INT NOT NULL,
	`person_id` INT NOT NULL,
	`section_id` INT NOT NULL,
	UNIQUE KEY `section-section_leader-uk0` (`person_id`, `section_id`),
	CONSTRAINT `section-section_leader_fk0` FOREIGN KEY (`classroom_id`, `person_id`) REFERENCES `classroom_section_leaders`(`classroom_id`, `person_id`),
	CONSTRAINT `section-section_leader_fk1` FOREIGN KEY (`section_id`, `classroom_id`) REFERENCES `sections`(`section_id`, `classroom_id`)
);

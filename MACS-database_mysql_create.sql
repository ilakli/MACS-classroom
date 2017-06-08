drop schema if exists `final_project`;
create schema `final_project`;
use `final_project`;

drop table if exists `seminar-seminarists`;
drop table if exists `section-section_leader`;
drop table if exists `student-seminar`;
drop table if exists `student-section`;
drop table if exists `sections`;
drop table if exists `seminars_timetable`;
drop table if exists `seminars`;
drop table if exists `classroom_students`;
drop table if exists `classroom_section_leaders`;
drop table if exists `classroom_seminarists`;
drop table if exists `classroom_lecturers`;
drop table if exists `classrooms`;
drop table if exists `persons`;





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
	PRIMARY KEY (`classroom_id`,`person_id`)
);

CREATE TABLE `classroom_seminarists` (
	`classroom_id` INT NOT NULL,
	`person_id` INT NOT NULL,
	PRIMARY KEY (`classroom_id`,`person_id`)
);

CREATE TABLE `classroom_section_leaders` (
	`classroom_id` INT NOT NULL,
	`person_id` INT NOT NULL,
	PRIMARY KEY (`classroom_id`,`person_id`)
);

CREATE TABLE `sections` (
	`section_id` INT NOT NULL AUTO_INCREMENT,
	`classroom_id` INT NOT NULL,
    `section_name` varchar(100) NOT NULL,
	PRIMARY KEY (`section_id`, `classroom_id`)
);

CREATE TABLE `seminars` (
	`seminar_id` INT NOT NULL AUTO_INCREMENT,
	`classroom_id` INT NOT NULL,
    `seminar_name` varchar(100) NOT NULL,
	PRIMARY KEY (`seminar_id`, `classroom_id`)
);

CREATE TABLE `classroom_students` (
	`classroom_id` INT NOT NULL,
	`student_id` INT NOT NULL,
	PRIMARY KEY (`classroom_id`,`student_id`)
);

CREATE TABLE `seminars_timetable` (
	`seminar_id` INT NOT NULL,
	`seminar_name` varchar(100) NOT NULL,
	`seminar_location` varchar(100) NOT NULL
);

CREATE TABLE `student-seminar` (
	`classroom_id` INT NOT NULL,
	`student_id` INT NOT NULL,
	`seminar_id` INT NOT NULL
);

CREATE TABLE `student-section` (
	`classroom_id` INT NOT NULL,
	`student_id` INT NOT NULL,
	`section_id` INT NOT NULL
);

CREATE TABLE `seminar-seminarists` (
	`seminar_id` INT NOT NULL,
	`classroom_id` INT NOT NULL,
	`person_id` INT NOT NULL
);

CREATE TABLE `section-section_leader` (
	`classroom_id` INT NOT NULL,
	`person_id` INT NOT NULL,
	`section_id` INT NOT NULL
);

ALTER TABLE `classroom_lecturers` ADD CONSTRAINT `classroom_lecturers_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms`(`classroom_id`);

ALTER TABLE `classroom_lecturers` ADD CONSTRAINT `classroom_lecturers_fk1` FOREIGN KEY (`person_id`) REFERENCES `persons`(`person_id`);

ALTER TABLE `classroom_seminarists` ADD CONSTRAINT `classroom_seminarists_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms`(`classroom_id`);

ALTER TABLE `classroom_seminarists` ADD CONSTRAINT `classroom_seminarists_fk1` FOREIGN KEY (`person_id`) REFERENCES `persons`(`person_id`);

ALTER TABLE `classroom_section_leaders` ADD CONSTRAINT `classroom_section_leaders_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms`(`classroom_id`);

ALTER TABLE `classroom_section_leaders` ADD CONSTRAINT `classroom_section_leaders_fk1` FOREIGN KEY (`person_id`) REFERENCES `persons`(`person_id`);

ALTER TABLE `sections` ADD CONSTRAINT `sections_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms`(`classroom_id`);

ALTER TABLE `seminars` ADD CONSTRAINT `seminars_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms`(`classroom_id`);

ALTER TABLE `classroom_students` ADD CONSTRAINT `classroom_students_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classroom_seminarists`(`classroom_id`);

ALTER TABLE `classroom_students` ADD CONSTRAINT `classroom_students_fk1` FOREIGN KEY (`student_id`) REFERENCES `persons`(`person_id`);

ALTER TABLE `seminars_timetable` ADD CONSTRAINT `seminars_timetable_fk0` FOREIGN KEY (`seminar_id`) REFERENCES `seminars`(`seminar_id`);



ALTER TABLE `student-seminar` ADD CONSTRAINT `student-seminar_fk1` FOREIGN KEY (`student_id`, `classroom_id`) REFERENCES `classroom_students`(`student_id`,`classroom_id`);

ALTER TABLE `student-seminar` ADD CONSTRAINT `student-seminar_fk2` FOREIGN KEY (`seminar_id`, `classroom_id`) REFERENCES `seminars`(`seminar_id`, `classroom_id`);



ALTER TABLE `student-section` ADD CONSTRAINT `student-section_fk1` FOREIGN KEY (`student_id`, `classroom_id`) REFERENCES `classroom_students`(`student_id`, `classroom_id`);

ALTER TABLE `student-section` ADD CONSTRAINT `student-section_fk2` FOREIGN KEY (`section_id`, `classroom_id`) REFERENCES `sections`(`section_id`, `classroom_id`);



ALTER TABLE `seminar-seminarists` ADD CONSTRAINT `seminar-seminarists_fk0` FOREIGN KEY (`seminar_id` , `classroom_id`) REFERENCES `seminars`(`seminar_id`, `classroom_id`);

ALTER TABLE `seminar-seminarists` ADD CONSTRAINT `seminar-seminarists_fk1` FOREIGN KEY (`classroom_id`, `person_id`) REFERENCES `classroom_seminarists`(`classroom_id` , `person_id`);




ALTER TABLE `section-section_leader` ADD CONSTRAINT `section-section_leader_fk0` FOREIGN KEY (`classroom_id`, `person_id`) REFERENCES `classroom_section_leaders`(`classroom_id`, `person_id`);


ALTER TABLE `section-section_leader` ADD CONSTRAINT `section-section_leader_fk2` FOREIGN KEY (`section_id`, `classroom_id`) REFERENCES `sections`(`section_id`, `classroom_id`);


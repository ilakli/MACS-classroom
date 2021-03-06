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
DROP TABLE IF EXISTS `classroom_materials`;
DROP TABLE IF EXISTS `classroom_assignments`;
DROP TABLE IF EXISTS `classrooms`;
DROP TABLE IF EXISTS `lecturers`;
DROP TABLE IF EXISTS `persons`;
DROP TABLE IF EXISTS `positions`;
DROP TABLE IF EXISTS `functions`;
DROP TABLE IF EXISTS `admins`;
DROP TABLE IF EXISTS `position_function`;
DROP TABLE IF EXISTS `classroom-classroom_folder`;
DROP TABLE IF EXISTS `seminarist-seminarist_folder`;
 
CREATE TABLE `persons` (
    `person_id` INT NOT NULL AUTO_INCREMENT,
    `person_name` varchar(100) DEFAULT "Name",
    `person_surname` varchar(100) DEFAULT "Surname",
    `person_email` varchar(100) NOT NULL,
    `image_url` varchar(200),
    UNIQUE (`person_email`),
    PRIMARY KEY (`person_id`)
);
 
CREATE TABLE `admins` (
    `person_id` INT NOT NULL,
    UNIQUE (`person_id`),
    CONSTRAINT `admins_fk0` FOREIGN KEY (`person_id`) REFERENCES `persons`(`person_id`) ON DELETE CASCADE
);
 
CREATE TABLE `lecturers` (
    `person_id` INT NOT NULL,
    UNIQUE (`person_id`),
    CONSTRAINT `lecturers_fk0` FOREIGN KEY (`person_id`) REFERENCES `persons`(`person_id`)  ON DELETE CASCADE
);
 
CREATE TABLE `classrooms` (
    `classroom_id` INT NOT NULL AUTO_INCREMENT,
    `classroom_name` varchar(100) NOT NULL,
    `classroom_creator_id` int NOT NULL,
    `classroom_reschedulings_num` INT NOT NULL DEFAULT 0,
    `classroom_reschedulings_length` INT NOT NULL DEFAULT 0,
    `classroom_seminar_auto_distribution` BOOL NOT NULL DEFAULT false,
    `classroom_section_auto_distribution` BOOL NOT NULL DEFAULT false,
    `classroom_is_finished` BOOL NOT NULL DEFAULT FALSE,
    CONSTRAINT `classrooms_fk0` FOREIGN KEY (`classroom_creator_id`) REFERENCES `persons`(`person_id`)  ON DELETE CASCADE,
    PRIMARY KEY (`classroom_id`)
);
 
CREATE TABLE `classroom_posts` (
    `post_id` INT NOT NULL AUTO_INCREMENT,
    `classroom_id` INT NOT NULL,
    `person_id` INT NOT NULL,
    `post_text` TEXT NOT NULL,
    `post_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`post_id`),
    CONSTRAINT `classroom_posts_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms`(`classroom_id`) ON DELETE CASCADE,
    CONSTRAINT `classroom_posts_fk1` FOREIGN KEY (`person_id`) REFERENCES `persons`(`person_id`) ON DELETE CASCADE
);
 
CREATE TABLE `post_comments` (
    `comment_id` INT NOT NULL AUTO_INCREMENT,
    `post_id` INT NOT NULL,
    `person_id` INT NOT NULL,
    `comment_text` TEXT NOT NULL,
    `comment_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`comment_id`),
    CONSTRAINT `post_comments_fk0` FOREIGN KEY (`post_id`) REFERENCES `classroom_posts`(`post_id`) ON DELETE CASCADE,
    CONSTRAINT `post_comments_fk1` FOREIGN KEY (`person_id`) REFERENCES `persons`(`person_id`) ON DELETE CASCADE
);
 
CREATE TABLE `classroom_assignments` (
    `assignment_id` INT NOT NULL AUTO_INCREMENT, 
	 `classroom_id` INT NOT NULL,
    `assignment_title` varchar(100) NOT NULL,
    `assignment_instructions` TEXT NOT NULL,
    `assignment_deadline` date,
    `file_name` varchar(100),
   
    PRIMARY KEY (`assignment_id`),
    UNIQUE (`classroom_id`,`assignment_title`),
    CONSTRAINT `classroom_assignments_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms`(`classroom_id`) ON DELETE CASCADE
);
 
CREATE TABLE `classroom_material_category` (
    `category_id` INT NOT NULL AUTO_INCREMENT,
    `classroom_id` INT NOT NULL,
    `category_name` varchar(100) NOT NULL,
    PRIMARY KEY (`category_id`),
    UNIQUE(`classroom_id`, `category_name`),
    CONSTRAINT `classroom_material_category_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms`(`classroom_id`) ON DELETE CASCADE
);
 
CREATE TABLE `classroom_materials` (
    `classroom_id` INT NOT NULL,
    `category_id` INT NOT NULL,
    `material_name` varchar(100) NOT NULL, 
    PRIMARY KEY (`material_name`,`category_id`),
    CONSTRAINT `classroom_materials_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms`(`classroom_id`) ON DELETE CASCADE,
    CONSTRAINT `classroom_materials_fk1` FOREIGN KEY (`category_id`) REFERENCES `classroom_material_category`(`category_id`) ON DELETE CASCADE
);
 
CREATE TABLE `classroom_lecturers` (
    `classroom_id` INT NOT NULL,
    `person_id` INT NOT NULL,
    PRIMARY KEY (`classroom_id`,`person_id`),
    CONSTRAINT `classroom_lecturers_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms`(`classroom_id`) ON DELETE CASCADE,
    CONSTRAINT `classroom_lecturers_fk1` FOREIGN KEY (`person_id`) REFERENCES `lecturers`(`person_id`) ON DELETE CASCADE
);
 
 
CREATE TABLE `lectures` (
    `classroom_id` INT NOT NULL,
    `person_id` INT NOT NULL,
    `lecture_time`  varchar(100) NOT NULL,
    `lecture_location`  varchar(100) NOT NULL,
    `lecture_name`  varchar(100) NOT NULL,
   
    CONSTRAINT `lectures_fk0` FOREIGN KEY (`classroom_id`, `person_id`) REFERENCES
                    `classroom_lecturers` (`classroom_id`, `person_id`)  ON DELETE CASCADE   
);
 
CREATE TABLE `classroom_seminarists` (
    `classroom_id` INT NOT NULL,
    `person_id` INT NOT NULL,
    PRIMARY KEY (`classroom_id`,`person_id`),
    CONSTRAINT `classroom_seminarists_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms`(`classroom_id`) ON DELETE CASCADE,
    CONSTRAINT `classroom_seminarists_fk1` FOREIGN KEY (`person_id`) REFERENCES `persons`(`person_id`) ON DELETE CASCADE
);
 
CREATE TABLE `classroom_section_leaders` (
    `classroom_id` INT NOT NULL,
    `person_id` INT NOT NULL,
    PRIMARY KEY (`classroom_id`,`person_id`),
    CONSTRAINT `classroom_section_leaders_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms`(`classroom_id`) ON DELETE CASCADE,
    CONSTRAINT `classroom_section_leaders_fk1` FOREIGN KEY (`person_id`) REFERENCES `persons`(`person_id`) ON DELETE CASCADE
);
 
CREATE TABLE `sections` (
    `section_id` INT NOT NULL AUTO_INCREMENT,
    `classroom_id` INT NOT NULL,
   `section_n` INT NOT NULL,
   `section_size` INT NOT NULL DEFAULT 0,
 
    PRIMARY KEY (`section_id`, `classroom_id`),
    UNIQUE KEY `seminars_uk0` (`section_n`, `classroom_id`),
    CONSTRAINT `sections_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms`(`classroom_id`) ON DELETE CASCADE
);
 
CREATE TABLE `seminars` (
    `seminar_id` INT NOT NULL AUTO_INCREMENT,
    `classroom_id` INT NOT NULL,
   `seminar_n` INT NOT NULL,
   `seminar_size` INT NOT NULL DEFAULT 0,
 
    PRIMARY KEY (`seminar_id`, `classroom_id`),
    UNIQUE KEY `seminars_uk0` (`seminar_n`, `classroom_id`),
    CONSTRAINT `seminars_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms`(`classroom_id`) ON DELETE CASCADE
);
 
CREATE TABLE `classroom_students` (
    `classroom_id` INT NOT NULL,
    `person_id` INT NOT NULL,
    `reschedulings_used` INT NOT NULL DEFAULT 0,
    PRIMARY KEY (`classroom_id`,`person_id`),
    CONSTRAINT `classroom_students_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms`(`classroom_id`) ON DELETE CASCADE,
    CONSTRAINT `classroom_students_fk1` FOREIGN KEY (`person_id`) REFERENCES `persons`(`person_id`) ON DELETE CASCADE
);
 
CREATE TABLE `student-seminar` (
    `classroom_id` INT NOT NULL,
    `person_id` INT NOT NULL,
    `seminar_id` INT NOT NULL,
    UNIQUE KEY `unique_key_student-seminar` (`person_id`, `classroom_id`),
    CONSTRAINT `student-seminar_fk0` FOREIGN KEY (`person_id`, `classroom_id`) REFERENCES `classroom_students`(`person_id`,`classroom_id`) ON DELETE CASCADE,
    CONSTRAINT `student-seminar_fk1` FOREIGN KEY (`seminar_id`, `classroom_id`) REFERENCES `seminars`(`seminar_id`, `classroom_id`) ON DELETE CASCADE
);
 
CREATE TABLE `student-section` (
    `classroom_id` INT NOT NULL,
    `person_id` INT NOT NULL,
    `section_id` INT NOT NULL,
    UNIQUE KEY `unique_key_student-section` (`person_id`, `classroom_id`),
    CONSTRAINT `student-section_fk0` FOREIGN KEY (`person_id`, `classroom_id`) REFERENCES `classroom_students`(`person_id`, `classroom_id`) ON DELETE CASCADE,
    CONSTRAINT `student-section_fk1` FOREIGN KEY (`section_id`, `classroom_id`) REFERENCES `sections`(`section_id`, `classroom_id`) ON DELETE CASCADE
);
 
CREATE TABLE `seminar-seminarists` (
    `seminar_id` INT NOT NULL,
    `classroom_id` INT NOT NULL,
    `person_id` INT NOT NULL,
    UNIQUE KEY `seminar-seminarists_uk0` (`seminar_id`),
    UNIQUE KEY `seminar-seminarists_uk1` (`person_id`, `classroom_id`),
    CONSTRAINT `seminar-seminarists_fk0` FOREIGN KEY (`seminar_id` , `classroom_id`) REFERENCES `seminars`(`seminar_id`, `classroom_id`) ON DELETE CASCADE,
    CONSTRAINT `seminar-seminarists_fk1` FOREIGN KEY (`classroom_id`, `person_id`) REFERENCES `classroom_seminarists`(`classroom_id` , `person_id`) ON DELETE CASCADE
);

CREATE TABLE `assignment_grades` (
	`grade_name` VARCHAR(50) NOT NULL,
	`grade_value` INT NOT NULL,
	PRIMARY KEY (`grade_name`)	
);

CREATE TABLE `student_assignments` (
	`student_assignment_id` int NOT NULL AUTO_INCREMENT,
	`classroom_id` INT NOT NULL,
	`person_id` INT NOT NULL,
	`assignment_id` int NOT NULL,	
	`assignment_grade` VARCHAR(50) default 'Not Graded',
	`assignment_approved` BOOL DEFAULT false,
	`deadline_with_reschedulings` date,

	PRIMARY KEY (`student_assignment_id`),
	UNIQUE KEY `student_assignments_uk0` (`classroom_id`, `person_id`, `assignment_id`),
	CONSTRAINT `FK__classroom_students` FOREIGN KEY (`person_id`) REFERENCES `classroom_students` (`person_id`) ON DELETE CASCADE,
	CONSTRAINT `FK__assignment_grades` FOREIGN KEY (`assignment_grade`) REFERENCES `assignment_grades` (`grade_name`) ON DELETE CASCADE,
	CONSTRAINT `FK__classroom_assignments` FOREIGN KEY (`assignment_id`) REFERENCES `classroom_assignments` (`assignment_id`) ON DELETE CASCADE
);

CREATE TABLE `student_uploaded_assignments` (
	`student_assignment_id` INT NOT NULL,
	`file_name` VARCHAR(100) NOT NULL,
	`upload_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
	UNIQUE KEY `student_assignments_uk0` (`student_assignment_id`, `file_name`),
	CONSTRAINT `FK__student_assignments` FOREIGN KEY (`student_assignment_id`) REFERENCES `student_assignments` (`student_assignment_id`) ON DELETE CASCADE
);
 
CREATE TABLE `assignment_comment` (
    `student_assignment_id` INT NOT NULL,
    `person_id` INT NOT NULL,
    `comment_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `comment_text` TEXT NOT NULL,
    CONSTRAINT `FK__student_assignments1` FOREIGN KEY (`student_assignment_id`) REFERENCES `student_assignments` (`student_assignment_id`) ON DELETE CASCADE,
    CONSTRAINT `FK__persons0` FOREIGN KEY (`person_id`) REFERENCES `persons` (`person_id`) ON DELETE CASCADE
);

CREATE TABLE `assignment_staff_comment` (
    `student_assignment_id` INT NOT NULL,
    `person_id` INT NOT NULL,
    `comment_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `comment_text` TEXT NOT NULL,
    CONSTRAINT `FK__student_assignments2` FOREIGN KEY (`student_assignment_id`) REFERENCES `student_assignments` (`student_assignment_id`) ON DELETE CASCADE,
    CONSTRAINT `FK__persons1` FOREIGN KEY (`person_id`) REFERENCES `persons` (`person_id`) ON DELETE CASCADE
);
 
CREATE TABLE `section-section_leader` (
    `classroom_id` INT NOT NULL,
    `person_id` INT NOT NULL,
    `section_id` INT NOT NULL,
    UNIQUE KEY `section-section_leader-uk0` (`person_id`, `classroom_id`),
    UNIQUE KEY `section-section_leader-uk1` (`section_id`),
    CONSTRAINT `section-section_leader_fk0` FOREIGN KEY (`classroom_id`, `person_id`) REFERENCES `classroom_section_leaders`(`classroom_id`, `person_id`) ON DELETE CASCADE,
    CONSTRAINT `section-section_leader_fk1` FOREIGN KEY (`section_id`, `classroom_id`) REFERENCES `sections`(`section_id`, `classroom_id`) ON DELETE CASCADE
);
 
CREATE TABLE `functions` (
    `function_id` INT(11) NOT NULL AUTO_INCREMENT,
    `function_name` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`function_id`)
);
 
CREATE TABLE `positions` (
    `position_id` INT NOT NULL AUTO_INCREMENT,
    `position_name` VARCHAR(50) NULL,
    PRIMARY KEY (`position_id`)
);
 
CREATE TABLE `classroom_position_function` (
    `classroom_id` INT NOT NULL,
    `position_id` INT NOT NULL,
    `function_id` INT NOT NULL,
    UNIQUE KEY `unique0` (`classroom_id`, `position_id`, `function_id`),
    CONSTRAINT `FK__classrooms` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms` (`classroom_id`) ON DELETE CASCADE,
    CONSTRAINT `FK__positions` FOREIGN KEY (`position_id`) REFERENCES `positions` (`position_id`) ON DELETE CASCADE,
    CONSTRAINT `FK__functions` FOREIGN KEY (`function_id`) REFERENCES `functions` (`function_id`) ON DELETE CASCADE
);
 
 
CREATE TABLE `classroom-classroom_folder` (
	`classroom_id` INT NOT NULL,
	`folder_id` VARCHAR (100) NOT NULL,
	
	PRIMARY KEY (`classroom_id`),
	CONSTRAINT `classroom-classroom_folder_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms` (`classroom_id`) ON DELETE CASCADE
);

CREATE TABLE `seminarist-seminarist_folder` (
	`classroom_id` INT NOT NULL,
	`seminarist_id` INT NOT NULL,
	`folder_id` VARCHAR(100) NOT NULL,
	
	PRIMARY KEY (`classroom_id`, `seminarist_id`),
	CONSTRAINT `seminarist-seminarist_folder_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms` (`classroom_id`) ON DELETE CASCADE,
	CONSTRAINT `seminarist-seminarist_folder_fk1` FOREIGN KEY (`classroom_id`, `seminarist_id`) REFERENCES `classroom_seminarists` (`classroom_id`, `person_id`) ON DELETE CASCADE
);

CREATE TABLE `section_leader-section_leader_folder` (
	`classroom_id` INT NOT NULL,
	`section_leader_id` INT NOT NULL,
	`folder_id` VARCHAR (100) NOT NULL,
	
	PRIMARY KEY (`classroom_id`, `section_leader_id`),
	CONSTRAINT `section_leader-section_leader_folder_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms` (`classroom_id`) ON DELETE CASCADE,
	CONSTRAINT `section_leader-section_leader_folder_fk1` FOREIGN KEY (`classroom_id`, `section_leader_id`) REFERENCES `classroom_section_leaders` (`classroom_id`, `person_id`) ON DELETE CASCADE
);

CREATE TABLE `category-category_folder` (
	`classroom_id` INT NOT NULL,
	`category_name` VARCHAR (100) NOT NULL,
	`category_folder` VARCHAR (100) NOT NULL,
	
	PRIMARY KEY (`classroom_id`, `category_name`),
	CONSTRAINT `category-category_folder_fk0` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms` (`classroom_id`) ON DELETE CASCADE,
	CONSTRAINT `category-category_folder_fk1` FOREIGN KEY (`classroom_id`, `category_name`) REFERENCES `classroom_material_category`(`classroom_id`, `category_name`) ON DELETE CASCADE
);

insert into `positions` (`position_name`) values
    ('lecturer'),
    ('seminarist'),
    ('section-leader'),
    ('student');
 
insert into `functions` (`function_name`) values
    ('add posts'),
    ('distribute students to seminars'),
    ('distribute students to sections'),
    ('add new assignments'),
    ('review assignments of students from their group'),
    ('add classroom materials');
    
insert into `assignment_grades` (`grade_name`, `grade_value`) values
	 ('Not Graded', 0),
	 ('Zero', 0), 	
	 ('Minus Minus', 20),
	 ('Minus', 40),
	 ('Check Minus', 70),
	 ('Check', 85),
	 ('Check Plus', 100),
	 ('Plus', 120),
	 ('Plus Plus', 140); 
 
insert into `persons` (`person_name`, `person_surname`, `person_email`) values
    ('admin', 'admin', 'admin@admin.admin');
 
insert into admins values(
    (select `person_id` from persons
        where person_email = 'admin@admin.admin')
);
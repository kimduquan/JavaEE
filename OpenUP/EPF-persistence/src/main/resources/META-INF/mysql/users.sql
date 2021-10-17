/*Basic Roles BEGIN*/
CREATE USER IF NOT EXISTS 'analyst1'@'localhost' IDENTIFIED BY 'analyst';
CREATE USER IF NOT EXISTS 'any_role1'@'localhost' IDENTIFIED BY 'any_role';
CREATE USER IF NOT EXISTS 'architect1'@'localhost' IDENTIFIED BY 'architect';
CREATE USER IF NOT EXISTS 'developer1'@'localhost' IDENTIFIED BY 'developer';
CREATE USER IF NOT EXISTS 'project_manager1'@'localhost' IDENTIFIED BY 'project_manager';
CREATE USER IF NOT EXISTS 'stakeholder1'@'localhost' IDENTIFIED BY 'stakeholder';
CREATE USER IF NOT EXISTS 'tester1'@'localhost' IDENTIFIED BY 'tester';
/*Basic Roles END*/

/*Deployment BEGIN*/
CREATE USER IF NOT EXISTS 'course_developer1'@'localhost' IDENTIFIED BY 'course_developer';
CREATE USER IF NOT EXISTS 'deployment_engineer1'@'localhost' IDENTIFIED BY 'deployment_engineer';
CREATE USER IF NOT EXISTS 'deployment_manager1'@'localhost' IDENTIFIED BY 'deployment_manager';
CREATE USER IF NOT EXISTS 'product_owner1'@'localhost' IDENTIFIED BY 'product_owner';
CREATE USER IF NOT EXISTS 'technical_writer1'@'localhost' IDENTIFIED BY 'technical_writer';
CREATE USER IF NOT EXISTS 'trainer1'@'localhost' IDENTIFIED BY 'trainer';
/*Deployment END*/

/*Environment BEGIN*/
CREATE USER IF NOT EXISTS 'process_engineer1'@'localhost' IDENTIFIED BY 'process_engineer';
CREATE USER IF NOT EXISTS 'tool_specialist1'@'localhost' IDENTIFIED BY 'tool_specialist';
/*Environment END*/
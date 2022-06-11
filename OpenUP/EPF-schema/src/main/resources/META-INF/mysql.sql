CREATE USER IF NOT EXISTS 'epf'@'localhost' IDENTIFIED BY 'Password1234****';
GRANT ALL ON *.* TO 'epf'@'localhost' WITH GRANT OPTION;
CREATE SCHEMA IF NOT EXISTS EPF_Delivery_Processes;
CREATE SCHEMA IF NOT EXISTS EPF_Roles;
CREATE SCHEMA IF NOT EXISTS EPF_Tasks;
CREATE SCHEMA IF NOT EXISTS EPF_Work_Products;
CREATE SCHEMA IF NOT EXISTS OpenUP;
CREATE SCHEMA IF NOT EXISTS EPF_Net;
CREATE SCHEMA IF NOT EXISTS EPF_Security;
CREATE SCHEMA IF NOT EXISTS EPF_Transaction;
/*Basic Roles BEGIN*/
CREATE ROLE IF NOT EXISTS 'Analyst'@'localhost';
CREATE ROLE IF NOT EXISTS 'Any_Role'@'localhost';
CREATE ROLE IF NOT EXISTS 'Architect'@'localhost';
CREATE ROLE IF NOT EXISTS 'Developer'@'localhost';
CREATE ROLE IF NOT EXISTS 'Project_Manager'@'localhost';
CREATE ROLE IF NOT EXISTS 'Stakeholder'@'localhost';
CREATE ROLE IF NOT EXISTS 'Tester'@'localhost';
/*Basic Roles END*/

/*Deployment BEGIN*/
CREATE ROLE IF NOT EXISTS 'Course_Developer'@'localhost';
CREATE ROLE IF NOT EXISTS 'Deployment_Engineer'@'localhost';
CREATE ROLE IF NOT EXISTS 'Deployment_Manager'@'localhost';
CREATE ROLE IF NOT EXISTS 'Product_Owner'@'localhost';
CREATE ROLE IF NOT EXISTS 'Technical_Writer'@'localhost';
CREATE ROLE IF NOT EXISTS 'Trainer'@'localhost';
/*Deployment END*/

/*Environment BEGIN*/
CREATE ROLE IF NOT EXISTS 'Process_Engineer'@'localhost';
CREATE ROLE IF NOT EXISTS 'Tool_Specialist'@'localhost';
/*Environment END*/
/*Basic Roles BEGIN*/
GRANT 'Any_Role'@'localhost' TO 'Analyst'@'localhost';
GRANT 'Any_Role'@'localhost' TO 'Architect'@'localhost';
GRANT 'Any_Role'@'localhost' TO 'Developer'@'localhost';
GRANT 'Any_Role'@'localhost' TO 'Project_Manager'@'localhost';
GRANT 'Any_Role'@'localhost' TO 'Stakeholder'@'localhost';
GRANT 'Any_Role'@'localhost' TO 'Tester'@'localhost';
/*Basic Roles END*/

/*Deployment BEGIN*/
GRANT 'Any_Role'@'localhost' TO 'Course_Developer'@'localhost';
GRANT 'Any_Role'@'localhost' TO 'Deployment_Engineer'@'localhost';
GRANT 'Any_Role'@'localhost' TO 'Deployment_Manager'@'localhost';
GRANT 'Any_Role'@'localhost' TO 'Product_Owner'@'localhost';
GRANT 'Any_Role'@'localhost' TO 'Technical_Writer'@'localhost';
GRANT 'Any_Role'@'localhost' TO 'Trainer'@'localhost';
/*Deployment END*/

/*Environment BEGIN*/
GRANT 'Any_Role'@'localhost' TO 'Process_Engineer'@'localhost';
GRANT 'Any_Role'@'localhost' TO 'Tool_Specialist'@'localhost';
/*Environment END*/

/*Basic Roles BEGIN*/
SET DEFAULT ROLE 'Any_Role'@'localhost' TO 'Analyst'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost' TO 'Architect'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost' TO 'Developer'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost' TO 'Project_Manager'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost' TO 'Stakeholder'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost' TO 'Tester'@'localhost';
/*Basic Roles END*/

/*Deployment BEGIN*/
SET DEFAULT ROLE 'Any_Role'@'localhost' TO 'Course_Developer'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost' TO 'Deployment_Engineer'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost' TO 'Deployment_Manager'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost' TO 'Product_Owner'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost' TO 'Technical_Writer'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost' TO 'Trainer'@'localhost';
/*Deployment END*/

/*Environment BEGIN*/
SET DEFAULT ROLE 'Any_Role'@'localhost' TO 'Process_Engineer'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost' TO 'Tool_Specialist'@'localhost';
/*Environment END*/
/*Basic Roles BEGIN*/
CREATE USER IF NOT EXISTS 'analyst1'@'localhost' IDENTIFIED BY 'Analyst1*';
CREATE USER IF NOT EXISTS 'any_role1'@'localhost' IDENTIFIED BY 'Any_Role1*';
CREATE USER IF NOT EXISTS 'architect1'@'localhost' IDENTIFIED BY 'Architect1*';
CREATE USER IF NOT EXISTS 'developer1'@'localhost' IDENTIFIED BY 'Developer1*';
CREATE USER IF NOT EXISTS 'project_manager1'@'localhost' IDENTIFIED BY 'Project_Manager1*';
CREATE USER IF NOT EXISTS 'stakeholder1'@'localhost' IDENTIFIED BY 'Stakeholder1*';
CREATE USER IF NOT EXISTS 'tester1'@'localhost' IDENTIFIED BY 'Tester1*';
/*Basic Roles END*/

/*Deployment BEGIN*/
CREATE USER IF NOT EXISTS 'course_developer1'@'localhost' IDENTIFIED BY 'Course_Developer1*';
CREATE USER IF NOT EXISTS 'deployment_engineer1'@'localhost' IDENTIFIED BY 'Deployment_Engineer1*';
CREATE USER IF NOT EXISTS 'deployment_manager1'@'localhost' IDENTIFIED BY 'Deployment_Manager1*';
CREATE USER IF NOT EXISTS 'product_owner1'@'localhost' IDENTIFIED BY 'Product_Owner1*';
CREATE USER IF NOT EXISTS 'technical_writer1'@'localhost' IDENTIFIED BY 'Technical_Writer1*';
CREATE USER IF NOT EXISTS 'trainer1'@'localhost' IDENTIFIED BY 'Trainer1*';
/*Deployment END*/

/*Environment BEGIN*/
CREATE USER IF NOT EXISTS 'process_engineer1'@'localhost' IDENTIFIED BY 'Process_Engineer1*';
CREATE USER IF NOT EXISTS 'tool_specialist1'@'localhost' IDENTIFIED BY 'Tool_Specialist1*';
/*Environment END*/
GRANT 'Any_Role'@'localhost' TO 'epf'@'localhost';

/*Basic Roles BEGIN*/
GRANT 'Any_Role'@'localhost', 'Analyst'@'localhost' TO 'analyst1'@'localhost';
GRANT 'Any_Role'@'localhost', 'Any_Role'@'localhost' TO 'any_role1'@'localhost';
GRANT 'Any_Role'@'localhost', 'Architect'@'localhost' TO 'architect1'@'localhost';
GRANT 'Any_Role'@'localhost', 'Developer'@'localhost' TO 'developer1'@'localhost';
GRANT 'Any_Role'@'localhost', 'Project_Manager'@'localhost' TO 'project_manager1'@'localhost';
GRANT 'Any_Role'@'localhost', 'Stakeholder'@'localhost' TO 'stakeholder1'@'localhost';
GRANT 'Any_Role'@'localhost', 'Tester'@'localhost' TO 'tester1'@'localhost';
/*Basic Roles END*/

/*Deployment BEGIN*/
GRANT 'Any_Role'@'localhost', 'Course_Developer'@'localhost' TO 'course_developer1'@'localhost';
GRANT 'Any_Role'@'localhost', 'Deployment_Engineer'@'localhost' TO 'deployment_engineer1'@'localhost';
GRANT 'Any_Role'@'localhost', 'Deployment_Manager'@'localhost' TO 'deployment_manager1'@'localhost';
GRANT 'Any_Role'@'localhost', 'Product_Owner'@'localhost' TO 'product_owner1'@'localhost';
GRANT 'Any_Role'@'localhost', 'Technical_Writer'@'localhost' TO 'technical_writer1'@'localhost';
GRANT 'Any_Role'@'localhost', 'Trainer'@'localhost' TO 'trainer1'@'localhost';
/*Deployment END*/

/*Environment BEGIN*/
GRANT 'Any_Role'@'localhost', 'Process_Engineer'@'localhost' TO 'process_engineer1'@'localhost';
GRANT 'Any_Role'@'localhost', 'Tool_Specialist'@'localhost' TO 'tool_specialist1'@'localhost';
/*Environment END*/

/*Basic Roles BEGIN*/
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Analyst'@'localhost' TO 'analyst1'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Any_Role'@'localhost' TO 'any_role1'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Architect'@'localhost' TO 'architect1'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Developer'@'localhost' TO 'developer1'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Project_Manager'@'localhost' TO 'project_manager1'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Stakeholder'@'localhost' TO 'stakeholder1'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Tester'@'localhost' TO 'tester1'@'localhost';
/*Basic Roles END*/

/*Deployment BEGIN*/
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Course_Developer'@'localhost' TO 'course_developer1'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Deployment_Engineer'@'localhost' TO 'deployment_engineer1'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Deployment_Manager'@'localhost' TO 'deployment_manager1'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Product_Owner'@'localhost' TO 'product_owner1'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Technical_Writer'@'localhost' TO 'technical_writer1'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Trainer'@'localhost' TO 'trainer1'@'localhost';
/*Deployment END*/

/*Environment BEGIN*/
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Process_Engineer'@'localhost' TO 'process_engineer1'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Tool_Specialist'@'localhost' TO 'tool_specialist1'@'localhost';
/*Environment END*/

SET DEFAULT ROLE 'Any_Role'@'localhost' TO 'epf'@'localhost';
/*Basic Roles BEGIN*/
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'Analyst'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'Any_Role'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'Architect'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'Developer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'Project_Manager'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'Stakeholder'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'Tester'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'Analyst'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'Any_Role'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'Architect'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'Developer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'Project_Manager'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'Stakeholder'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'Tester'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'Analyst'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'Any_Role'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'Architect'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'Developer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'Project_Manager'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'Stakeholder'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'Tester'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'Analyst'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'Any_Role'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'Architect'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'Developer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'Project_Manager'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'Stakeholder'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'Tester'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'Analyst'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'Any_Role'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'Architect'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'Developer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'Project_Manager'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'Stakeholder'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'Tester'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Analyst'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Any_Role'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Architect'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Developer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Project_Manager'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Stakeholder'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Tester'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'Analyst'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'Any_Role'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'Architect'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'Developer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'Project_Manager'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'Stakeholder'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'Tester'@'localhost';
/*Basic Roles END*/

/*Deployment BEGIN*/
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'Course_Developer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'Deployment_Engineer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'Deployment_Manager'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'Product_Owner'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'Technical_Writer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'Trainer'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'Course_Developer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'Deployment_Engineer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'Deployment_Manager'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'Product_Owner'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'Technical_Writer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'Trainer'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'Course_Developer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'Deployment_Engineer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'Deployment_Manager'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'Product_Owner'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'Technical_Writer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'Trainer'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'Course_Developer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'Deployment_Engineer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'Deployment_Manager'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'Product_Owner'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'Technical_Writer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'Trainer'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'Course_Developer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'Deployment_Engineer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'Deployment_Manager'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'Product_Owner'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'Technical_Writer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'Trainer'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Course_Developer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Deployment_Engineer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Deployment_Manager'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Product_Owner'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Technical_Writer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Trainer'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'Course_Developer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'Deployment_Engineer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'Deployment_Manager'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'Product_Owner'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'Technical_Writer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'Trainer'@'localhost';
/*Deployment END*/

/*Environment BEGIN*/
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'Process_Engineer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'Tool_Specialist'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'Process_Engineer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'Tool_Specialist'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'Process_Engineer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'Tool_Specialist'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'Process_Engineer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'Tool_Specialist'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'Process_Engineer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'Tool_Specialist'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Process_Engineer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Tool_Specialist'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'Process_Engineer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'Tool_Specialist'@'localhost';
/*Environment END*/
GRANT ALL ON *.* TO 'epf'@'localhost' WITH GRANT OPTION;

/*Basic Roles BEGIN*/
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'analyst1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'any_role1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'architect1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'developer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'project_manager1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'stakeholder1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'tester1'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'analyst1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'any_role1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'architect1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'developer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'project_manager1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'stakeholder1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'tester1'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'analyst1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'any_role1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'architect1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'developer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'project_manager1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'stakeholder1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'tester1'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'analyst1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'any_role1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'architect1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'developer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'project_manager1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'stakeholder1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'tester1'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'analyst1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'any_role1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'architect1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'developer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'project_manager1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'stakeholder1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'tester1'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'analyst1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'any_role1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'architect1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'developer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'project_manager1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'stakeholder1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'tester1'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'analyst1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'any_role1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'architect1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'developer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'project_manager1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'stakeholder1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'tester1'@'localhost';
/*Basic Roles END*/

/*Deployment BEGIN*/
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'course_developer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'deployment_engineer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'deployment_manager1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'product_owner1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'technical_writer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'trainer1'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'course_developer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'deployment_engineer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'deployment_manager1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'product_owner1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'technical_writer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'trainer1'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'course_developer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'deployment_engineer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'deployment_manager1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'product_owner1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'technical_writer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'trainer1'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'course_developer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'deployment_engineer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'deployment_manager1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'product_owner1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'technical_writer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'trainer1'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'course_developer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'deployment_engineer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'deployment_manager1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'product_owner1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'technical_writer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'trainer1'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'course_developer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'deployment_engineer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'deployment_manager1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'product_owner1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'technical_writer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'trainer1'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'course_developer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'deployment_engineer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'deployment_manager1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'product_owner1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'technical_writer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'trainer1'@'localhost';
/*Deployment END*/

/*Environment BEGIN*/
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'process_engineer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Delivery_Processes.* TO 'tool_specialist1'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'process_engineer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Roles.* TO 'tool_specialist1'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'process_engineer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Tasks.* TO 'tool_specialist1'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'process_engineer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Work_Products.* TO 'tool_specialist1'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'process_engineer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OpenUP.* TO 'tool_specialist1'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'process_engineer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'tool_specialist1'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'process_engineer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Security.* TO 'tool_specialist1'@'localhost';
/*Environment END*/

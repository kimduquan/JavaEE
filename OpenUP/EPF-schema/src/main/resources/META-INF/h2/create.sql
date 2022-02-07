CREATE SCHEMA IF NOT EXISTS EPF_Security;

/*Basic Roles BEGIN*/
CREATE ROLE IF NOT EXISTS Analyst;
CREATE ROLE IF NOT EXISTS Any_Role;
CREATE ROLE IF NOT EXISTS Architect;
CREATE ROLE IF NOT EXISTS Developer;
CREATE ROLE IF NOT EXISTS Project_Manager;
CREATE ROLE IF NOT EXISTS Stakeholder;
CREATE ROLE IF NOT EXISTS Tester;
/*Basic Roles END*/

/*Deployment BEGIN*/
CREATE ROLE IF NOT EXISTS Course_Developer;
CREATE ROLE IF NOT EXISTS Deployment_Engineer;
CREATE ROLE IF NOT EXISTS Deployment_Manager;
CREATE ROLE IF NOT EXISTS Product_Owner;
CREATE ROLE IF NOT EXISTS Technical_Writer;
CREATE ROLE IF NOT EXISTS Trainer;
/*Deployment END*/

/*Environment BEGIN*/
CREATE ROLE IF NOT EXISTS Process_Engineer;
CREATE ROLE IF NOT EXISTS Tool_Specialist;
/*Environment END*/


/*Basic Roles BEGIN*/
CREATE USER IF NOT EXISTS analyst1 PASSWORD 'Analyst1*';
CREATE USER IF NOT EXISTS any_role1 PASSWORD 'Any_Role1*';
CREATE USER IF NOT EXISTS architect1 PASSWORD 'Architect1*';
CREATE USER IF NOT EXISTS developer1 PASSWORD 'Developer1*';
CREATE USER IF NOT EXISTS project_manager1 PASSWORD 'Project_Manager1*';
CREATE USER IF NOT EXISTS stakeholder1 PASSWORD 'Stakeholder1*';
CREATE USER IF NOT EXISTS tester1 PASSWORD 'Tester1*';
/*Basic Roles END*/

/*Deployment BEGIN*/
CREATE USER IF NOT EXISTS course_developer1 PASSWORD 'Course_Developer1*';
CREATE USER IF NOT EXISTS deployment_engineer1 PASSWORD 'Deployment_Engineer1*';
CREATE USER IF NOT EXISTS deployment_manager1 PASSWORD 'Deployment_Manager1*';
CREATE USER IF NOT EXISTS product_owner1 PASSWORD 'Product_Owner1*';
CREATE USER IF NOT EXISTS technical_writer1 PASSWORD 'Technical_Writer1*';
CREATE USER IF NOT EXISTS trainer1 PASSWORD 'Trainer1*';
/*Deployment END*/

/*Environment BEGIN*/
CREATE USER IF NOT EXISTS process_engineer1 PASSWORD 'Process_Engineer1*';
CREATE USER IF NOT EXISTS tool_specialist1 PASSWORD 'Tool_Specialist1*';
/*Environment END*/
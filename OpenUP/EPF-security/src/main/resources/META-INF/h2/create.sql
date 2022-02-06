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


/*Basic Roles BEGIN*/
GRANT Any_Role TO analyst1;
GRANT Any_Role TO any_role1;
GRANT Any_Role TO architect1;
GRANT Any_Role TO developer1;
GRANT Any_Role TO project_manager1;
GRANT Any_Role TO stakeholder1;
GRANT Any_Role TO tester1;
/*Basic Roles END*/

/*Deployment BEGIN*/
GRANT Any_Role TO course_developer1;
GRANT Any_Role TO deployment_engineer1;
GRANT Any_Role TO deployment_manager1;
GRANT Any_Role TO product_owner1;
GRANT Any_Role TO technical_writer1;
GRANT Any_Role TO trainer1;
/*Deployment END*/

/*Environment BEGIN*/
GRANT Any_Role TO process_engineer1;
GRANT Any_Role TO tool_specialist1;
/*Environment END*/


/*Basic Roles BEGIN*/
GRANT Analyst TO analyst1;
GRANT Any_Role TO any_role1;
GRANT Architect TO architect1;
GRANT Developer TO developer1;
GRANT Project_Manager TO project_manager1;
GRANT Stakeholder TO stakeholder1;
GRANT Tester TO tester1;
/*Basic Roles END*/

/*Deployment BEGIN*/
GRANT Course_Developer TO course_developer1;
GRANT Deployment_Engineer TO deployment_engineer1;
GRANT Deployment_Manager TO deployment_manager1;
GRANT Product_Owner TO product_owner1;
GRANT Technical_Writer TO technical_writer1;
GRANT Trainer TO trainer1;
/*Deployment END*/

/*Environment BEGIN*/
GRANT Process_Engineer TO process_engineer1;
GRANT Tool_Specialist TO tool_specialist1;
/*Environment END*/
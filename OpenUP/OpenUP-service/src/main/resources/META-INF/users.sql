/*Basic Roles BEGIN*/
CREATE USER IF NOT EXISTS analyst1 PASSWORD 'analyst';
CREATE USER IF NOT EXISTS any_role1 PASSWORD 'any_role';
CREATE USER IF NOT EXISTS architect1 PASSWORD 'architect';
CREATE USER IF NOT EXISTS developer1 PASSWORD 'developer';
CREATE USER IF NOT EXISTS project_manager1 PASSWORD 'project_manager';
CREATE USER IF NOT EXISTS stakeholder1 PASSWORD 'stakeholder';
CREATE USER IF NOT EXISTS tester1 PASSWORD 'tester';

GRANT Analyst TO analyst1;
GRANT Any_Role TO any_role1;
GRANT Architect TO architect1;
GRANT Developer TO developer1;
GRANT Project_Manager TO project_manager1;
GRANT Stakeholder TO stakeholder1;
GRANT Tester TO tester1;
/*Basic Roles END*/

/*Deployment BEGIN*/
CREATE USER IF NOT EXISTS course_developer1 PASSWORD 'course_developer';
CREATE USER IF NOT EXISTS deployment_engineer1 PASSWORD 'deployment_engineer';
CREATE USER IF NOT EXISTS deployment_manager1 PASSWORD 'deployment_manager';
CREATE USER IF NOT EXISTS product_owner1 PASSWORD 'product_owner';
CREATE USER IF NOT EXISTS technical_writer1 PASSWORD 'technical_writer';
CREATE USER IF NOT EXISTS trainer1 PASSWORD 'trainer';

GRANT Course_Developer TO course_developer1;
GRANT Deployment_Engineer TO deployment_engineer1;
GRANT Deployment_Manager TO deployment_manager1;
GRANT Product_Owner TO product_owner1;
GRANT Technical_Writer TO technical_writer1;
GRANT Trainer TO trainer1;
/*Deployment END*/

/*Environment BEGIN*/
CREATE USER IF NOT EXISTS process_engineer1 PASSWORD 'process_engineer';
CREATE USER IF NOT EXISTS tool_specialist1 PASSWORD 'tool_specialist';

GRANT Process_Engineer TO process_engineer1;
GRANT Tool_Specialist TO tool_specialist1;
/*Environment END*/
/*Basic Roles BEGIN*/
CREATE USER IF NOT EXISTS analyst PASSWORD 'analyst';
CREATE USER IF NOT EXISTS any_role PASSWORD 'any_role';
CREATE USER IF NOT EXISTS architect PASSWORD 'architect';
CREATE USER IF NOT EXISTS developer PASSWORD 'developer';
CREATE USER IF NOT EXISTS project_manager PASSWORD 'project_manager';
CREATE USER IF NOT EXISTS stakeholder PASSWORD 'stakeholder';
CREATE USER IF NOT EXISTS tester PASSWORD 'tester';

GRANT analyst TO Analyst;
GRANT any_role TO Any_Role;
GRANT architect TO Architect;
GRANT developer TO Developer;
GRANT project_manager TO Project_Manager;
GRANT stakeholder TO Stakeholder;
GRANT tester TO Tester;
/*Basic Roles END*/

/*Deployment BEGIN*/
CREATE USER IF NOT EXISTS course_developer PASSWORD 'course_developer';
CREATE USER IF NOT EXISTS deployment_engineer PASSWORD 'deployment_engineer';
CREATE USER IF NOT EXISTS deployment_manager PASSWORD 'deployment_manager';
CREATE USER IF NOT EXISTS product_owner PASSWORD 'product_owner';
CREATE USER IF NOT EXISTS technical_writer PASSWORD 'technical_writer';
CREATE USER IF NOT EXISTS trainer PASSWORD 'trainer';

GRANT course_developer TO Course_Developer;
GRANT deployment_engineer TO Deployment_Engineer;
GRANT deployment_manager TO Deployment_Manager;
GRANT product_owner TO Product_Owner;
GRANT technical_writer TO Technical_Writer;
GRANT trainer TO Trainer;
/*Deployment END*/

/*Environment BEGIN*/
CREATE USER IF NOT EXISTS process_engineer PASSWORD 'process_engineer';
CREATE USER IF NOT EXISTS tool_specialist PASSWORD 'tool_specialist';

GRANT process_engineer TO Process_Engineer;
GRANT tool_specialist TO Tool_Specialist;
/*Environment END*/
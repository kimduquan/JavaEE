/*Basic Roles BEGIN*/
CREATE ROLE IF NOT EXISTS Analyst;
CREATE ROLE IF NOT EXISTS Any_Role;
CREATE ROLE IF NOT EXISTS Architect;
CREATE ROLE IF NOT EXISTS Developer;
CREATE ROLE IF NOT EXISTS Project_Manager;
CREATE ROLE IF NOT EXISTS Stakeholder;
CREATE ROLE IF NOT EXISTS Tester;

GRANT Analyst TO PUBLIC;
GRANT Any_Role TO PUBLIC;
GRANT Architect TO PUBLIC;
GRANT Developer TO PUBLIC;
GRANT Project_Manager TO PUBLIC;
GRANT Stakeholder TO PUBLIC;
GRANT Tester TO PUBLIC;

GRANT Analyst TO Any_Role;
GRANT Architect TO Any_Role;
GRANT Developer TO Any_Role;
GRANT Project_Manager TO Any_Role;
GRANT Stakeholder TO Any_Role;
GRANT Tester TO Any_Role;
/*Basic Roles END*/

/*Deployment BEGIN*/
CREATE ROLE IF NOT EXISTS Course_Developer;
CREATE ROLE IF NOT EXISTS Deployment_Engineer;
CREATE ROLE IF NOT EXISTS Deployment_Manager;
CREATE ROLE IF NOT EXISTS Product_Owner;
CREATE ROLE IF NOT EXISTS Technical_Writer;
CREATE ROLE IF NOT EXISTS Trainer;

GRANT Course_Developer TO PUBLIC;
GRANT Deployment_Engineer TO PUBLIC;
GRANT Deployment_Manager TO PUBLIC;
GRANT Product_Owner TO PUBLIC;
GRANT Technical_Writer TO PUBLIC;
GRANT Trainer TO PUBLIC;

GRANT Course_Developer TO Any_Role;
GRANT Deployment_Engineer TO Any_Role;
GRANT Deployment_Manager TO Any_Role;
GRANT Product_Owner TO Any_Role;
GRANT Technical_Writer TO Any_Role;
GRANT Trainer TO Any_Role;
/*Deployment END*/

/*Environment BEGIN*/
CREATE ROLE IF NOT EXISTS Process_Engineer;
CREATE ROLE IF NOT EXISTS Tool_Specialist;

GRANT Process_Engineer TO PUBLIC;
GRANT Tool_Specialist TO PUBLIC;

GRANT Process_Engineer TO Any_Role;
GRANT Tool_Specialist TO Any_Role;
/*Environment END*/
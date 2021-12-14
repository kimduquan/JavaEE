/*Basic Roles BEGIN*/
GRANT SELECT ON EPF_Delivery_Processes.* TO 'Analyst';
GRANT SELECT ON EPF_Delivery_Processes.* TO 'Any_Role';
GRANT SELECT ON EPF_Delivery_Processes.* TO 'Architect';
GRANT SELECT ON EPF_Delivery_Processes.* TO 'Developer';
GRANT SELECT ON EPF_Delivery_Processes.* TO 'Project_Manager';
GRANT SELECT ON EPF_Delivery_Processes.* TO 'Stakeholder';
GRANT SELECT ON EPF_Delivery_Processes.* TO 'Tester';

GRANT SELECT ON EPF_Roles.* TO 'Analyst';
GRANT SELECT ON EPF_Roles.* TO 'Any_Role';
GRANT SELECT ON EPF_Roles.* TO 'Architect';
GRANT SELECT ON EPF_Roles.* TO 'Developer';
GRANT SELECT ON EPF_Roles.* TO 'Project_Manager';
GRANT SELECT ON EPF_Roles.* TO 'Stakeholder';
GRANT SELECT ON EPF_Roles.* TO 'Tester';

GRANT SELECT ON EPF_Tasks.* TO 'Analyst';
GRANT SELECT ON EPF_Tasks.* TO 'Any_Role';
GRANT SELECT ON EPF_Tasks.* TO 'Architect';
GRANT SELECT ON EPF_Tasks.* TO 'Developer';
GRANT SELECT ON EPF_Tasks.* TO 'Project_Manager';
GRANT SELECT ON EPF_Tasks.* TO 'Stakeholder';
GRANT SELECT ON EPF_Tasks.* TO 'Tester';

GRANT SELECT ON EPF_Work_Products.* TO 'Analyst';
GRANT SELECT ON EPF_Work_Products.* TO 'Any_Role';
GRANT SELECT ON EPF_Work_Products.* TO 'Architect';
GRANT SELECT ON EPF_Work_Products.* TO 'Developer';
GRANT SELECT ON EPF_Work_Products.* TO 'Project_Manager';
GRANT SELECT ON EPF_Work_Products.* TO 'Stakeholder';
GRANT SELECT ON EPF_Work_Products.* TO 'Tester';

GRANT SELECT ON OPENUP.* TO 'Analyst';
GRANT SELECT ON OPENUP.* TO 'Any_Role';
GRANT SELECT ON OPENUP.* TO 'Architect';
GRANT SELECT ON OPENUP.* TO 'Developer';
GRANT SELECT ON OPENUP.* TO 'Project_Manager';
GRANT SELECT ON OPENUP.* TO 'Stakeholder';
GRANT SELECT ON OPENUP.* TO 'Tester';

/*REVOKE ALL ON sys.* FROM 'Analyst';
REVOKE ALL ON sys.* FROM 'Any_Role';
REVOKE ALL ON sys.* FROM 'Architect';
REVOKE ALL ON sys.* FROM 'Developer';
REVOKE ALL ON sys.* FROM 'Project_Manager';
REVOKE ALL ON sys.* FROM 'Stakeholder';
REVOKE ALL ON sys.* FROM 'Tester';

REVOKE ALL ON sys.* FROM 'analyst1'@'localhost';
REVOKE ALL ON sys.* FROM 'any_role1'@'localhost';
REVOKE ALL ON sys.* FROM 'architect1'@'localhost';
REVOKE ALL ON sys.* FROM 'developer1'@'localhost';
REVOKE ALL ON sys.* FROM 'project_manager1'@'localhost';
REVOKE ALL ON sys.* FROM 'stakeholder1'@'localhost';
REVOKE ALL ON sys.* FROM 'tester1'@'localhost';*/
/*Basic Roles END*/

/*Deployment BEGIN*/
GRANT SELECT ON EPF_Delivery_Processes.* TO 'Course_Developer';
GRANT SELECT ON EPF_Delivery_Processes.* TO 'Deployment_Engineer';
GRANT SELECT ON EPF_Delivery_Processes.* TO 'Deployment_Manager';
GRANT SELECT ON EPF_Delivery_Processes.* TO 'Product_Owner';
GRANT SELECT ON EPF_Delivery_Processes.* TO 'Technical_Writer';
GRANT SELECT ON EPF_Delivery_Processes.* TO 'Trainer';

GRANT SELECT ON EPF_Roles.* TO 'Course_Developer';
GRANT SELECT ON EPF_Roles.* TO 'Deployment_Engineer';
GRANT SELECT ON EPF_Roles.* TO 'Deployment_Manager';
GRANT SELECT ON EPF_Roles.* TO 'Product_Owner';
GRANT SELECT ON EPF_Roles.* TO 'Technical_Writer';
GRANT SELECT ON EPF_Roles.* TO 'Trainer';

GRANT SELECT ON EPF_Tasks.* TO 'Course_Developer';
GRANT SELECT ON EPF_Tasks.* TO 'Deployment_Engineer';
GRANT SELECT ON EPF_Tasks.* TO 'Deployment_Manager';
GRANT SELECT ON EPF_Tasks.* TO 'Product_Owner';
GRANT SELECT ON EPF_Tasks.* TO 'Technical_Writer';
GRANT SELECT ON EPF_Tasks.* TO 'Trainer';

GRANT SELECT ON EPF_Work_Products.* TO 'Course_Developer';
GRANT SELECT ON EPF_Work_Products.* TO 'Deployment_Engineer';
GRANT SELECT ON EPF_Work_Products.* TO 'Deployment_Manager';
GRANT SELECT ON EPF_Work_Products.* TO 'Product_Owner';
GRANT SELECT ON EPF_Work_Products.* TO 'Technical_Writer';
GRANT SELECT ON EPF_Work_Products.* TO 'Trainer';

GRANT SELECT ON OPENUP.* TO 'Course_Developer';
GRANT SELECT ON OPENUP.* TO 'Deployment_Engineer';
GRANT SELECT ON OPENUP.* TO 'Deployment_Manager';
GRANT SELECT ON OPENUP.* TO 'Product_Owner';
GRANT SELECT ON OPENUP.* TO 'Technical_Writer';
GRANT SELECT ON OPENUP.* TO 'Trainer';

/*REVOKE ALL ON sys.* FROM 'Course_Developer';
REVOKE ALL ON sys.* FROM 'Deployment_Engineer';
REVOKE ALL ON sys.* FROM 'Deployment_Manager';
REVOKE ALL ON sys.* FROM 'Product_Owner';
REVOKE ALL ON sys.* FROM 'Technical_Writer';
REVOKE ALL ON sys.* FROM 'Trainer';

REVOKE ALL ON sys.* FROM 'course_developer1'@'localhost';
REVOKE ALL ON sys.* FROM 'deployment_engineer1'@'localhost';
REVOKE ALL ON sys.* FROM 'deployment_manager1'@'localhost';
REVOKE ALL ON sys.* FROM 'product_owner1'@'localhost';
REVOKE ALL ON sys.* FROM 'technical_writer1'@'localhost';
REVOKE ALL ON sys.* FROM 'trainer1'@'localhost';*/
/*Deployment END*/

/*Environment BEGIN*/
GRANT SELECT ON EPF_Delivery_Processes.* TO 'Process_Engineer';
GRANT SELECT ON EPF_Delivery_Processes.* TO 'Tool_Specialist';

GRANT SELECT ON EPF_Roles.* TO 'Process_Engineer';
GRANT SELECT ON EPF_Roles.* TO 'Tool_Specialist';

GRANT SELECT ON EPF_Tasks.* TO 'Process_Engineer';
GRANT SELECT ON EPF_Tasks.* TO 'Tool_Specialist';

GRANT SELECT ON EPF_Work_Products.* TO 'Process_Engineer';
GRANT SELECT ON EPF_Work_Products.* TO 'Tool_Specialist';

GRANT SELECT ON OPENUP.* TO 'Process_Engineer';
GRANT SELECT ON OPENUP.* TO 'Tool_Specialist';

/*REVOKE ALL ON sys.* FROM 'Process_Engineer';
REVOKE ALL ON sys.* FROM 'Tool_Specialist';

REVOKE ALL ON sys.* FROM 'process_engineer1'@'localhost';
REVOKE ALL ON sys.* FROM 'tool_specialist1'@'localhost';*/
/*Environment END*/
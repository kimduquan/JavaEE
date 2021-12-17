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

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'analyst1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'any_role1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'architect1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'developer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'project_manager1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'stakeholder1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'tester1'@'localhost';
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

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'course_developer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'deployment_engineer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'deployment_manager1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'product_owner1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'technical_writer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'trainer1'@'localhost';
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

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'process_engineer1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'tool_specialist1'@'localhost';
/*Environment END*/
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
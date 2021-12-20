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
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

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'Analyst'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'Any_Role'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'Architect'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'Developer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'Project_Manager'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'Stakeholder'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'Tester'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Analyst'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Any_Role'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Architect'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Developer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Project_Manager'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Stakeholder'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Tester'@'localhost';
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

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'Course_Developer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'Deployment_Engineer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'Deployment_Manager'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'Product_Owner'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'Technical_Writer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'Trainer'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Course_Developer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Deployment_Engineer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Deployment_Manager'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Product_Owner'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Technical_Writer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Trainer'@'localhost';
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

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'Process_Engineer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON OPENUP.* TO 'Tool_Specialist'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Process_Engineer'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EPF_Net.* TO 'Tool_Specialist'@'localhost';
/*Environment END*/
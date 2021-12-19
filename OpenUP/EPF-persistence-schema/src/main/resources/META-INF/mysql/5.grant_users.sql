/*Basic Roles BEGIN*/
GRANT 'Any_Role'@'localhost', 'Analyst'@'localhost' TO 'analyst1'@'localhost';
GRANT 'Any_Role'@'localhost', 'Any_Role'@'localhost' TO 'any_role1'@'localhost';
GRANT 'Any_Role'@'localhost', 'Architect'@'localhost' TO 'architect1'@'localhost';
GRANT 'Any_Role'@'localhost', 'Developer'@'localhost' TO 'developer1'@'localhost';
GRANT 'Any_Role'@'localhost', 'Project_Manager'@'localhost' TO 'project_manager1'@'localhost';
GRANT 'Any_Role'@'localhost', 'Stakeholder'@'localhost' TO 'stakeholder1'@'localhost';
GRANT 'Any_Role'@'localhost', 'Tester'@'localhost' TO 'tester1'@'localhost';
/*Basic Roles END*/

/*Deployment BEGIN*/
GRANT 'Any_Role'@'localhost', 'Course_Developer'@'localhost' TO 'course_developer1'@'localhost';
GRANT 'Any_Role'@'localhost', 'Deployment_Engineer'@'localhost' TO 'deployment_engineer1'@'localhost';
GRANT 'Any_Role'@'localhost', 'Deployment_Manager'@'localhost' TO 'deployment_manager1'@'localhost';
GRANT 'Any_Role'@'localhost', 'Product_Owner'@'localhost' TO 'product_owner1'@'localhost';
GRANT 'Any_Role'@'localhost', 'Technical_Writer'@'localhost' TO 'technical_writer1'@'localhost';
GRANT 'Any_Role'@'localhost', 'Trainer'@'localhost' TO 'trainer1'@'localhost';
/*Deployment END*/

/*Environment BEGIN*/
GRANT 'Any_Role'@'localhost', 'Process_Engineer'@'localhost' TO 'process_engineer1'@'localhost';
GRANT 'Any_Role'@'localhost', 'Tool_Specialist'@'localhost' TO 'tool_specialist1'@'localhost';
/*Environment END*/

/*Basic Roles BEGIN*/
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Analyst'@'localhost' TO 'analyst1'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Any_Role'@'localhost' TO 'any_role1'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Architect'@'localhost' TO 'architect1'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Developer'@'localhost' TO 'developer1'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Project_Manager'@'localhost' TO 'project_manager1'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Stakeholder'@'localhost' TO 'stakeholder1'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Tester'@'localhost' TO 'tester1'@'localhost';
/*Basic Roles END*/

/*Deployment BEGIN*/
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Course_Developer'@'localhost' TO 'course_developer1'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Deployment_Engineer'@'localhost' TO 'deployment_engineer1'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Deployment_Manager'@'localhost' TO 'deployment_manager1'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Product_Owner'@'localhost' TO 'product_owner1'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Technical_Writer'@'localhost' TO 'technical_writer1'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Trainer'@'localhost' TO 'trainer1'@'localhost';
/*Deployment END*/

/*Environment BEGIN*/
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Process_Engineer'@'localhost' TO 'process_engineer1'@'localhost';
SET DEFAULT ROLE 'Any_Role'@'localhost', 'Tool_Specialist'@'localhost' TO 'tool_specialist1'@'localhost';
/*Environment END*/
/*Basic Roles BEGIN*/
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('analyst1');
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('any_role1');
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('architect1');
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('developer1');
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('project_manager1');
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('stakeholder1');
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('tester1');
/*Basic Roles END*/

/*Deployment BEGIN*/
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('course_developer1');
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('deployment_engineer1');
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('deployment_manager1');
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('product_owner1');
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('technical_writer1');
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('trainer1');
/*Deployment END*/

/*Environment BEGIN*/
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('process_engineer1');
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('tool_specialist1');
/*Environment END*/

/*Basic Roles BEGIN*/
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('analyst1', 'full_name', 'Analyst 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('analyst1', 'email', 'analyst1@openup.org');

INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('any_role1', 'full_name', 'Any Role 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('any_role1', 'email', 'any_role1@openup.org');

INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('architect1', 'full_name', 'Architect 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('architect1', 'email', 'architect1@openup.org');

INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('developer1', 'full_name', 'Developer 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('developer1', 'email', 'developer1@openup.org');

INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('project_manager1', 'full_name', 'Project Manager 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('project_manager1', 'email', 'project_manager1@openup.org');

INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('stakeholder1', 'full_name', 'Stakeholder 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('stakeholder1', 'email', 'stakeholder1@openup.org');

INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('tester1', 'full_name', 'Tester 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('tester1', 'email', 'tester1@openup.org');
/*Basic Roles END*/

/*Deployment BEGIN*/
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('course_developer1', 'full_name', 'Course Developer 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('course_developer1', 'email', 'course_developer1@openup.org');

INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('deployment_engineer1', 'full_name', 'Deployment Engineer 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('deployment_engineer1', 'email', 'deployment_engineer1@openup.org');

INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('deployment_manager1', 'full_name', 'Deployment Manager 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('deployment_manager1', 'email', 'deployment_manager1@openup.org');

INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('product_owner1', 'full_name', 'Product Owner 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('product_owner1', 'email', 'product_owner1@openup.org');

INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('technical_writer1', 'full_name', 'Technical Writer 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('technical_writer1', 'email', 'technical_writer1@openup.org');

INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('trainer1', 'full_name', 'Trainer 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('trainer1', 'email', 'trainer1@openup.org');
/*Deployment END*/

/*Environment BEGIN*/
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('process_engineer1', 'full_name', 'Process Engineer 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('process_engineer1', 'email', 'process_engineer1@openup.org');

INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('tool_specialist1', 'full_name', 'Tool Specialist 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, NAME, VALUE) VALUES ('tool_specialist1', 'email', 'tool_specialist1@openup.org');
/*Environment END*/
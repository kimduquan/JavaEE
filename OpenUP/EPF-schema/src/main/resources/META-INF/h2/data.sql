/*Basic Roles BEGIN*/
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('analyst1@openup');
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('any_role1@openup');
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('architect1@openup');
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('developer1@openup');
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('project_manager1@openup');
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('stakeholder1@openup');
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('tester1@openup');
/*Basic Roles END*/

/*Deployment BEGIN*/
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('course_developer1@openup');
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('deployment_engineer1@openup');
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('deployment_manager1@openup');
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('product_owner1@openup');
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('technical_writer1@openup');
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('trainer1@openup');
/*Deployment END*/

/*Environment BEGIN*/
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('process_engineer1@openup');
INSERT INTO EPF_Security.PRINCIPAL (NAME) VALUES ('tool_specialist1@openup');
/*Environment END*/

/*Basic Roles BEGIN*/
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('analyst1@openup', 'full_name', 'Analyst 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('analyst1@openup', 'email', 'analyst1@openup.org');

INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('any_role1@openup', 'full_name', 'Any Role 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('any_role1@openup', 'email', 'any_role1@openup.org');

INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('architect1@openup', 'full_name', 'Architect 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('architect1@openup', 'email', 'architect1@openup.org');

INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('developer1@openup', 'full_name', 'Developer 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('developer1@openup', 'email', 'developer1@openup.org');

INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('project_manager1@openup', 'full_name', 'Project Manager 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('project_manager1@openup', 'email', 'project_manager1@openup.org');

INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('stakeholder1@openup', 'full_name', 'Stakeholder 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('stakeholder1@openup', 'email', 'stakeholder1@openup.org');

INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('tester1@openup', 'full_name', 'Tester 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('tester1@openup', 'email', 'tester1@openup.org');
/*Basic Roles END*/

/*Deployment BEGIN*/
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('course_developer1@openup', 'full_name', 'Course Developer 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('course_developer1@openup', 'email', 'course_developer1@openup.org');

INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('deployment_engineer1@openup', 'full_name', 'Deployment Engineer 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('deployment_engineer1@openup', 'email', 'deployment_engineer1@openup.org');

INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('deployment_manager1@openup', 'full_name', 'Deployment Manager 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('deployment_manager1@openup', 'email', 'deployment_manager1@openup.org');

INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('product_owner1@openup', 'full_name', 'Product Owner 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('product_owner1@openup', 'email', 'product_owner1@openup.org');

INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('technical_writer1@openup', 'full_name', 'Technical Writer 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('technical_writer1@openup', 'email', 'technical_writer1@openup.org');

INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('trainer1@openup', 'full_name', 'Trainer 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('trainer1@openup', 'email', 'trainer1@openup.org');
/*Deployment END*/

/*Environment BEGIN*/
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('process_engineer1@openup', 'full_name', 'Process Engineer 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('process_engineer1@openup', 'email', 'process_engineer1@openup.org');

INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('tool_specialist1@openup', 'full_name', 'Tool Specialist 1');
INSERT INTO EPF_Security.PRINCIPAL_CLAIMS (PRINCIPAL_NAME, CLAIM, CLAIM_VALUE) VALUES ('tool_specialist1@openup', 'email', 'tool_specialist1@openup.org');
/*Environment END*/
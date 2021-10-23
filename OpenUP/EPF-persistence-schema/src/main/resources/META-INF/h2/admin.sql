CREATE USER IF NOT EXISTS admin1 PASSWORD 'admin' ADMIN;
GRANT Any_Role TO admin1;

/*Basic Roles BEGIN*/
ALTER USER analyst1 ADMIN TRUE;
ALTER USER any_role1 ADMIN TRUE;
ALTER USER architect1 ADMIN TRUE;
ALTER USER developer1 ADMIN TRUE;
ALTER USER project_manager1 ADMIN TRUE;
ALTER USER stakeholder1 ADMIN TRUE;
ALTER USER tester1 ADMIN TRUE;
/*Basic Roles END*/

/*Deployment BEGIN*/
ALTER USER course_developer1 ADMIN TRUE;
ALTER USER deployment_engineer1 ADMIN TRUE;
ALTER USER deployment_manager1 ADMIN TRUE;
ALTER USER product_owner1 ADMIN TRUE;
ALTER USER technical_writer1 ADMIN TRUE;
ALTER USER trainer1 ADMIN TRUE;
/*Deployment END*/

/*Environment BEGIN*/
ALTER USER process_engineer1 ADMIN TRUE;
ALTER USER tool_specialist1 ADMIN TRUE;
/*Environment END*/
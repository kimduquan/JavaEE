/*ROLE_SET BEGIN*/
INSERT INTO OPENUP."PUBLIC".ROLE_SET ("NAME") 
	VALUES ('Basic Roles');
INSERT INTO OPENUP."PUBLIC".ROLE_SET ("NAME") 
	VALUES ('Deployment');
INSERT INTO OPENUP."PUBLIC".ROLE_SET ("NAME") 
	VALUES ('Environment');
/*ROLE_SET END*/

/*_ROLE BEGIN*/
INSERT INTO OPENUP."PUBLIC"."_ROLE" ("NAME") 
	VALUES ('Analyst');
INSERT INTO OPENUP."PUBLIC"."_ROLE" ("NAME") 
	VALUES ('Any Role');
INSERT INTO OPENUP."PUBLIC"."_ROLE" ("NAME") 
	VALUES ('Architect');
INSERT INTO OPENUP."PUBLIC"."_ROLE" ("NAME") 
	VALUES ('Course Developer');
INSERT INTO OPENUP."PUBLIC"."_ROLE" ("NAME") 
	VALUES ('Deployment Engineer');
INSERT INTO OPENUP."PUBLIC"."_ROLE" ("NAME") 
	VALUES ('Deployment Manager');
INSERT INTO OPENUP."PUBLIC"."_ROLE" ("NAME") 
	VALUES ('Developer');
INSERT INTO OPENUP."PUBLIC"."_ROLE" ("NAME") 
	VALUES ('Process Engineer');
INSERT INTO OPENUP."PUBLIC"."_ROLE" ("NAME") 
	VALUES ('Product Owner');
INSERT INTO OPENUP."PUBLIC"."_ROLE" ("NAME") 
	VALUES ('Project Manager');
INSERT INTO OPENUP."PUBLIC"."_ROLE" ("NAME") 
	VALUES ('Stakeholder');
INSERT INTO OPENUP."PUBLIC"."_ROLE" ("NAME") 
	VALUES ('Technical Writer');
INSERT INTO OPENUP."PUBLIC"."_ROLE" ("NAME") 
	VALUES ('Tester');
INSERT INTO OPENUP."PUBLIC"."_ROLE" ("NAME") 
	VALUES ('Tool Specialist');
INSERT INTO OPENUP."PUBLIC"."_ROLE" ("NAME") 
	VALUES ('Trainer');
/*_ROLE END*/

/*ROLES BEGIN*/
INSERT INTO OPENUP."PUBLIC".ROLES (ROLE_SET, "ROLE") 
	VALUES ('Basic Roles', 'Analyst');
INSERT INTO OPENUP."PUBLIC".ROLES (ROLE_SET, "ROLE") 
	VALUES ('Basic Roles', 'Any Role');
INSERT INTO OPENUP."PUBLIC".ROLES (ROLE_SET, "ROLE") 
	VALUES ('Basic Roles', 'Architect');
INSERT INTO OPENUP."PUBLIC".ROLES (ROLE_SET, "ROLE") 
	VALUES ('Basic Roles', 'Developer');
INSERT INTO OPENUP."PUBLIC".ROLES (ROLE_SET, "ROLE") 
	VALUES ('Basic Roles', 'Project Manager');
INSERT INTO OPENUP."PUBLIC".ROLES (ROLE_SET, "ROLE") 
	VALUES ('Basic Roles', 'Stakeholder');
INSERT INTO OPENUP."PUBLIC".ROLES (ROLE_SET, "ROLE") 
	VALUES ('Basic Roles', 'Tester');
INSERT INTO OPENUP."PUBLIC".ROLES (ROLE_SET, "ROLE") 
	VALUES ('Deployment', 'Course Developer');
INSERT INTO OPENUP."PUBLIC".ROLES (ROLE_SET, "ROLE") 
	VALUES ('Deployment', 'Deployment Engineer');
INSERT INTO OPENUP."PUBLIC".ROLES (ROLE_SET, "ROLE") 
	VALUES ('Deployment', 'Deployment Manager');
INSERT INTO OPENUP."PUBLIC".ROLES (ROLE_SET, "ROLE") 
	VALUES ('Deployment', 'Product Owner');
INSERT INTO OPENUP."PUBLIC".ROLES (ROLE_SET, "ROLE") 
	VALUES ('Deployment', 'Technical Writer');
INSERT INTO OPENUP."PUBLIC".ROLES (ROLE_SET, "ROLE") 
	VALUES ('Deployment', 'Trainer');
INSERT INTO OPENUP."PUBLIC".ROLES (ROLE_SET, "ROLE") 
	VALUES ('Environment', 'Process Engineer');
INSERT INTO OPENUP."PUBLIC".ROLES (ROLE_SET, "ROLE") 
	VALUES ('Environment', 'Tool Specialist');
/*ROLES END*/

/*_DOMAIN BEGIN*/
INSERT INTO OPENUP."PUBLIC"."_DOMAIN" ("NAME") 
	VALUES ('Architecture');
INSERT INTO OPENUP."PUBLIC"."_DOMAIN" ("NAME") 
	VALUES ('Deployment');
INSERT INTO OPENUP."PUBLIC"."_DOMAIN" ("NAME") 
	VALUES ('Development');
INSERT INTO OPENUP."PUBLIC"."_DOMAIN" ("NAME") 
	VALUES ('Environment');
INSERT INTO OPENUP."PUBLIC"."_DOMAIN" ("NAME") 
	VALUES ('Project Management');
INSERT INTO OPENUP."PUBLIC"."_DOMAIN" ("NAME") 
	VALUES ('Requirements');
INSERT INTO OPENUP."PUBLIC"."_DOMAIN" ("NAME") 
	VALUES ('Test');
/*_DOMAIN END*/

/*ARTIFACT BEGIN*/
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Architecture Notebook', 'Architect');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Product Documentation', 'Technical Writer');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Support Documentation', 'Technical Writer');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('User Documentation', 'Technical Writer');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Training Materials', 'Course Developer');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Backout Plan', 'Deployment Engineer');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Deployment Plan', 'Deployment Engineer');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Infrastructure', NULL);
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Release Communications', 'Deployment Engineer');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Release Controls', 'Deployment Manager');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Implementation', 'Developer');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Build', 'Developer');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Developer Test', 'Developer');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Design', 'Developer');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Project Defined Process', 'Process Engineer');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Tools', 'Tool Specialist');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Risk List', 'Project Manager');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Work Items List', 'Project Manager');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Iteration Plan', 'Project Manager');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Project Plan', 'Project Manager');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Glossary', 'Analyst');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Vision', 'Analyst');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('System-Wide Requirements', 'Analyst');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Use-Case Model', 'Analyst');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Use Case', 'Analyst');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Test Case', 'Tester');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Test Script', 'Tester');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Test Log', 'Tester');
INSERT INTO OPENUP."PUBLIC".ARTIFACT ("NAME", RESPONSIBLE) 
	VALUES ('Release', 'Deployment Engineer');
/*ARTIFACT END*/

/*WORK_PRODUCTS BEGIN*/
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Architecture', 'Architecture Notebook');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Deployment', 'Backout Plan');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Deployment', 'Deployment Plan');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Deployment', 'Infrastructure');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Deployment', 'Product Documentation');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Deployment', 'Release');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Deployment', 'Release Communications');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Deployment', 'Release Controls');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Deployment', 'Support Documentation');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Deployment', 'Training Materials');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Deployment', 'User Documentation');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Development', 'Build');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Development', 'Design');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Development', 'Developer Test');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Development', 'Implementation');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Environment', 'Project Defined Process');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Environment', 'Tools');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Project Management', 'Iteration Plan');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Project Management', 'Project Plan');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Project Management', 'Risk List');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Project Management', 'Work Items List');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Requirements', 'Glossary');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Requirements', 'System-Wide Requirements');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Requirements', 'Use Case');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Requirements', 'Use-Case Model');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Requirements', 'Vision');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Test', 'Test Case');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Test', 'Test Log');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCTS ("DOMAIN", ARTIFACT) 
	VALUES ('Test', 'Test Script');
/*WORK_PRODUCTS END*/

/*WORK_PRODUCT_SLOT BEGIN*/
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCT_SLOT ("NAME") 
	VALUES ('Project Definition and Scope');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCT_SLOT ("NAME") 
	VALUES ('Project Risk');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCT_SLOT ("NAME") 
	VALUES ('Project Status');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCT_SLOT ("NAME") 
	VALUES ('Project Work');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCT_SLOT ("NAME") 
	VALUES ('Technical Architecture');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCT_SLOT ("NAME") 
	VALUES ('Technical Design');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCT_SLOT ("NAME") 
	VALUES ('Technical Implementation');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCT_SLOT ("NAME") 
	VALUES ('Technical Specification');
INSERT INTO OPENUP."PUBLIC".WORK_PRODUCT_SLOT ("NAME") 
	VALUES ('Technical Test Results');
/*WORK_PRODUCT_SLOT END*/

/*DISCIPLINE BEGIN*/
INSERT INTO OPENUP."PUBLIC".DISCIPLINE ("NAME") 
	VALUES ('Architecture');
INSERT INTO OPENUP."PUBLIC".DISCIPLINE ("NAME") 
	VALUES ('Deployment');
INSERT INTO OPENUP."PUBLIC".DISCIPLINE ("NAME") 
	VALUES ('Development');
INSERT INTO OPENUP."PUBLIC".DISCIPLINE ("NAME") 
	VALUES ('Environment');
INSERT INTO OPENUP."PUBLIC".DISCIPLINE ("NAME") 
	VALUES ('Project Management');
INSERT INTO OPENUP."PUBLIC".DISCIPLINE ("NAME") 
	VALUES ('Requirements');
INSERT INTO OPENUP."PUBLIC".DISCIPLINE ("NAME") 
	VALUES ('Test');
/*DISCIPLINE END*/

/*TASK BEGIN*/
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Refine the Architecture', 'Architect');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Envision the Architecture', 'Architect');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Develop Product Documentation', 'Technical Writer');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Develop User Documentation', 'Technical Writer');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Develop Support Documentation', 'Technical Writer');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Deliver end user Training', 'Trainer');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Deliver Support Training', 'Trainer');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Develop Training Materials', 'Course Developer');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Deliver Release Communications', 'Deployment Engineer');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Execute Backout Plan', 'Deployment Engineer');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Execute Deployment Plan', 'Deployment Engineer');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Package the Release', 'Developer');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Verify Successful Deployment', 'Deployment Engineer');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Develop Backout Plan', 'Developer');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Develop Release Communications', 'Deployment Engineer');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Install and Validate Infrastructure', 'Deployment Engineer');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Plan Deployment', 'Deployment Engineer');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Review and Conform to Release Controls', 'Developer');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Implement Developer Tests', 'Developer');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Implement Solution', 'Developer');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Run Developer Tests', 'Developer');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Integrate and Create Build', 'Developer');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Design the Solution', 'Developer');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Deploy the Process', 'Process Engineer');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Tailor the Process', 'Process Engineer');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Set Up Tools', 'Tool Specialist');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Verify Tool Configuration and Installation', 'Tool Specialist');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Assess Results', 'Project Manager');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Manage Iteration', 'Project Manager');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Plan Iteration', 'Project Manager');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Plan Project', 'Project Manager');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Request Change', 'Any Role');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Identify and Outline Requirements', 'Analyst');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Detail Use-Case Scenarios', 'Analyst');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Detail System-Wide Requirements', 'Analyst');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Develop Technical Vision', 'Analyst');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Create Test Cases', 'Tester');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Implement Tests', 'Tester');
INSERT INTO OPENUP."PUBLIC".TASK ("NAME", TASK_PRIMARY_PERFORMER) 
	VALUES ('Run Tests', 'Tester');
/*TASK END*/

/*TASKS BEGIN*/
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Architecture', 'Envision the Architecture');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Architecture', 'Refine the Architecture');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Deployment', 'Deliver Release Communications');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Deployment', 'Deliver Support Training');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Deployment', 'Deliver end user Training');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Deployment', 'Develop Backout Plan');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Deployment', 'Develop Product Documentation');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Deployment', 'Develop Release Communications');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Deployment', 'Develop Support Documentation');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Deployment', 'Develop Training Materials');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Deployment', 'Develop User Documentation');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Deployment', 'Execute Backout Plan');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Deployment', 'Execute Deployment Plan');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Deployment', 'Install and Validate Infrastructure');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Deployment', 'Package the Release');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Deployment', 'Plan Deployment');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Deployment', 'Review and Conform to Release Controls');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Deployment', 'Verify Successful Deployment');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Development', 'Design the Solution');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Development', 'Implement Developer Tests');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Development', 'Implement Solution');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Development', 'Integrate and Create Build');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Development', 'Run Developer Tests');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Environment', 'Deploy the Process');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Environment', 'Set Up Tools');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Environment', 'Tailor the Process');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Environment', 'Verify Tool Configuration and Installation');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Project Management', 'Assess Results');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Project Management', 'Manage Iteration');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Project Management', 'Plan Iteration');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Project Management', 'Plan Project');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Project Management', 'Request Change');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Requirements', 'Detail System-Wide Requirements');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Requirements', 'Detail Use-Case Scenarios');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Requirements', 'Develop Technical Vision');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Requirements', 'Identify and Outline Requirements');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Test', 'Create Test Cases');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Test', 'Implement Tests');
INSERT INTO OPENUP."PUBLIC".TASKS (DISCIPLINE, TASK) 
	VALUES ('Test', 'Run Tests');
/*TASKS END*/

/*ROLE_ADDITIONALLY_PERFORMS BEGIN*/
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Analyst', 'Assess Results');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Analyst', 'Create Test Cases');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Analyst', 'Design the Solution');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Analyst', 'Envision the Architecture');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Analyst', 'Implement Tests');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Analyst', 'Manage Iteration');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Analyst', 'Plan Iteration');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Analyst', 'Plan Project');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Architect', 'Assess Results');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Architect', 'Design the Solution');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Architect', 'Detail System-Wide Requirements');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Architect', 'Detail Use-Case Scenarios');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Architect', 'Develop Technical Vision');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Architect', 'Identify and Outline Requirements');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Architect', 'Manage Iteration');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Architect', 'Plan Iteration');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Architect', 'Plan Project');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Deployment Engineer', 'Develop Backout Plan');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Deployment Engineer', 'Package the Release');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Developer', 'Assess Results');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Developer', 'Create Test Cases');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Developer', 'Deliver Release Communications');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Developer', 'Detail System-Wide Requirements');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Developer', 'Detail Use-Case Scenarios');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Developer', 'Develop Product Documentation');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Developer', 'Envision the Architecture');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Developer', 'Execute Backout Plan');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Developer', 'Execute Deployment Plan');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Developer', 'Identify and Outline Requirements');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Developer', 'Implement Tests');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Developer', 'Manage Iteration');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Developer', 'Plan Deployment');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Developer', 'Plan Iteration');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Developer', 'Plan Project');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Developer', 'Refine the Architecture');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Developer', 'Verify Successful Deployment');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Product Owner', 'Develop Product Documentation');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Product Owner', 'Verify Successful Deployment');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Project Manager', 'Develop Technical Vision');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Project Manager', 'Envision the Architecture');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Project Manager', 'Refine the Architecture');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Stakeholder', 'Assess Results');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Stakeholder', 'Create Test Cases');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Stakeholder', 'Design the Solution');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Stakeholder', 'Detail System-Wide Requirements');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Stakeholder', 'Detail Use-Case Scenarios');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Stakeholder', 'Develop Technical Vision');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Stakeholder', 'Envision the Architecture');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Stakeholder', 'Identify and Outline Requirements');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Stakeholder', 'Implement Solution');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Stakeholder', 'Implement Tests');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Stakeholder', 'Manage Iteration');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Stakeholder', 'Plan Iteration');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Stakeholder', 'Plan Project');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Tester', 'Assess Results');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Tester', 'Design the Solution');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Tester', 'Detail System-Wide Requirements');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Tester', 'Detail Use-Case Scenarios');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Tester', 'Identify and Outline Requirements');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Tester', 'Implement Developer Tests');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Tester', 'Implement Solution');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Tester', 'Manage Iteration');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Tester', 'Plan Iteration');
INSERT INTO OPENUP."PUBLIC".ROLE_ADDITIONALLY_PERFORMS ("ROLE", TASK) 
	VALUES ('Tester', 'Plan Project');
/*ROLE_ADDITIONALLY_PERFORMS END*/

/*ROLE_MODIFIES BEGIN*/
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Analyst', 'Glossary');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Analyst', 'System-Wide Requirements');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Analyst', 'Use Case');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Analyst', 'Use-Case Model');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Analyst', 'Vision');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Analyst', 'Work Items List');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Any Role', 'Work Items List');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Architect', 'Architecture Notebook');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Course Developer', 'Training Materials');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Deployment Engineer', 'Deployment Plan');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Deployment Engineer', 'Infrastructure');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Deployment Engineer', 'Release Communications');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Developer', 'Backout Plan');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Developer', 'Build');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Developer', 'Design');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Developer', 'Developer Test');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Developer', 'Implementation');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Developer', 'Infrastructure');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Developer', 'Release');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Developer', 'Test Log');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Process Engineer', 'Project Defined Process');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Project Manager', 'Iteration Plan');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Project Manager', 'Project Plan');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Project Manager', 'Risk List');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Project Manager', 'Work Items List');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Technical Writer', 'Product Documentation');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Technical Writer', 'Support Documentation');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Technical Writer', 'User Documentation');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Tester', 'Test Case');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Tester', 'Test Log');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Tester', 'Test Script');
INSERT INTO OPENUP."PUBLIC".ROLE_MODIFIES ("ROLE", ARTIFACT) 
	VALUES ('Tool Specialist', 'Tools');
/*ROLE_MODIFIES END*/

/*ARTIFACT_FULFILLED_SLOTS BEGIN*/
INSERT INTO OPENUP."PUBLIC".ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) 
	VALUES ('Architecture Notebook', 'Technical Architecture');
INSERT INTO OPENUP."PUBLIC".ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) 
	VALUES ('Build', 'Technical Implementation');
INSERT INTO OPENUP."PUBLIC".ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) 
	VALUES ('Design', 'Technical Design');
INSERT INTO OPENUP."PUBLIC".ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) 
	VALUES ('Glossary', 'Technical Specification');
INSERT INTO OPENUP."PUBLIC".ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) 
	VALUES ('Implementation', 'Technical Implementation');
INSERT INTO OPENUP."PUBLIC".ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) 
	VALUES ('Iteration Plan', 'Project Status');
INSERT INTO OPENUP."PUBLIC".ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) 
	VALUES ('Iteration Plan', 'Project Work');
INSERT INTO OPENUP."PUBLIC".ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) 
	VALUES ('Project Plan', 'Project Definition and Scope');
INSERT INTO OPENUP."PUBLIC".ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) 
	VALUES ('Risk List', 'Project Risk');
INSERT INTO OPENUP."PUBLIC".ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) 
	VALUES ('System-Wide Requirements', 'Technical Specification');
INSERT INTO OPENUP."PUBLIC".ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) 
	VALUES ('Test Log', 'Technical Test Results');
INSERT INTO OPENUP."PUBLIC".ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) 
	VALUES ('Use Case', 'Technical Specification');
INSERT INTO OPENUP."PUBLIC".ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) 
	VALUES ('Use-Case Model', 'Technical Specification');
INSERT INTO OPENUP."PUBLIC".ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) 
	VALUES ('Vision', 'Technical Specification');
INSERT INTO OPENUP."PUBLIC".ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) 
	VALUES ('Work Items List', 'Project Status');
INSERT INTO OPENUP."PUBLIC".ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) 
	VALUES ('Work Items List', 'Project Work');
/*ARTIFACT_FULFILLED_SLOTS END*/

/*TASK_OUTPUTS BEGIN*/
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Assess Results', 'Iteration Plan');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Assess Results', 'Work Items List');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Create Test Cases', 'Test Case');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Deploy the Process', 'Project Defined Process');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Design the Solution', 'Design');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Detail System-Wide Requirements', 'Glossary');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Detail System-Wide Requirements', 'System-Wide Requirements');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Detail Use-Case Scenarios', 'Glossary');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Detail Use-Case Scenarios', 'Use Case');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Detail Use-Case Scenarios', 'Use-Case Model');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Develop Backout Plan', 'Backout Plan');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Develop Product Documentation', 'Product Documentation');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Develop Release Communications', 'Release Communications');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Develop Support Documentation', 'Support Documentation');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Develop Technical Vision', 'Glossary');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Develop Technical Vision', 'Vision');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Develop Training Materials', 'Training Materials');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Develop User Documentation', 'User Documentation');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Envision the Architecture', 'Architecture Notebook');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Identify and Outline Requirements', 'Glossary');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Identify and Outline Requirements', 'System-Wide Requirements');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Identify and Outline Requirements', 'Use Case');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Identify and Outline Requirements', 'Use-Case Model');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Identify and Outline Requirements', 'Work Items List');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Implement Developer Tests', 'Developer Test');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Implement Solution', 'Implementation');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Implement Tests', 'Test Script');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Install and Validate Infrastructure', 'Infrastructure');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Integrate and Create Build', 'Build');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Manage Iteration', 'Iteration Plan');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Manage Iteration', 'Risk List');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Manage Iteration', 'Work Items List');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Package the Release', 'Release');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Plan Deployment', 'Deployment Plan');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Plan Iteration', 'Iteration Plan');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Plan Iteration', 'Risk List');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Plan Iteration', 'Work Items List');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Plan Project', 'Project Plan');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Refine the Architecture', 'Architecture Notebook');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Request Change', 'Work Items List');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Run Developer Tests', 'Test Log');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Run Tests', 'Test Log');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Set Up Tools', 'Tools');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Tailor the Process', 'Project Defined Process');
INSERT INTO OPENUP."PUBLIC".TASK_OUTPUTS (TASK, ARTIFACT) 
	VALUES ('Verify Tool Configuration and Installation', 'Tools');
/*TASK_OUTPUTS END*/
/*EPF.DELIVERY_PROCESS BEGIN*/
INSERT INTO EPF_Delivery_Processes.DELIVERY_PROCESS (NAME, SUMMARY, ALTERNATIVES, DESCRIPTION, PURPOSE, RELATIONSHIPS, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE, TEAM_BREAKDOWN, WORK_BREAKDOWN, WORKFLOW, WORK_PRODUCT_BREAKDOWN) VALUES ('OpenUP Lifecycle', 'This delivery process defines an end-to-end software development lifecycle that supports the core principles of OpenUP. It is designed to support small, co-located teams in their daily activities.', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL, NULL, NULL, NULL);
/*EPF.DELIVERY_PROCESS END*/

/*EPF.PHASE BEGIN*/
INSERT INTO EPF_Delivery_Processes.PHASE (NAME, ALTERNATIVES, DESCRIPTION, PURPOSE, RELATIONSHIPS, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE, TEAM_BREAKDOWN, WORK_BREAKDOWN, WORKFLOW, WORK_PRODUCT_BREAKDOWN, PARENT_ACTIVITIES) VALUES ('Inception Phase', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL, NULL, NULL, NULL, 'OpenUP Lifecycle');
INSERT INTO EPF_Delivery_Processes.PHASE (NAME, ALTERNATIVES, DESCRIPTION, PURPOSE, RELATIONSHIPS, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE, TEAM_BREAKDOWN, WORK_BREAKDOWN, WORKFLOW, WORK_PRODUCT_BREAKDOWN, PARENT_ACTIVITIES) VALUES ('Elaboration Phase', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL, NULL, NULL, NULL, 'OpenUP Lifecycle');
INSERT INTO EPF_Delivery_Processes.PHASE (NAME, ALTERNATIVES, DESCRIPTION, PURPOSE, RELATIONSHIPS, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE, TEAM_BREAKDOWN, WORK_BREAKDOWN, WORKFLOW, WORK_PRODUCT_BREAKDOWN, PARENT_ACTIVITIES) VALUES ('Construction Phase', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL, NULL, NULL, NULL, 'OpenUP Lifecycle');
INSERT INTO EPF_Delivery_Processes.PHASE (NAME, ALTERNATIVES, DESCRIPTION, PURPOSE, RELATIONSHIPS, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE, TEAM_BREAKDOWN, WORK_BREAKDOWN, WORKFLOW, WORK_PRODUCT_BREAKDOWN, PARENT_ACTIVITIES) VALUES ('Transition Phase', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL, NULL, NULL, NULL, 'OpenUP Lifecycle');
/*EPF.PHASE END*/

/*EPF.CAPABILITY_PATTERN BEGIN*/
INSERT INTO EPF_Delivery_Processes.CAPABILITY_PATTERN (NAME, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE) VALUES ('Inception Phase Iteration', NULL, NULL, NULL, NULL, true, NULL);
INSERT INTO EPF_Delivery_Processes.CAPABILITY_PATTERN (NAME, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE) VALUES ('Initiate Project', NULL, NULL, NULL, NULL, true, NULL);
INSERT INTO EPF_Delivery_Processes.CAPABILITY_PATTERN (NAME, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE) VALUES ('Plan and Manage Iteration', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO EPF_Delivery_Processes.CAPABILITY_PATTERN (NAME, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE) VALUES ('Prepare Environment', NULL, NULL, NULL, NULL, true, NULL);
INSERT INTO EPF_Delivery_Processes.CAPABILITY_PATTERN (NAME, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE) VALUES ('Identify and Refine Requirements', NULL, NULL, NULL, NULL, true, NULL);
INSERT INTO EPF_Delivery_Processes.CAPABILITY_PATTERN (NAME, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE) VALUES ('Agree on Technical Approach', NULL, NULL, NULL, NULL, true, NULL);
INSERT INTO EPF_Delivery_Processes.CAPABILITY_PATTERN (NAME, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE) VALUES ('Develop the Architecture', NULL, NULL, NULL, NULL, true, NULL);
INSERT INTO EPF_Delivery_Processes.CAPABILITY_PATTERN (NAME, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE) VALUES ('Develop Solution Increment', NULL, NULL, NULL, NULL, true, NULL);
INSERT INTO EPF_Delivery_Processes.CAPABILITY_PATTERN (NAME, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE) VALUES ('Test Solution', NULL, NULL, NULL, NULL, true, NULL);
INSERT INTO EPF_Delivery_Processes.CAPABILITY_PATTERN (NAME, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE) VALUES ('Ongoing Tasks', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO EPF_Delivery_Processes.CAPABILITY_PATTERN (NAME, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE) VALUES ('Elaboration Phase Iteration', NULL, NULL, NULL, NULL, true, NULL);
INSERT INTO EPF_Delivery_Processes.CAPABILITY_PATTERN (NAME, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE) VALUES ('Construction Phase Iteration', NULL, NULL, NULL, NULL, true, NULL);
INSERT INTO EPF_Delivery_Processes.CAPABILITY_PATTERN (NAME, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE) VALUES ('Develop Product Documentation and Training', NULL, NULL, NULL, NULL, true, NULL);
INSERT INTO EPF_Delivery_Processes.CAPABILITY_PATTERN (NAME, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE) VALUES ('Finalize Product Documentation and Training', NULL, NULL, NULL, NULL, true, NULL);
INSERT INTO EPF_Delivery_Processes.CAPABILITY_PATTERN (NAME, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE) VALUES ('Prepare for Release', NULL, NULL, NULL, NULL, true, NULL);
INSERT INTO EPF_Delivery_Processes.CAPABILITY_PATTERN (NAME, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE) VALUES ('Provide Product Training', NULL, NULL, NULL, NULL, true, NULL);
INSERT INTO EPF_Delivery_Processes.CAPABILITY_PATTERN (NAME, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE) VALUES ('Deploy Release to Production', NULL, NULL, NULL, NULL, true, NULL);
INSERT INTO EPF_Delivery_Processes.CAPABILITY_PATTERN (NAME, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE) VALUES ('Transition Phase Iteration', NULL, NULL, NULL, NULL, true, NULL);
/*EPF.CAPABILITY_PATTERN END*/

/*EPF.ACTIVITY BEGIN*/
INSERT INTO EPF_Delivery_Processes.ACTIVITY (NAME) VALUES ('Initiate Project');
INSERT INTO EPF_Delivery_Processes.ACTIVITY (NAME) VALUES ('Plan and Manage Iteration');
INSERT INTO EPF_Delivery_Processes.ACTIVITY (NAME) VALUES ('Prepare Environment');
INSERT INTO EPF_Delivery_Processes.ACTIVITY (NAME) VALUES ('Identify and Refine Requirements');
INSERT INTO EPF_Delivery_Processes.ACTIVITY (NAME) VALUES ('Agree on Technical Approach');
INSERT INTO EPF_Delivery_Processes.ACTIVITY (NAME) VALUES ('Develop the Architecture');
INSERT INTO EPF_Delivery_Processes.ACTIVITY (NAME) VALUES ('Develop Solution Increment');
INSERT INTO EPF_Delivery_Processes.ACTIVITY (NAME) VALUES ('Test Solution');
INSERT INTO EPF_Delivery_Processes.ACTIVITY (NAME) VALUES ('Ongoing Tasks');
INSERT INTO EPF_Delivery_Processes.ACTIVITY (NAME) VALUES ('Develop Product Documentation and Training');
INSERT INTO EPF_Delivery_Processes.ACTIVITY (NAME) VALUES ('Finalize Product Documentation and Training');
INSERT INTO EPF_Delivery_Processes.ACTIVITY (NAME) VALUES ('Prepare for Release');
INSERT INTO EPF_Delivery_Processes.ACTIVITY (NAME) VALUES ('Provide Product Training');
INSERT INTO EPF_Delivery_Processes.ACTIVITY (NAME) VALUES ('Deploy Release to Production');
/*EPF.ACTIVITY END*/

/*EPF.ITERATION BEGIN*/
INSERT INTO EPF_Delivery_Processes.ITERATION (NAME, NUMBER, PARENT_ACTIVITIES) VALUES ('Inception Phase Iteration', NULL, 'Inception Phase');
INSERT INTO EPF_Delivery_Processes.ITERATION (NAME, NUMBER, PARENT_ACTIVITIES) VALUES ('Elaboration Phase Iteration', NULL, 'Elaboration Phase');
INSERT INTO EPF_Delivery_Processes.ITERATION (NAME, NUMBER, PARENT_ACTIVITIES) VALUES ('Construction Phase Iteration', NULL, 'Construction Phase');
INSERT INTO EPF_Delivery_Processes.ITERATION (NAME, NUMBER, PARENT_ACTIVITIES) VALUES ('Transition Phase Iteration', NULL, 'Transition Phase');
/*EPF.ITERATION END*/

/*EPF.ITERATION_ACTIVITIES BEGIN*/
INSERT INTO EPF_Delivery_Processes.ITERATION_ACTIVITIES (ITERATION, ACTIVITY) VALUES ('Construction Phase Iteration', 'Develop Product Documentation and Training');
INSERT INTO EPF_Delivery_Processes.ITERATION_ACTIVITIES (ITERATION, ACTIVITY) VALUES ('Construction Phase Iteration', 'Develop Solution Increment');
INSERT INTO EPF_Delivery_Processes.ITERATION_ACTIVITIES (ITERATION, ACTIVITY) VALUES ('Construction Phase Iteration', 'Identify and Refine Requirements');
INSERT INTO EPF_Delivery_Processes.ITERATION_ACTIVITIES (ITERATION, ACTIVITY) VALUES ('Construction Phase Iteration', 'Ongoing Tasks');
INSERT INTO EPF_Delivery_Processes.ITERATION_ACTIVITIES (ITERATION, ACTIVITY) VALUES ('Construction Phase Iteration', 'Plan and Manage Iteration');
INSERT INTO EPF_Delivery_Processes.ITERATION_ACTIVITIES (ITERATION, ACTIVITY) VALUES ('Construction Phase Iteration', 'Test Solution');
INSERT INTO EPF_Delivery_Processes.ITERATION_ACTIVITIES (ITERATION, ACTIVITY) VALUES ('Elaboration Phase Iteration', 'Develop Solution Increment');
INSERT INTO EPF_Delivery_Processes.ITERATION_ACTIVITIES (ITERATION, ACTIVITY) VALUES ('Elaboration Phase Iteration', 'Develop the Architecture');
INSERT INTO EPF_Delivery_Processes.ITERATION_ACTIVITIES (ITERATION, ACTIVITY) VALUES ('Elaboration Phase Iteration', 'Identify and Refine Requirements');
INSERT INTO EPF_Delivery_Processes.ITERATION_ACTIVITIES (ITERATION, ACTIVITY) VALUES ('Elaboration Phase Iteration', 'Ongoing Tasks');
INSERT INTO EPF_Delivery_Processes.ITERATION_ACTIVITIES (ITERATION, ACTIVITY) VALUES ('Elaboration Phase Iteration', 'Plan and Manage Iteration');
INSERT INTO EPF_Delivery_Processes.ITERATION_ACTIVITIES (ITERATION, ACTIVITY) VALUES ('Elaboration Phase Iteration', 'Test Solution');
INSERT INTO EPF_Delivery_Processes.ITERATION_ACTIVITIES (ITERATION, ACTIVITY) VALUES ('Inception Phase Iteration', 'Agree on Technical Approach');
INSERT INTO EPF_Delivery_Processes.ITERATION_ACTIVITIES (ITERATION, ACTIVITY) VALUES ('Inception Phase Iteration', 'Identify and Refine Requirements');
INSERT INTO EPF_Delivery_Processes.ITERATION_ACTIVITIES (ITERATION, ACTIVITY) VALUES ('Inception Phase Iteration', 'Initiate Project');
INSERT INTO EPF_Delivery_Processes.ITERATION_ACTIVITIES (ITERATION, ACTIVITY) VALUES ('Inception Phase Iteration', 'Plan and Manage Iteration');
INSERT INTO EPF_Delivery_Processes.ITERATION_ACTIVITIES (ITERATION, ACTIVITY) VALUES ('Transition Phase Iteration', 'Deploy Release to Production');
INSERT INTO EPF_Delivery_Processes.ITERATION_ACTIVITIES (ITERATION, ACTIVITY) VALUES ('Transition Phase Iteration', 'Develop Product Documentation and Training');
INSERT INTO EPF_Delivery_Processes.ITERATION_ACTIVITIES (ITERATION, ACTIVITY) VALUES ('Transition Phase Iteration', 'Develop Solution Increment');
INSERT INTO EPF_Delivery_Processes.ITERATION_ACTIVITIES (ITERATION, ACTIVITY) VALUES ('Transition Phase Iteration', 'Ongoing Tasks');
INSERT INTO EPF_Delivery_Processes.ITERATION_ACTIVITIES (ITERATION, ACTIVITY) VALUES ('Transition Phase Iteration', 'Plan and Manage Iteration');
INSERT INTO EPF_Delivery_Processes.ITERATION_ACTIVITIES (ITERATION, ACTIVITY) VALUES ('Transition Phase Iteration', 'Prepare for Release');
INSERT INTO EPF_Delivery_Processes.ITERATION_ACTIVITIES (ITERATION, ACTIVITY) VALUES ('Transition Phase Iteration', 'Provide Product Training');
INSERT INTO EPF_Delivery_Processes.ITERATION_ACTIVITIES (ITERATION, ACTIVITY) VALUES ('Transition Phase Iteration', 'Test Solution');
/*EPF.ITERATION_ACTIVITIES END*/

/*EPF.MILESTONE BEGIN*/
INSERT INTO EPF_Delivery_Processes.MILESTONE (NAME, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE, PREDECESSOR) VALUES ('Lifecycle Objectives Milestone', NULL, NULL, NULL, NULL, true, NULL, 'Inception Phase Iteration');
INSERT INTO EPF_Delivery_Processes.MILESTONE (NAME, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE, PREDECESSOR) VALUES ('Lifecycle Architecture Milestone', NULL, NULL, NULL, NULL, true, NULL, 'Elaboration Phase Iteration');
INSERT INTO EPF_Delivery_Processes.MILESTONE (NAME, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE, PREDECESSOR) VALUES ('Initial Operational Capability Milestone', NULL, NULL, NULL, NULL, true, NULL, 'Construction Phase Iteration');
INSERT INTO EPF_Delivery_Processes.MILESTONE (NAME, EVENT_DRIVEN, MULTIPLE_OCCURRENCES, ONGOING, OPTIONAL, PLANNED, REPEATABLE, PREDECESSOR) VALUES ('Product Release Milestone', NULL, NULL, NULL, NULL, true, NULL, 'Transition Phase Iteration');
/*EPF.MILESTONE END*/

/*EPF.ROLE_SET BEGIN*/
INSERT INTO EPF_Roles.ROLE_SET (NAME) VALUES ('Basic Roles');
INSERT INTO EPF_Roles.ROLE_SET (NAME) VALUES ('Deployment');
INSERT INTO EPF_Roles.ROLE_SET (NAME) VALUES ('Environment');
/*EPF.ROLE_SET END*/

/*EPF.EPF_ROLE BEGIN*/
INSERT INTO EPF_Roles.EPF_ROLE (NAME) VALUES ('Analyst');
INSERT INTO EPF_Roles.EPF_ROLE (NAME) VALUES ('Any_Role');
INSERT INTO EPF_Roles.EPF_ROLE (NAME) VALUES ('Architect');
INSERT INTO EPF_Roles.EPF_ROLE (NAME) VALUES ('Course_Developer');
INSERT INTO EPF_Roles.EPF_ROLE (NAME) VALUES ('Deployment_Engineer');
INSERT INTO EPF_Roles.EPF_ROLE (NAME) VALUES ('Deployment_Manager');
INSERT INTO EPF_Roles.EPF_ROLE (NAME) VALUES ('Developer');
INSERT INTO EPF_Roles.EPF_ROLE (NAME) VALUES ('Process_Engineer');
INSERT INTO EPF_Roles.EPF_ROLE (NAME) VALUES ('Product_Owner');
INSERT INTO EPF_Roles.EPF_ROLE (NAME) VALUES ('Project_Manager');
INSERT INTO EPF_Roles.EPF_ROLE (NAME) VALUES ('Stakeholder');
INSERT INTO EPF_Roles.EPF_ROLE (NAME) VALUES ('Technical_Writer');
INSERT INTO EPF_Roles.EPF_ROLE (NAME) VALUES ('Tester');
INSERT INTO EPF_Roles.EPF_ROLE (NAME) VALUES ('Tool_Specialist');
INSERT INTO EPF_Roles.EPF_ROLE (NAME) VALUES ('Trainer');
/*EPF.EPF_ROLE END*/

/*EPF.ROLES BEGIN*/
INSERT INTO EPF_Roles.ROLES (ROLE_SET, ROLE) VALUES ('Basic Roles', 'Analyst');
INSERT INTO EPF_Roles.ROLES (ROLE_SET, ROLE) VALUES ('Basic Roles', 'Any_Role');
INSERT INTO EPF_Roles.ROLES (ROLE_SET, ROLE) VALUES ('Basic Roles', 'Architect');
INSERT INTO EPF_Roles.ROLES (ROLE_SET, ROLE) VALUES ('Basic Roles', 'Developer');
INSERT INTO EPF_Roles.ROLES (ROLE_SET, ROLE) VALUES ('Basic Roles', 'Project_Manager');
INSERT INTO EPF_Roles.ROLES (ROLE_SET, ROLE) VALUES ('Basic Roles', 'Stakeholder');
INSERT INTO EPF_Roles.ROLES (ROLE_SET, ROLE) VALUES ('Basic Roles', 'Tester');
INSERT INTO EPF_Roles.ROLES (ROLE_SET, ROLE) VALUES ('Deployment', 'Course_Developer');
INSERT INTO EPF_Roles.ROLES (ROLE_SET, ROLE) VALUES ('Deployment', 'Deployment_Engineer');
INSERT INTO EPF_Roles.ROLES (ROLE_SET, ROLE) VALUES ('Deployment', 'Deployment_Manager');
INSERT INTO EPF_Roles.ROLES (ROLE_SET, ROLE) VALUES ('Deployment', 'Product_Owner');
INSERT INTO EPF_Roles.ROLES (ROLE_SET, ROLE) VALUES ('Deployment', 'Technical_Writer');
INSERT INTO EPF_Roles.ROLES (ROLE_SET, ROLE) VALUES ('Deployment', 'Trainer');
INSERT INTO EPF_Roles.ROLES (ROLE_SET, ROLE) VALUES ('Environment', 'Process_Engineer');
INSERT INTO EPF_Roles.ROLES (ROLE_SET, ROLE) VALUES ('Environment', 'Tool_Specialist');
/*EPF.ROLES END*/

/*EPF.EPF_DOMAIN BEGIN*/
INSERT INTO EPF_Work_Products.EPF_DOMAIN (NAME) VALUES ('Architecture');
INSERT INTO EPF_Work_Products.EPF_DOMAIN (NAME) VALUES ('Deployment');
INSERT INTO EPF_Work_Products.EPF_DOMAIN (NAME) VALUES ('Development');
INSERT INTO EPF_Work_Products.EPF_DOMAIN (NAME) VALUES ('Environment');
INSERT INTO EPF_Work_Products.EPF_DOMAIN (NAME) VALUES ('Project Management');
INSERT INTO EPF_Work_Products.EPF_DOMAIN (NAME) VALUES ('Requirements');
INSERT INTO EPF_Work_Products.EPF_DOMAIN (NAME) VALUES ('Test');
/*EPF.EPF_DOMAIN END*/

/*EPF.ARTIFACT BEGIN*/
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Architecture Notebook', 'Architect');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Product Documentation', 'Technical_Writer');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Support Documentation', 'Technical_Writer');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('User Documentation', 'Technical_Writer');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Training Materials', 'Course_Developer');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Backout Plan', 'Deployment_Engineer');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Deployment Plan', 'Deployment_Engineer');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Infrastructure', NULL);
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Release Communications', 'Deployment_Engineer');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Release Controls', 'Deployment_Manager');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Implementation', 'Developer');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Build', 'Developer');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Developer Test', 'Developer');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Design', 'Developer');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Project Defined Process', 'Process_Engineer');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Tools', 'Tool_Specialist');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Risk List', 'Project_Manager');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Work Items List', 'Project_Manager');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Iteration Plan', 'Project_Manager');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Project Plan', 'Project_Manager');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Glossary', 'Analyst');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Vision', 'Analyst');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('System-Wide Requirements', 'Analyst');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Use-Case Model', 'Analyst');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Use Case', 'Analyst');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Test Case', 'Tester');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Test Script', 'Tester');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Test Log', 'Tester');
INSERT INTO EPF_Work_Products.ARTIFACT (NAME, RESPONSIBLE) VALUES ('Release', 'Deployment_Engineer');
/*EPF.ARTIFACT END*/

/*EPF.DELIVERABLE BEGIN*/
INSERT INTO EPF_Work_Products.DELIVERABLE (NAME) VALUES ('Release');
/*EPF.DELIVERABLE END*/

/*EPF.WORK_PRODUCTS BEGIN*/
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Architecture', 'Architecture Notebook');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Deployment', 'Backout Plan');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Deployment', 'Deployment Plan');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Deployment', 'Infrastructure');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Deployment', 'Product Documentation');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Deployment', 'Release');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Deployment', 'Release Communications');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Deployment', 'Release Controls');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Deployment', 'Support Documentation');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Deployment', 'Training Materials');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Deployment', 'User Documentation');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Development', 'Build');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Development', 'Design');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Development', 'Developer Test');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Development', 'Implementation');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Environment', 'Project Defined Process');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Environment', 'Tools');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Project Management', 'Iteration Plan');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Project Management', 'Project Plan');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Project Management', 'Risk List');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Project Management', 'Work Items List');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Requirements', 'Glossary');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Requirements', 'System-Wide Requirements');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Requirements', 'Use Case');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Requirements', 'Use-Case Model');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Requirements', 'Vision');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Test', 'Test Case');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Test', 'Test Log');
INSERT INTO EPF_Work_Products.WORK_PRODUCTS (DOMAIN, ARTIFACT) VALUES ('Test', 'Test Script');
/*EPF.WORK_PRODUCTS END*/

/*EPF.WORK_PRODUCT_SLOT BEGIN*/
INSERT INTO EPF_Work_Products.WORK_PRODUCT_SLOT (NAME) VALUES ('Project Definition and Scope');
INSERT INTO EPF_Work_Products.WORK_PRODUCT_SLOT (NAME) VALUES ('Project Risk');
INSERT INTO EPF_Work_Products.WORK_PRODUCT_SLOT (NAME) VALUES ('Project Status');
INSERT INTO EPF_Work_Products.WORK_PRODUCT_SLOT (NAME) VALUES ('Project Work');
INSERT INTO EPF_Work_Products.WORK_PRODUCT_SLOT (NAME) VALUES ('Technical Architecture');
INSERT INTO EPF_Work_Products.WORK_PRODUCT_SLOT (NAME) VALUES ('Technical Design');
INSERT INTO EPF_Work_Products.WORK_PRODUCT_SLOT (NAME) VALUES ('Technical Implementation');
INSERT INTO EPF_Work_Products.WORK_PRODUCT_SLOT (NAME) VALUES ('Technical Specification');
INSERT INTO EPF_Work_Products.WORK_PRODUCT_SLOT (NAME) VALUES ('Technical Test Results');
/*EPF.WORK_PRODUCT_SLOT END*/

/*EPF.DISCIPLINE BEGIN*/
INSERT INTO EPF_Tasks.DISCIPLINE (NAME) VALUES ('Architecture');
INSERT INTO EPF_Tasks.DISCIPLINE (NAME) VALUES ('Deployment');
INSERT INTO EPF_Tasks.DISCIPLINE (NAME) VALUES ('Development');
INSERT INTO EPF_Tasks.DISCIPLINE (NAME) VALUES ('Environment');
INSERT INTO EPF_Tasks.DISCIPLINE (NAME) VALUES ('Project Management');
INSERT INTO EPF_Tasks.DISCIPLINE (NAME) VALUES ('Requirements');
INSERT INTO EPF_Tasks.DISCIPLINE (NAME) VALUES ('Test');
/*EPF.DISCIPLINE END*/

/*EPF.TASK BEGIN*/
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Refine the Architecture', 'Architect');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Envision the Architecture', 'Architect');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Develop Product Documentation', 'Technical_Writer');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Develop User Documentation', 'Technical_Writer');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Develop Support Documentation', 'Technical_Writer');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Deliver end user Training', 'Trainer');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Deliver Support Training', 'Trainer');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Develop Training Materials', 'Course_Developer');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Deliver Release Communications', 'Deployment_Engineer');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Execute Backout Plan', 'Deployment_Engineer');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Execute Deployment Plan', 'Deployment_Engineer');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Package the Release', 'Developer');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Verify Successful Deployment', 'Deployment_Engineer');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Develop Backout Plan', 'Developer');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Develop Release Communications', 'Deployment_Engineer');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Install and Validate Infrastructure', 'Deployment_Engineer');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Plan Deployment', 'Deployment_Engineer');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Review and Conform to Release Controls', 'Developer');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Implement Developer Tests', 'Developer');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Implement Solution', 'Developer');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Run Developer Tests', 'Developer');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Integrate and Create Build', 'Developer');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Design the Solution', 'Developer');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Deploy the Process', 'Process_Engineer');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Tailor the Process', 'Process_Engineer');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Set Up Tools', 'Tool_Specialist');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Verify Tool Configuration and Installation', 'Tool_Specialist');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Assess Results', 'Project_Manager');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Manage Iteration', 'Project_Manager');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Plan Iteration', 'Project_Manager');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Plan Project', 'Project_Manager');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Request Change', 'Any_Role');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Identify and Outline Requirements', 'Analyst');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Detail Use-Case Scenarios', 'Analyst');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Detail System-Wide Requirements', 'Analyst');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Develop Technical Vision', 'Analyst');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Create Test Cases', 'Tester');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Implement Tests', 'Tester');
INSERT INTO EPF_Tasks.TASK (NAME, PRIMARY_PERFORMER) VALUES ('Run Tests', 'Tester');
/*EPF.TASK END*/

/*EPF.TASKS BEGIN*/
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Architecture', 'Envision the Architecture');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Architecture', 'Refine the Architecture');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Deployment', 'Deliver Release Communications');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Deployment', 'Deliver Support Training');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Deployment', 'Deliver end user Training');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Deployment', 'Develop Backout Plan');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Deployment', 'Develop Product Documentation');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Deployment', 'Develop Release Communications');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Deployment', 'Develop Support Documentation');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Deployment', 'Develop Training Materials');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Deployment', 'Develop User Documentation');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Deployment', 'Execute Backout Plan');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Deployment', 'Execute Deployment Plan');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Deployment', 'Install and Validate Infrastructure');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Deployment', 'Package the Release');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Deployment', 'Plan Deployment');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Deployment', 'Review and Conform to Release Controls');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Deployment', 'Verify Successful Deployment');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Development', 'Design the Solution');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Development', 'Implement Developer Tests');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Development', 'Implement Solution');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Development', 'Integrate and Create Build');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Development', 'Run Developer Tests');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Environment', 'Deploy the Process');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Environment', 'Set Up Tools');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Environment', 'Tailor the Process');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Environment', 'Verify Tool Configuration and Installation');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Project Management', 'Assess Results');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Project Management', 'Manage Iteration');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Project Management', 'Plan Iteration');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Project Management', 'Plan Project');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Project Management', 'Request Change');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Requirements', 'Detail System-Wide Requirements');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Requirements', 'Detail Use-Case Scenarios');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Requirements', 'Develop Technical Vision');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Requirements', 'Identify and Outline Requirements');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Test', 'Create Test Cases');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Test', 'Implement Tests');
INSERT INTO EPF_Tasks.TASKS (DISCIPLINE, TASK) VALUES ('Test', 'Run Tests');
/*EPF.TASKS END*/

/*EPF.ROLE_ADDITIONALLY_PERFORMS BEGIN*/
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Analyst', 'Assess Results');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Analyst', 'Create Test Cases');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Analyst', 'Design the Solution');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Analyst', 'Envision the Architecture');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Analyst', 'Implement Tests');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Analyst', 'Manage Iteration');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Analyst', 'Plan Iteration');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Analyst', 'Plan Project');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Architect', 'Assess Results');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Architect', 'Design the Solution');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Architect', 'Detail System-Wide Requirements');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Architect', 'Detail Use-Case Scenarios');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Architect', 'Develop Technical Vision');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Architect', 'Identify and Outline Requirements');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Architect', 'Manage Iteration');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Architect', 'Plan Iteration');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Architect', 'Plan Project');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Deployment_Engineer', 'Develop Backout Plan');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Deployment_Engineer', 'Package the Release');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Developer', 'Assess Results');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Developer', 'Create Test Cases');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Developer', 'Deliver Release Communications');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Developer', 'Detail System-Wide Requirements');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Developer', 'Detail Use-Case Scenarios');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Developer', 'Develop Product Documentation');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Developer', 'Envision the Architecture');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Developer', 'Execute Backout Plan');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Developer', 'Execute Deployment Plan');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Developer', 'Identify and Outline Requirements');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Developer', 'Implement Tests');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Developer', 'Manage Iteration');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Developer', 'Plan Deployment');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Developer', 'Plan Iteration');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Developer', 'Plan Project');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Developer', 'Refine the Architecture');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Developer', 'Verify Successful Deployment');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Product_Owner', 'Develop Product Documentation');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Product_Owner', 'Verify Successful Deployment');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Project_Manager', 'Develop Technical Vision');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Project_Manager', 'Envision the Architecture');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Project_Manager', 'Refine the Architecture');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Stakeholder', 'Assess Results');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Stakeholder', 'Create Test Cases');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Stakeholder', 'Design the Solution');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Stakeholder', 'Detail System-Wide Requirements');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Stakeholder', 'Detail Use-Case Scenarios');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Stakeholder', 'Develop Technical Vision');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Stakeholder', 'Envision the Architecture');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Stakeholder', 'Identify and Outline Requirements');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Stakeholder', 'Implement Solution');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Stakeholder', 'Implement Tests');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Stakeholder', 'Manage Iteration');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Stakeholder', 'Plan Iteration');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Stakeholder', 'Plan Project');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Tester', 'Assess Results');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Tester', 'Design the Solution');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Tester', 'Detail System-Wide Requirements');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Tester', 'Detail Use-Case Scenarios');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Tester', 'Identify and Outline Requirements');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Tester', 'Implement Developer Tests');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Tester', 'Implement Solution');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Tester', 'Manage Iteration');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Tester', 'Plan Iteration');
INSERT INTO EPF_Tasks.ROLE_ADDITIONALLY_PERFORMS (ROLE, TASK) VALUES ('Tester', 'Plan Project');
/*EPF.ROLE_ADDITIONALLY_PERFORMS END*/

/*EPF.ROLE_MODIFIES BEGIN*/
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Analyst', 'Glossary');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Analyst', 'System-Wide Requirements');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Analyst', 'Use Case');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Analyst', 'Use-Case Model');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Analyst', 'Vision');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Analyst', 'Work Items List');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Any_Role', 'Work Items List');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Architect', 'Architecture Notebook');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Course_Developer', 'Training Materials');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Deployment_Engineer', 'Deployment Plan');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Deployment_Engineer', 'Infrastructure');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Deployment_Engineer', 'Release Communications');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Developer', 'Backout Plan');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Developer', 'Build');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Developer', 'Design');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Developer', 'Developer Test');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Developer', 'Implementation');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Developer', 'Infrastructure');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Developer', 'Release');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Developer', 'Test Log');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Process_Engineer', 'Project Defined Process');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Project_Manager', 'Iteration Plan');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Project_Manager', 'Project Plan');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Project_Manager', 'Risk List');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Project_Manager', 'Work Items List');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Technical_Writer', 'Product Documentation');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Technical_Writer', 'Support Documentation');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Technical_Writer', 'User Documentation');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Tester', 'Test Case');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Tester', 'Test Log');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Tester', 'Test Script');
INSERT INTO EPF_Work_Products.ROLE_MODIFIES (ROLE, ARTIFACT) VALUES ('Tool_Specialist', 'Tools');
/*EPF.ROLE_MODIFIES END*/

/*EPF.ARTIFACT_FULFILLED_SLOTS BEGIN*/
INSERT INTO EPF_Work_Products.ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) VALUES ('Architecture Notebook', 'Technical Architecture');
INSERT INTO EPF_Work_Products.ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) VALUES ('Build', 'Technical Implementation');
INSERT INTO EPF_Work_Products.ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) VALUES ('Design', 'Technical Design');
INSERT INTO EPF_Work_Products.ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) VALUES ('Glossary', 'Technical Specification');
INSERT INTO EPF_Work_Products.ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) VALUES ('Implementation', 'Technical Implementation');
INSERT INTO EPF_Work_Products.ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) VALUES ('Iteration Plan', 'Project Status');
INSERT INTO EPF_Work_Products.ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) VALUES ('Iteration Plan', 'Project Work');
INSERT INTO EPF_Work_Products.ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) VALUES ('Project Plan', 'Project Definition and Scope');
INSERT INTO EPF_Work_Products.ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) VALUES ('Risk List', 'Project Risk');
INSERT INTO EPF_Work_Products.ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) VALUES ('System-Wide Requirements', 'Technical Specification');
INSERT INTO EPF_Work_Products.ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) VALUES ('Test Log', 'Technical Test Results');
INSERT INTO EPF_Work_Products.ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) VALUES ('Use Case', 'Technical Specification');
INSERT INTO EPF_Work_Products.ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) VALUES ('Use-Case Model', 'Technical Specification');
INSERT INTO EPF_Work_Products.ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) VALUES ('Vision', 'Technical Specification');
INSERT INTO EPF_Work_Products.ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) VALUES ('Work Items List', 'Project Status');
INSERT INTO EPF_Work_Products.ARTIFACT_FULFILLED_SLOTS (ARTIFACT, WORK_PRODUCT_SLOT) VALUES ('Work Items List', 'Project Work');
/*EPF.ARTIFACT_FULFILLED_SLOTS END*/

/*EPF.TASK_INPUTS_MANDATORY BEGIN*/
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Assess Results', 'Iteration Plan');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Assess Results', 'Work Items List');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Deliver Release Communications', 'Release Communications');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Deliver Support Training', 'Support Documentation');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Deliver Support Training', 'Training Materials');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Deliver end user Training', 'Training Materials');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Deliver end user Training', 'User Documentation');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Deploy the Process', 'Project Defined Process');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Detail System-Wide Requirements', 'System-Wide Requirements');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Detail Use-Case Scenarios', 'Use Case');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Develop Release Communications', 'Deployment Plan');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Execute Backout Plan', 'Backout Plan');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Execute Backout Plan', 'Release');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Execute Deployment Plan', 'Deployment Plan');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Execute Deployment Plan', 'Infrastructure');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Execute Deployment Plan', 'Release');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Implement Tests', 'Test Case');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Install and Validate Infrastructure', 'Deployment Plan');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Integrate and Create Build', 'Test Script');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Manage Iteration', 'Iteration Plan');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Manage Iteration', 'Risk List');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Manage Iteration', 'Work Items List');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Package the Release', 'Deployment Plan');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Package the Release', 'Release Controls');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Plan Iteration', 'Work Items List');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Refine the Architecture', 'Architecture Notebook');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Review and Conform to Release Controls', 'Release Controls');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Run Developer Tests', 'Developer Test');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Run Tests', 'Test Script');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Set Up Tools', 'Tools');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Tailor the Process', 'Project Defined Process');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Verify Successful Deployment', 'Release');
INSERT INTO EPF_Tasks.TASK_INPUTS_MANDATORY (TASK, ARTIFACT) VALUES ('Verify Tool Configuration and Installation', 'Tools');
/*EPF.TASK_INPUTS_MANDATORY END*/

/*EPF.TASK_INPUTS_OPTIONAL BEGIN*/
INSERT INTO EPF_Tasks.TASK_INPUTS_OPTIONAL (TASK, ARTIFACT) VALUES ('Create Test Cases', 'Test Case');
INSERT INTO EPF_Tasks.TASK_INPUTS_OPTIONAL (TASK, ARTIFACT) VALUES ('Deliver Release Communications', 'Release');
INSERT INTO EPF_Tasks.TASK_INPUTS_OPTIONAL (TASK, ARTIFACT) VALUES ('Deliver Support Training', 'Product Documentation');
INSERT INTO EPF_Tasks.TASK_INPUTS_OPTIONAL (TASK, ARTIFACT) VALUES ('Deliver Support Training', 'User Documentation');
INSERT INTO EPF_Tasks.TASK_INPUTS_OPTIONAL (TASK, ARTIFACT) VALUES ('Deliver end user Training', 'Product Documentation');
INSERT INTO EPF_Tasks.TASK_INPUTS_OPTIONAL (TASK, ARTIFACT) VALUES ('Design the Solution', 'Design');
INSERT INTO EPF_Tasks.TASK_INPUTS_OPTIONAL (TASK, ARTIFACT) VALUES ('Detail System-Wide Requirements', 'Use Case');
INSERT INTO EPF_Tasks.TASK_INPUTS_OPTIONAL (TASK, ARTIFACT) VALUES ('Develop Backout Plan', 'Deployment Plan');
INSERT INTO EPF_Tasks.TASK_INPUTS_OPTIONAL (TASK, ARTIFACT) VALUES ('Develop Release Communications', 'Release Controls');
INSERT INTO EPF_Tasks.TASK_INPUTS_OPTIONAL (TASK, ARTIFACT) VALUES ('Develop Support Documentation', 'Product Documentation');
INSERT INTO EPF_Tasks.TASK_INPUTS_OPTIONAL (TASK, ARTIFACT) VALUES ('Develop Support Documentation', 'User Documentation');
INSERT INTO EPF_Tasks.TASK_INPUTS_OPTIONAL (TASK, ARTIFACT) VALUES ('Develop Technical Vision', 'Vision');
INSERT INTO EPF_Tasks.TASK_INPUTS_OPTIONAL (TASK, ARTIFACT) VALUES ('Develop Training Materials', 'Product Documentation');
INSERT INTO EPF_Tasks.TASK_INPUTS_OPTIONAL (TASK, ARTIFACT) VALUES ('Develop Training Materials', 'Support Documentation');
INSERT INTO EPF_Tasks.TASK_INPUTS_OPTIONAL (TASK, ARTIFACT) VALUES ('Develop Training Materials', 'User Documentation');
INSERT INTO EPF_Tasks.TASK_INPUTS_OPTIONAL (TASK, ARTIFACT) VALUES ('Develop User Documentation', 'Product Documentation');
INSERT INTO EPF_Tasks.TASK_INPUTS_OPTIONAL (TASK, ARTIFACT) VALUES ('Envision the Architecture', 'Architecture Notebook');
INSERT INTO EPF_Tasks.TASK_INPUTS_OPTIONAL (TASK, ARTIFACT) VALUES ('Execute Deployment Plan', 'Release Controls');
INSERT INTO EPF_Tasks.TASK_INPUTS_OPTIONAL (TASK, ARTIFACT) VALUES ('Implement Solution', 'Developer Test');
INSERT INTO EPF_Tasks.TASK_INPUTS_OPTIONAL (TASK, ARTIFACT) VALUES ('Implement Tests', 'Test Script');
INSERT INTO EPF_Tasks.TASK_INPUTS_OPTIONAL (TASK, ARTIFACT) VALUES ('Install and Validate Infrastructure', 'Release Controls');
INSERT INTO EPF_Tasks.TASK_INPUTS_OPTIONAL (TASK, ARTIFACT) VALUES ('Plan Iteration', 'Iteration Plan');
INSERT INTO EPF_Tasks.TASK_INPUTS_OPTIONAL (TASK, ARTIFACT) VALUES ('Plan Iteration', 'Risk List');
INSERT INTO EPF_Tasks.TASK_INPUTS_OPTIONAL (TASK, ARTIFACT) VALUES ('Verify Successful Deployment', 'Deployment Plan');
/*EPF.TASK_INPUTS_OPTIONAL END*/

/*EPF.TASK_OUTPUTS BEGIN*/
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Assess Results', 'Iteration Plan');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Assess Results', 'Work Items List');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Create Test Cases', 'Test Case');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Deploy the Process', 'Project Defined Process');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Design the Solution', 'Design');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Detail System-Wide Requirements', 'Glossary');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Detail System-Wide Requirements', 'System-Wide Requirements');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Detail Use-Case Scenarios', 'Glossary');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Detail Use-Case Scenarios', 'Use Case');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Detail Use-Case Scenarios', 'Use-Case Model');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Develop Backout Plan', 'Backout Plan');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Develop Product Documentation', 'Product Documentation');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Develop Release Communications', 'Release Communications');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Develop Support Documentation', 'Support Documentation');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Develop Technical Vision', 'Glossary');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Develop Technical Vision', 'Vision');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Develop Training Materials', 'Training Materials');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Develop User Documentation', 'User Documentation');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Envision the Architecture', 'Architecture Notebook');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Identify and Outline Requirements', 'Glossary');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Identify and Outline Requirements', 'System-Wide Requirements');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Identify and Outline Requirements', 'Use Case');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Identify and Outline Requirements', 'Use-Case Model');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Identify and Outline Requirements', 'Work Items List');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Implement Developer Tests', 'Developer Test');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Implement Solution', 'Implementation');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Implement Tests', 'Test Script');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Install and Validate Infrastructure', 'Infrastructure');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Integrate and Create Build', 'Build');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Manage Iteration', 'Iteration Plan');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Manage Iteration', 'Risk List');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Manage Iteration', 'Work Items List');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Package the Release', 'Release');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Plan Deployment', 'Deployment Plan');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Plan Iteration', 'Iteration Plan');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Plan Iteration', 'Risk List');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Plan Iteration', 'Work Items List');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Plan Project', 'Project Plan');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Refine the Architecture', 'Architecture Notebook');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Request Change', 'Work Items List');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Run Developer Tests', 'Test Log');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Run Tests', 'Test Log');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Set Up Tools', 'Tools');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Tailor the Process', 'Project Defined Process');
INSERT INTO EPF_Tasks.TASK_OUTPUTS (TASK, ARTIFACT) VALUES ('Verify Tool Configuration and Installation', 'Tools');
/*EPF.TASK_OUTPUTS END*/

/*EPF.PRINCIPAL BEGIN*/

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

/*OPENUP SCHEMA END*/
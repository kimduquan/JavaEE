/*Basic Roles BEGIN*/
GRANT SELECT ON SCHEMA EPF_Delivery_Processes TO Analyst;
GRANT SELECT ON SCHEMA EPF_Delivery_Processes TO Any_Role;
GRANT SELECT ON SCHEMA EPF_Delivery_Processes TO Architect;
GRANT SELECT ON SCHEMA EPF_Delivery_Processes TO Developer;
GRANT SELECT ON SCHEMA EPF_Delivery_Processes TO Project_Manager;
GRANT SELECT ON SCHEMA EPF_Delivery_Processes TO Stakeholder;
GRANT SELECT ON SCHEMA EPF_Delivery_Processes TO Tester;

GRANT SELECT ON SCHEMA EPF_Roles TO Analyst;
GRANT SELECT ON SCHEMA EPF_Roles TO Any_Role;
GRANT SELECT ON SCHEMA EPF_Roles TO Architect;
GRANT SELECT ON SCHEMA EPF_Roles TO Developer;
GRANT SELECT ON SCHEMA EPF_Roles TO Project_Manager;
GRANT SELECT ON SCHEMA EPF_Roles TO Stakeholder;
GRANT SELECT ON SCHEMA EPF_Roles TO Tester;

GRANT SELECT ON SCHEMA EPF_Tasks TO Analyst;
GRANT SELECT ON SCHEMA EPF_Tasks TO Any_Role;
GRANT SELECT ON SCHEMA EPF_Tasks TO Architect;
GRANT SELECT ON SCHEMA EPF_Tasks TO Developer;
GRANT SELECT ON SCHEMA EPF_Tasks TO Project_Manager;
GRANT SELECT ON SCHEMA EPF_Tasks TO Stakeholder;
GRANT SELECT ON SCHEMA EPF_Tasks TO Tester;

GRANT SELECT ON SCHEMA EPF_Work_Products TO Analyst;
GRANT SELECT ON SCHEMA EPF_Work_Products TO Any_Role;
GRANT SELECT ON SCHEMA EPF_Work_Products TO Architect;
GRANT SELECT ON SCHEMA EPF_Work_Products TO Developer;
GRANT SELECT ON SCHEMA EPF_Work_Products TO Project_Manager;
GRANT SELECT ON SCHEMA EPF_Work_Products TO Stakeholder;
GRANT SELECT ON SCHEMA EPF_Work_Products TO Tester;

GRANT SELECT ON SCHEMA OPENUP TO Analyst;
GRANT SELECT ON SCHEMA OPENUP TO Any_Role;
GRANT SELECT ON SCHEMA OPENUP TO Architect;
GRANT SELECT ON SCHEMA OPENUP TO Developer;
GRANT SELECT ON SCHEMA OPENUP TO Project_Manager;
GRANT SELECT ON SCHEMA OPENUP TO Stakeholder;
GRANT SELECT ON SCHEMA OPENUP TO Tester;

GRANT SELECT ON SCHEMA FT TO Analyst;
GRANT SELECT ON SCHEMA FT TO Any_Role;
GRANT SELECT ON SCHEMA FT TO Architect;
GRANT SELECT ON SCHEMA FT TO Developer;
GRANT SELECT ON SCHEMA FT TO Project_Manager;
GRANT SELECT ON SCHEMA FT TO Stakeholder;
GRANT SELECT ON SCHEMA FT TO Tester;

REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM Analyst;
REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM Any_Role;
REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM Architect;
REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM Developer;
REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM Project_Manager;
REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM Stakeholder;
REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM Tester;

REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM analyst1;
REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM any_role1;
REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM architect1;
REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM developer1;
REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM project_manager1;
REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM stakeholder1;
REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM tester1;
/*Basic Roles END*/

/*Deployment BEGIN*/
GRANT SELECT ON SCHEMA EPF_Delivery_Processes TO Course_Developer;
GRANT SELECT ON SCHEMA EPF_Delivery_Processes TO Deployment_Engineer;
GRANT SELECT ON SCHEMA EPF_Delivery_Processes TO Deployment_Manager;
GRANT SELECT ON SCHEMA EPF_Delivery_Processes TO Product_Owner;
GRANT SELECT ON SCHEMA EPF_Delivery_Processes TO Technical_Writer;
GRANT SELECT ON SCHEMA EPF_Delivery_Processes TO Trainer;

GRANT SELECT ON SCHEMA EPF_Roles TO Course_Developer;
GRANT SELECT ON SCHEMA EPF_Roles TO Deployment_Engineer;
GRANT SELECT ON SCHEMA EPF_Roles TO Deployment_Manager;
GRANT SELECT ON SCHEMA EPF_Roles TO Product_Owner;
GRANT SELECT ON SCHEMA EPF_Roles TO Technical_Writer;
GRANT SELECT ON SCHEMA EPF_Roles TO Trainer;

GRANT SELECT ON SCHEMA EPF_Tasks TO Course_Developer;
GRANT SELECT ON SCHEMA EPF_Tasks TO Deployment_Engineer;
GRANT SELECT ON SCHEMA EPF_Tasks TO Deployment_Manager;
GRANT SELECT ON SCHEMA EPF_Tasks TO Product_Owner;
GRANT SELECT ON SCHEMA EPF_Tasks TO Technical_Writer;
GRANT SELECT ON SCHEMA EPF_Tasks TO Trainer;

GRANT SELECT ON SCHEMA EPF_Work_Products TO Course_Developer;
GRANT SELECT ON SCHEMA EPF_Work_Products TO Deployment_Engineer;
GRANT SELECT ON SCHEMA EPF_Work_Products TO Deployment_Manager;
GRANT SELECT ON SCHEMA EPF_Work_Products TO Product_Owner;
GRANT SELECT ON SCHEMA EPF_Work_Products TO Technical_Writer;
GRANT SELECT ON SCHEMA EPF_Work_Products TO Trainer;

GRANT SELECT ON SCHEMA OPENUP TO Course_Developer;
GRANT SELECT ON SCHEMA OPENUP TO Deployment_Engineer;
GRANT SELECT ON SCHEMA OPENUP TO Deployment_Manager;
GRANT SELECT ON SCHEMA OPENUP TO Product_Owner;
GRANT SELECT ON SCHEMA OPENUP TO Technical_Writer;
GRANT SELECT ON SCHEMA OPENUP TO Trainer;

GRANT SELECT ON SCHEMA FT TO Course_Developer;
GRANT SELECT ON SCHEMA FT TO Deployment_Engineer;
GRANT SELECT ON SCHEMA FT TO Deployment_Manager;
GRANT SELECT ON SCHEMA FT TO Product_Owner;
GRANT SELECT ON SCHEMA FT TO Technical_Writer;
GRANT SELECT ON SCHEMA FT TO Trainer;

REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM Course_Developer;
REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM Deployment_Engineer;
REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM Deployment_Manager;
REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM Product_Owner;
REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM Technical_Writer;
REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM Trainer;

REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM course_developer1;
REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM deployment_engineer1;
REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM deployment_manager1;
REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM product_owner1;
REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM technical_writer1;
REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM trainer1;
/*Deployment END*/

/*Environment BEGIN*/
GRANT SELECT ON SCHEMA EPF_Delivery_Processes TO Process_Engineer;
GRANT SELECT ON SCHEMA EPF_Delivery_Processes TO Tool_Specialist;

GRANT SELECT ON SCHEMA EPF_Roles TO Process_Engineer;
GRANT SELECT ON SCHEMA EPF_Roles TO Tool_Specialist;

GRANT SELECT ON SCHEMA EPF_Tasks TO Process_Engineer;
GRANT SELECT ON SCHEMA EPF_Tasks TO Tool_Specialist;

GRANT SELECT ON SCHEMA EPF_Work_Products TO Process_Engineer;
GRANT SELECT ON SCHEMA EPF_Work_Products TO Tool_Specialist;

GRANT SELECT ON SCHEMA OPENUP TO Process_Engineer;
GRANT SELECT ON SCHEMA OPENUP TO Tool_Specialist;

GRANT SELECT ON SCHEMA FT TO Process_Engineer;
GRANT SELECT ON SCHEMA FT TO Tool_Specialist;

REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM Process_Engineer;
REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM Tool_Specialist;

REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM process_engineer1;
REVOKE ALL ON SCHEMA INFORMATION_SCHEMA FROM tool_specialist1;
/*Environment END*/
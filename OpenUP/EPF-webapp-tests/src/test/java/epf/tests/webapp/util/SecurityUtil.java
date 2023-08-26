package epf.tests.webapp.util;

import java.util.Map.Entry;
import java.util.AbstractMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author FOXCONN
 */
public class SecurityUtil {
    
	private static final Queue<Entry<String, String>> credentials = new ConcurrentLinkedQueue<>();
    
    public static Entry<String, String> peekCredential() throws Exception{
    	if(credentials.isEmpty()) {
    		/*Basic Roles BEGIN*/
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("analyst1@openup.org", "Analyst1*"));
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("any_role1@openup.org", "Any_Role1*"));
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("architect1@openup.org", "Architect1*"));
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("developer1@openup.org", "Developer1*"));
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("project_manager1@openup.org", "Project_Manager1*"));
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("stakeholder1@openup.org", "Stakeholder1*"));
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("tester1@openup.org", "Tester1*"));
    		/*Basic Roles END*/

    		/*Deployment BEGIN*/
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("course_developer1@openup.org", "Course_Developer1*"));
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("deployment_engineer1@openup.org", "Deployment_Engineer1*"));
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("deployment_manager1@openup.org", "Deployment_Manager1*"));
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("product_owner1@openup.org", "Product_Owner1*"));
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("technical_writer1@openup.org", "Technical_Writer1*"));
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("trainer1@openup.org", "Trainer1*"));
    		/*Deployment END*/

    		/*Environment BEGIN*/
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("process_engineer1@openup.org", "Process_Engineer1*"));
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("tool_specialist1@openup.org", "Tool_Specialist1*"));
    		/*Environment END*/
    	}
    	Entry<String, String> credential = credentials.poll();
    	credentials.add(credential);
    	System.out.println(String.format("SecurityUtil.peekCredential(\"%s\")", credential.getKey(), credential.getValue()));
    	return credential;
    }
    
    static Entry<String, String> getAdminCredential(){
    	System.out.println(String.format("SecurityUtil.getAdminCredential()"));
    	return new AbstractMap.SimpleImmutableEntry<>("test", "123456");
    }
}

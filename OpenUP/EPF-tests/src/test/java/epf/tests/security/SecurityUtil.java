package epf.tests.security;

import epf.client.gateway.GatewayUtil;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.security.client.Security;
import epf.security.schema.Token;
import epf.tests.client.ClientUtil;
import epf.tests.health.HealthUtil;
import java.util.Map.Entry;
import java.time.Duration;
import java.util.AbstractMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author FOXCONN
 */
public class SecurityUtil {
    
	private static final Queue<Entry<String, String>> credentials = new ConcurrentLinkedQueue<>();
    
	public static String login(String username, String password) throws Exception {
		try(Client client = ClientUtil.newClient(GatewayUtil.get(Naming.SECURITY))){
    		return Security.login(client, username, password, GatewayUtil.get("tests").toURL());
    	}
    }
    
    public static void logOut(String token) throws Exception {
    	try(Client client = ClientUtil.newClient(GatewayUtil.get(Naming.SECURITY))){
    		client.authorization(token);
    		String userName = Security.logOut(client);
        	System.out.println(String.format("SecurityUtil.logOut(\"%s\")", userName));
    	}
    }
    
    public static Token auth(String token) throws Exception {
    	try(Client client = ClientUtil.newClient(GatewayUtil.get(Naming.SECURITY))){
    		client.authorization(token);
    		return Security.authenticate(client);
    	}
    }
    
    public static String revoke(String token, Duration duration) throws Exception {
    	try(Client client = ClientUtil.newClient(GatewayUtil.get(Naming.SECURITY))){
    		client.authorization(token);
    		return Security.revoke(client, duration);
    	}
    }
    
    public static Entry<String, String> peekCredential() throws Exception{
    	if(credentials.isEmpty()) {
    		/*Basic Roles BEGIN*/
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("analyst1@openup", "Analyst1*"));
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("any_role1@openup", "Any_Role1*"));
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("architect1@openup", "Architect1*"));
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("developer1@openup", "Developer1*"));
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("project_manager1@openup", "Project_Manager1*"));
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("stakeholder1@openup", "Stakeholder1*"));
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("tester1@openup", "Tester1*"));
    		/*Basic Roles END*/

    		/*Deployment BEGIN*/
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("course_developer1@openup", "Course_Developer1*"));
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("deployment_engineer1@openup", "Deployment_Engineer1*"));
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("deployment_manager1@openup", "Deployment_Manager1*"));
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("product_owner1@openup", "Product_Owner1*"));
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("technical_writer1@openup", "Technical_Writer1*"));
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("trainer1@openup", "Trainer1*"));
    		/*Deployment END*/

    		/*Environment BEGIN*/
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("process_engineer1@openup", "Process_Engineer1*"));
    		credentials.add(new AbstractMap.SimpleImmutableEntry<>("tool_specialist1@openup", "Tool_Specialist1*"));
    		/*Environment END*/
    		
    		HealthUtil.isReady();
    	}
    	Entry<String, String> credential = credentials.poll();
    	credentials.add(credential);
    	System.out.println(String.format("SecurityUtil.peekCredential(\"%s\")", credential.getKey(), credential.getValue()));
    	return credential;
    }
    
    public static String login() throws Exception {
    	Entry<String, String> credential = peekCredential();
    	return login(credential.getKey(), credential.getValue());
    }
    
    static Entry<String, String> getAdminCredential(){
    	System.out.println(String.format("SecurityUtil.getAdminCredential()"));
    	return new AbstractMap.SimpleImmutableEntry<>("test", "123456");
    }
}

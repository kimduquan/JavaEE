package epf.tests.webapp.util;

import java.util.Map.Entry;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import epf.naming.Naming;
import epf.security.schema.Token;

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
	
	public static String securityLogin(ProcessBuilder builder, Path in, Path out, String username, String password) throws Exception {
		builder = ShellUtil.command(builder, Naming.SECURITY, "login", "-u", username, "-p");
		Process process = ShellUtil.waitFor(builder, in, password);
		List<String> lines = ShellUtil.getOutput(out);
		lines.stream().forEach(System.out::println);
		process.destroyForcibly();
		return lines.get(lines.size() - 1);
	}
	
	public static Token securityAuth(ProcessBuilder builder, String token, Path out) throws Exception {
		builder = ShellUtil.command(builder, Naming.SECURITY, "auth", "-t", token);
		Process process = ShellUtil.waitFor(builder);
		List<String> lines = ShellUtil.getOutput(out);
		lines.stream().forEach(System.out::println);
		process.destroyForcibly();
		try(Jsonb jsonb = JsonbBuilder.create()){
			return jsonb.fromJson(lines.get(lines.size() - 1), Token.class);
		}
	}
	
	public static String securityLogout(ProcessBuilder builder, String tokenID, Path out) throws Exception {
		builder = ShellUtil.command(builder, Naming.SECURITY, "logout", "-tid", tokenID);
		Process process = ShellUtil.waitFor(builder);
		List<String> lines = ShellUtil.getOutput(out);
		lines.stream().forEach(System.out::println);
		process.destroyForcibly();
		return lines.get(lines.size() - 1);
	}
}

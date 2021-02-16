/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.runtime;

import java.util.ArrayList;
import java.util.List;
import openup.TestUtil;
import openup.client.security.PasswordHash;
import openup.schema.OpenUP;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import epf.client.RestClient;
import epf.client.runtime.Processes;
import epf.client.security.Security;

/**
 *
 * @author FOXCONN
 */
public class ProcessesTest {
    
	private static RestClient restClient;
	private static String token;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
    	restClient = TestUtil.newRestClient(TestUtil.url().toURI());
    	Security security = restClient.build(Security.class);
        token = security.login(
        		OpenUP.Schema, 
        		"any_role1", 
        		PasswordHash.hash(
        				"any_role1", 
        				"any_role".toCharArray()
        				), 
        		TestUtil.url()
        		);
        restClient.authorization(token);
    }
    
    @AfterClass
    public static void afterClass() throws Exception{
    	restClient.authorization(token).build(Security.class).logOut(OpenUP.Schema);
    }
    
    @Test
    public void testStartOK() throws Exception {
        List<String> command = new ArrayList<>();
        command.add("git");
        command.add("pull");
        long pid = restClient.build(Processes.class).start(command, "D:\\projects\\JavaEE2\\OpenUP");
        Assert.assertNotEquals("Process", 0, pid);
    }
}

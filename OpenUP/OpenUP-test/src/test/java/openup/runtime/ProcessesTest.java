/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.runtime;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import openup.TestUtil;
import openup.schema.OpenUP;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import epf.client.runtime.Processes;
import epf.util.client.Client;

/**
 *
 * @author FOXCONN
 */
public class ProcessesTest {
    
	private static String token;
	private static URI processesUrl;
	private Client client;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
    	token = TestUtil.login(OpenUP.Schema, "any_role1", "any_role");
        processesUrl = new URI(TestUtil.gateway_url().toString() + "runtime/process");
    }
    
    @AfterClass
    public static void afterClass() throws Exception{
    	TestUtil.logOut(OpenUP.Schema, token);
    }
    
    @After
    public void after() throws Exception {
    	client.close();
    }
    
    @Before
    public void before() {
    	client = TestUtil.newClient(processesUrl);
    	client.authorization(token);
    }
    
    @Test
    public void testStartOK() throws Exception {
        List<String> command = new ArrayList<>();
        command.add("git");
        command.add("pull");
        long pid = Processes.start(client, command, "D:\\projects\\JavaEE2\\OpenUP");
        Assert.assertNotEquals("Process", 0, pid);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.runtime;

import java.util.ArrayList;
import java.util.List;
import openup.TestUtil;
import openup.client.runtime.Processes;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author FOXCONN
 */
public class ProcessesTest {
    
    private static String token;
    private static Processes processes;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
        TestUtil.beforeClass();
        token = TestUtil.login("any_role1", "any_role");
        processes = TestUtil.rest().build(Processes.class);
    }
    
    @AfterClass
    public static void afterClass() throws Exception{
        processes.stop();
        TestUtil.logout(token);
        TestUtil.afterClass();
    }
    
    @Test
    public void testStartOK() throws Exception {
        List<String> command = new ArrayList<>();
        command.add("git");
        command.add("pull");
        long pid = processes.start(command, "D:\\projects\\JavaEE2\\OpenUP");
        
        Assert.assertNotEquals("Process", 0, pid);
    }
}

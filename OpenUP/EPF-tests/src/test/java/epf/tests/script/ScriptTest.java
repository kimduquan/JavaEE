package epf.tests.script;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import epf.client.script.Script;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.tests.client.ClientUtil;
import epf.tests.security.SecurityUtil;
import epf.client.gateway.GatewayUtil;

/**
 * @author PC
 *
 */
public class ScriptTest {
	
	@Rule
    public TestName testName = new TestName();
	
	static URI scriptUrl;
	static String token;
	Client client;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		scriptUrl = GatewayUtil.get(Naming.SCRIPT);
		token = SecurityUtil.login();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		SecurityUtil.logOut(token);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		client = ClientUtil.newClient(scriptUrl);
		client.authorization(token.toCharArray());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		client.close();
		ClientUtil.afterClass();
	}

	@Test
	@Ignore
	public void testEvalOk() throws IOException {
		String script = "return \'this is a test\';";
		try(ByteArrayInputStream input = new ByteArrayInputStream(script.getBytes())){
			String result = Script.eval(client, "JavaScript", input, String.class);
			Assert.assertEquals("this is a test", result);
		}
	}
}

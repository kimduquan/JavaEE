package epf.security.util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CredentialUtilTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNewCredential() {
		
	}

	@Test
	public void testEncryptPassword() throws Exception {
		String pass = CredentialUtil.encryptPassword("test", "123456");
		//Assert.assertEquals("F6A70B9817E710C08A6D7EA9A671940B81CCB5CE88CD6FF4BF035E48A50425FED771FB0E45786C0DDF960CFD02D8B99D89ECE449FB773FF43CC5992B20C0B7C8164B9F55CD7D085DE254DF2C2DA05FFCA6752666385FD6446771EE77D7976D53", pass);
	}

}

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
		Assert.assertEquals("9B61AB6196F933E6D9CE43FD04936E3F4A265AD66398F656E4EA46A4E6BE50C307F02FCEF9BF3A4C9500454DDABC7719321D75ED82778C7C1DEFC6873E7605EDF250F9BB337F4E29C89E70E31A389CD8", pass);
	}

}

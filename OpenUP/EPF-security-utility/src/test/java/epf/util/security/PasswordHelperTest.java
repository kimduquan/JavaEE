/**
 * 
 */
package epf.util.security;

import org.junit.Assert;
import org.junit.Test;
import epf.security.util.PasswordUtil;

/**
 * @author PC
 *
 */
public class PasswordHelperTest {

	/**
	 * Test method for {@link epf.security.util.PasswordUtil#hash(java.lang.String, char[])}.
	 * @throws Exception 
	 */
	@Test
	public void testHash() throws Exception {
		String hash = PasswordUtil.hash("any_role1", "Any_Role1*".toCharArray());
		Assert.assertEquals("Any_Role1*", hash);
		
		hash = PasswordUtil.hash("");
		Assert.assertEquals("", hash);
	}

	/**
	 * Test method for {@link epf.security.util.PasswordUtil#hash(java.lang.String, char[])}.
	 * @throws Exception 
	 */
	@Test(expected = NullPointerException.class)
	public void testHash_NullUserName() throws Exception {
		PasswordUtil.hash(null, "any_role1".toCharArray());
		
	}
	
	/**
	 * Test method for {@link epf.security.util.PasswordUtil#hash(java.lang.String, char[])}.
	 * @throws Exception 
	 */
	@Test(expected = NullPointerException.class)
	public void testHash_NullPassword() throws Exception {
		PasswordUtil.hash("any_role", null);
		
	}
}

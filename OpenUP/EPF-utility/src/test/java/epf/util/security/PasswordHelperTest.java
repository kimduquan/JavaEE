/**
 * 
 */
package epf.util.security;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author PC
 *
 */
public class PasswordHelperTest {

	/**
	 * Test method for {@link epf.util.security.PasswordHelper#hash(java.lang.String, char[])}.
	 * @throws Exception 
	 */
	@Test
	public void testHash() throws Exception {
		String hash = PasswordHelper.hash("any_role1", "any_role".toCharArray());
		Assert.assertEquals("0A9733E63BE2ACE9B1E69E112ED433D3FE46A0C1B74F46F114F30BECE34FC884C991BD82A7851DF52B5FDADE637F2EAEF7D02740156C9F523C9BCFB013C03190E8AC49CE418D86618371E3EC52D0466D", hash);
		
		hash = PasswordHelper.hash("");
		Assert.assertEquals("07FD60CBFDD5E8824B4595F03834257CFC3B5BD12CB3DE229C0EA0E4B6E62CEBCAC02A33ADFAB2EEE3681D3109A41CB0D4FF7B6EC73B5A1C66E01AD7A1C8CAF2CA0C0980AAEDAB1505C3BCFC5A46E70F", hash);
	}

	/**
	 * Test method for {@link epf.util.security.PasswordHelper#hash(java.lang.String, char[])}.
	 * @throws Exception 
	 */
	@Test(expected = NullPointerException.class)
	public void testHash_NullUserName() throws Exception {
		PasswordHelper.hash(null, "any_role1".toCharArray());
		
	}
	
	/**
	 * Test method for {@link epf.util.security.PasswordHelper#hash(java.lang.String, char[])}.
	 * @throws Exception 
	 */
	@Test(expected = NullPointerException.class)
	public void testHash_NullPassword() throws Exception {
		PasswordHelper.hash("any_role", null);
		
	}
}

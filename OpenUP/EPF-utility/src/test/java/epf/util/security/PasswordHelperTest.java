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
		String hash = PasswordHelper.hash("any_role", "any_role1".toCharArray());
		Assert.assertEquals("98816125A8CB0DCDD8A394EB8B6E80B8968AAA655F1F40337D07446390F2F11B7CE5B582C89AD8E1A82549E7577A24D26EA88044CE137534D2F27D0CE5E76FF02AD4E67CB4F3A79CA7DE77A6C02F2154", hash);
		
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

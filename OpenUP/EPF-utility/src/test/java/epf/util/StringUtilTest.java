package epf.util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author PC
 *
 */
public class StringUtilTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link epf.util.StringUtil#randomString(java.lang.String)}.
	 * @throws InterruptedException 
	 */
	@Test
	public void testRandomString() throws InterruptedException {
		String random1 = StringUtil.randomString("random1");
		Thread.sleep(40);
		String random2 = StringUtil.randomString("random2");
		Assert.assertNotEquals(random1, random2);
	}

	/**
	 * Test method for {@link epf.util.StringUtil#toShortString(int)}.
	 */
	@Test
	public void testToShortString() {
		int number = 12345;
		String shortString = StringUtil.toShortString(number);
		Assert.assertEquals("dnh", shortString);
	}

	/**
	 * Test method for {@link epf.util.StringUtil#fromShortString(java.lang.String)}.
	 */
	@Test
	public void testFromShortString() {
		String shortString = "dnh";
		long number = StringUtil.fromShortString(shortString);
		Assert.assertEquals(12345, number);
	}
	
	/**
	 * Test method for {@link epf.util.StringUtil#join(java.lang.String[])}.
	 */
	@Test
	public void testJoin() {
		String str1 = "abc";
		String str2 = "def";
		String actual = StringUtil.join(str1, str2);
		Assert.assertEquals("abc\0def", actual);
	}

	/**
	 * Test method for {@link epf.util.StringUtil#split(java.lang.String)}.
	 */
	@Test
	public void testSplit() {
		String str = "abc\0def";
		String[] actual = StringUtil.split(str);
		Assert.assertArrayEquals(new String[] {"abc", "def"}, actual);
	}
}

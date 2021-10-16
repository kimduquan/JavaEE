/**
 * 
 */
package epf.util.logging;

import java.lang.reflect.Method;
import java.util.logging.Logger;
import javax.interceptor.InvocationContext;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author PC
 *
 */
public class LoggingTest {
	
	String mockMethod(String arg) throws Exception{
		if("".equals(arg)) {
			throw new Exception();
		}
		return arg;
	}

	/**
	 * Test method for {@link epf.util.logging.LogManager#logMethodEntry(javax.interceptor.InvocationContext)}.
	 * @throws Exception 
	 */
	@Test
	public void testLogMethodEntry() throws NoSuchMethodException, Exception {
		InvocationContext mockContext = Mockito.mock(InvocationContext.class);
		Method method = LoggingTest.class.getDeclaredMethod("mockMethod", String.class);
		Mockito.when(mockContext.getMethod()).thenReturn(method);
		Mockito.when(mockContext.getParameters()).thenReturn(new String[] {"arg"});
		Mockito.when(mockContext.proceed()).thenReturn("arg");
		LogManager logManager = new LogManager();
		Object obj = logManager.logMethodEntry(mockContext);
		Assert.assertEquals("arg", obj);
	}
	
	/**
	 * Test method for {@link epf.util.logging.LogManager#logMethodEntry(javax.interceptor.InvocationContext)}.
	 * @throws Exception 
	 */
	@Test
	public void testLogMethodEntry_ThrowException() throws NoSuchMethodException, Exception {
		InvocationContext mockContext = Mockito.mock(InvocationContext.class);
		Method method = LoggingTest.class.getDeclaredMethod("mockMethod", String.class);
		Mockito.when(mockContext.getMethod()).thenReturn(method);
		Mockito.when(mockContext.getParameters()).thenReturn(new String[] {""});
		Mockito.when(mockContext.proceed()).thenThrow(Exception.class);
		LogManager logManager = new LogManager();
		Object obj = logManager.logMethodEntry(mockContext);
		Assert.assertNull(obj);
	}

	/**
	 * Test method for {@link epf.util.logging.LogManager#getLogger(java.lang.String)}.
	 */
	@Test
	public void testGetLogger() {
		Logger logger = LogManager.getLogger(LoggingTest.class.getName());
		Assert.assertNotNull(logger);
		logger = LogManager.getLogger("");
		Assert.assertNotNull(logger);
		logger = LogManager.getLogger("invalid");
		Assert.assertNotNull(logger);
	}

	/**
	 * Test method for {@link epf.util.logging.LogManager#getLogger(java.lang.String)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testGetLogger_NullName() {
		LogManager.getLogger(null);
	}
	
	/**
	 * Test method for {@link epf.util.logging.LogManager#config(java.lang.Class)}.
	 */
	@Test
	public void testConfig() {
		LogManager.config(getClass());
		String configFile = System.getProperty("java.util.logging.config.file");
		Assert.assertNotNull("System.property.java.util.logging.config.file", configFile);
	}
}

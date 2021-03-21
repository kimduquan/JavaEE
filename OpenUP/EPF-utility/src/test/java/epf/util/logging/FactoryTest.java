/**
 * 
 */
package epf.util.logging;

import java.lang.reflect.Member;
import java.util.logging.Logger;
import javax.enterprise.inject.spi.InjectionPoint;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author PC
 *
 */
public class FactoryTest {
	
	Logger mockMember;

	/**
	 * Test method for {@link epf.util.logging.Factory#getLogger(javax.enterprise.inject.spi.InjectionPoint)}.
	 * @throws Exception
	 */
	@Test
	public void testGetLogger() throws NoSuchFieldException, Exception {
		InjectionPoint inject = Mockito.mock(InjectionPoint.class);
		Member mockMember = FactoryTest.class.getDeclaredField("mockMember");
		Mockito.when(inject.getMember()).thenReturn(mockMember);
		Logger logger = new Factory().getLogger(inject);
		Assert.assertEquals("Logger.name", FactoryTest.class.getName(), logger.getName());
	}

}

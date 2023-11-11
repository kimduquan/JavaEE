package epf.util;

import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
public class ClassUtilTest {

	@Test
	public void testNewInstances() throws Exception {
		List<String> strings = ClassUtil.newInstances(Arrays.asList(String.class));
		Assert.assertEquals("list.size", 1, strings.size());
	}
}

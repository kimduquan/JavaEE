package epf.util;

import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class UUIDUtilTest {

	@Test
	public void testFromString() {
		UUID uuid = UUIDUtil.fromString(UUID.randomUUID().toString());
		Assert.assertNotNull("UUID", uuid);
		uuid = UUIDUtil.fromString("aaa");
		Assert.assertNull("UUID", uuid);
	}
}

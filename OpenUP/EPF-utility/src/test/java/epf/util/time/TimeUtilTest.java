package epf.util.time;

import java.time.Duration;
import java.time.Instant;
import org.junit.Test;
import org.junit.Assert;

public class TimeUtilTest {

	@Test
	public void testParse() {
		Duration duration = TimeUtil.parse("P10DT2H30M", (Duration)null);
		Assert.assertNotNull("Duration", duration);
		duration = TimeUtil.parse("aaa", (Duration)null);
		Assert.assertNull("Duration", duration);
		Instant instant = TimeUtil.parse("2007-03-01T13:00:00Z", (Instant)null);
		Assert.assertNotNull("Instant", instant);
		instant = TimeUtil.parse("aaa", (Instant)null);
		Assert.assertNull("Instant", instant);
	}
}

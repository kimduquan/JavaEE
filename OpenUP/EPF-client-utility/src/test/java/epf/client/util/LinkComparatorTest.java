package epf.client.util;

import javax.ws.rs.core.Link;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author PC
 *
 */
public class LinkComparatorTest {

	@Test
	public void testCompare_OneIsNull_OtherIsNull_ReturnZero() {
		Link one = null;
		Link other = null;
		int compare = new LinkComparator().compare(one, other);
		Assert.assertEquals(compare, 0);
	}
	
	@Test
	public void testCompare_OneIsNull_OtherIsNotNull_ReturnNegative() {
		Link one = null;
		Link other = Mockito.mock(Link.class);
		int compare = new LinkComparator().compare(one, other);
		Assert.assertEquals(compare, -1);
	}
	
	@Test
	public void testCompare_OneIsNotNull_OtherIsNull_ReturnPositive() {
		Link one = Mockito.mock(Link.class);
		Link other = null;
		int compare = new LinkComparator().compare(one, other);
		Assert.assertEquals(compare, 1);
	}
	
	@Test
	public void testCompare_OneEqualsOther_ReturnZero() {
		Link one = Mockito.mock(Link.class);
		Mockito.when(one.getTitle()).thenReturn("a");
		Link other = Mockito.mock(Link.class);
		Mockito.when(other.getTitle()).thenReturn("a");
		int compare = new LinkComparator().compare(one, other);
		Assert.assertEquals(compare, 0);
	}
	
	@Test
	public void testCompare_OneLagerOther_ReturnPositive() {
		Link one = Mockito.mock(Link.class);
		Mockito.when(one.getTitle()).thenReturn("b");
		Link other = Mockito.mock(Link.class);
		Mockito.when(other.getTitle()).thenReturn("a");
		int compare = new LinkComparator().compare(one, other);
		Assert.assertEquals(compare, 1);
	}
	
	@Test
	public void testCompare_OneSmallerOther_ReturnNegative() {
		Link one = Mockito.mock(Link.class);
		Mockito.when(one.getTitle()).thenReturn("a");
		Link other = Mockito.mock(Link.class);
		Mockito.when(other.getTitle()).thenReturn("b");
		int compare = new LinkComparator().compare(one, other);
		Assert.assertEquals(compare, -1);
	}
}

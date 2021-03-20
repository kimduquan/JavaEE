/**
 * 
 */
package epf.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author PC
 *
 */
public class VarTest {

	/**
	 * Test method for {@link epf.util.Var#Var()}.
	 */
	@Test
	public void testVar() {
		Var<String> var = new Var<>();
		Assert.assertNull("var", var.get());
	}

	/**
	 * Test method for {@link epf.util.Var#Var(java.lang.Object)}.
	 */
	@Test
	public void testVarT() {
		Var<String> var = new Var<>("var");
		Assert.assertNotNull("var", var.get());
		Assert.assertEquals("var", "var", var.get());
	}

	/**
	 * Test method for {@link epf.util.Var#set(java.lang.Object)}.
	 */
	@Test
	public void testSetT() {
		Var<String> var = new Var<>("");
		var.set("var");
		Assert.assertEquals("var", "var", var.get());
		var.set("var1");
		Assert.assertEquals("var", "var1", var.get());
	}

	/**
	 * Test method for {@link epf.util.Var#get()}.
	 */
	@Test
	public void testGet() {
		Var<String> var = new Var<>();
		var.set("");
		Assert.assertEquals("var", "", var.get());
		var.set("var");
		Assert.assertEquals("var", "var", var.get());
	}

	/**
	 * Test method for {@link epf.util.Var#set(java.util.function.Function)}.
	 */
	@Test
	public void testSetFunctionOfTT() {
		Var<String> var = new Var<>();
		var.set(v -> {
			Assert.assertNull("var", v);
			return "var";
		});
		Assert.assertEquals("var", "var", var.get());
		var.set(v -> {
			Assert.assertEquals("var", "var", v);
			return "";
		});
		Assert.assertEquals("var", "", var.get());
	}

	/**
	 * Test method for {@link epf.util.Var#get(java.util.function.Consumer)}.
	 */
	@Test
	public void testGetConsumerOfT() {
		Var<String> var = new Var<>();
		var.get(v -> {
			Assert.assertNull("var", v);
		});
		var.set("var");
		var.get(v -> {
			Assert.assertEquals("var", "var", v);
		});
		var.set("");
		var.get(v -> {
			Assert.assertEquals("var", "", v);
		});
	}

}

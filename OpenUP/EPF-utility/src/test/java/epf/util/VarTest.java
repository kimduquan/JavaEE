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
		final Var<String> var = new Var<>();
		Assert.assertNull(var.get().orElse(null));
	}

	/**
	 * Test method for {@link epf.util.Var#Var(java.lang.Object)}.
	 */
	@Test
	public void testVarT() {
		final Var<String> var = new Var<>("var");
		Assert.assertTrue("Var", var.get().isPresent());
		Assert.assertEquals("var", var.get().get());
	}

	/**
	 * Test method for {@link epf.util.Var#set(java.lang.Object)}.
	 */
	@Test
	public void testSetT() {
		final Var<String> var = new Var<>("");
		var.set("var");
		Assert.assertEquals("var", var.get().get());
		var.set("var1");
		Assert.assertEquals("var1", var.get().get());
	}

	/**
	 * Test method for {@link epf.util.Var#get()}.
	 */
	@Test
	public void testGet() {
		final Var<String> var = new Var<>();
		var.set("");
		Assert.assertEquals("", var.get().get());
		var.set("var");
		Assert.assertEquals("var", var.get().get());
	}

	/**
	 * Test method for {@link epf.util.Var#set(java.util.function.Function)}.
	 */
	@Test
	public void testSetFunctionOfTT() {
		final Var<String> var = new Var<>();
		var.set(v -> {
			Assert.assertNull(v);
			return "var";
		});
		Assert.assertEquals("var", var.get().get());
		var.set(v -> {
			Assert.assertEquals("var", v);
			return "";
		});
		Assert.assertEquals("", var.get().get());
	}

	/**
	 * Test method for {@link epf.util.Var#get(java.util.function.Consumer)}.
	 */
	@Test
	public void testGetConsumerOfT() {
		final Var<String> var = new Var<>();
		var.get(v -> {
			Assert.assertNull(v);
		});
		var.set("var");
		var.get(v -> {
			Assert.assertEquals("var", v);
		});
		var.set("");
		var.get(v -> {
			Assert.assertEquals("", v);
		});
	}

}

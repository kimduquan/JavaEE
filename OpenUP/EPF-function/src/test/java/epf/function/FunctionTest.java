package epf.function;

import java.util.stream.Stream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import epf.util.Var;

public class FunctionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRunOk() {
		Var<Integer> varCount = new Var<>();
		Function func = new Function(
				Stream.of(
						() -> { varCount.set(Integer.valueOf(0));},
						() -> { varCount.set(count -> count + 1);},
						() -> { varCount.set(count -> count + 5);},
						new Return<>(),
						() -> { varCount.set(count -> count + 10);}
						)
				);
		func.run();
		Assert.assertNotNull("varCount", varCount.get());
		Assert.assertEquals("varCount", 6, varCount.get().intValue());
	}
	
	@Test
	public void testRunOk_Empty() {
		Function func = new Function(Stream.of());
		func.run();
	}
	
	@Test
	public void testRunOk_NestedFunction() {
		Var<Integer> varCount = new Var<>();
		Var<Integer> varNestedCount = new Var<>();
		Function func = new Function(
				Stream.of(
						() -> { varCount.set(Integer.valueOf(0));},
						() -> { varCount.set(count -> count + 1);},
						() -> { varCount.set(count -> count + 5);},
						new Function(
								Stream.of(
										() -> { varNestedCount.set(Integer.valueOf(0));},
										() -> { varNestedCount.set(count -> count + 2);},
										() -> { varNestedCount.set(count -> count + 10);},
										new Return<>(),
										() -> { varNestedCount.set(count -> count + 20);}
										)
								),
						new Return<>(),
						() -> { varCount.set(count -> count + 10);}
						)
				);
		func.run();
		Assert.assertNotNull("varCount", varCount.get());
		Assert.assertEquals("varCount", 6, varCount.get().intValue());
		Assert.assertNotNull("varNestedCount", varNestedCount.get());
		Assert.assertEquals("varNestedCount", 12, varNestedCount.get().intValue());
	}
	
	@Test
	public void testRunOk_NestedMultiFunctions() {
		Var<Integer> varCount = new Var<>();
		Var<Integer> varNestedCount = new Var<>();
		Var<Integer> varNestedCount2 = new Var<>();
		Function func = new Function(
				Stream.of(
						new Function(
								Stream.of(
										() -> { varNestedCount.set(Integer.valueOf(0));},
										() -> { varNestedCount.set(count -> count + 2);},
										() -> { varNestedCount.set(count -> count + 10);},
										new Return<>(),
										() -> { varNestedCount.set(count -> count + 20);}
										)
								),
						() -> { varCount.set(Integer.valueOf(0));},
						() -> { varCount.set(count -> count + 1);},
						() -> { varCount.set(count -> count + 5);},
						new Function(
								Stream.of(
										() -> { varNestedCount2.set(Integer.valueOf(0));},
										() -> { varNestedCount2.set(count -> count + 3);},
										() -> { varNestedCount2.set(count -> count + 15);},
										new Return<>(),
										() -> { varNestedCount2.set(count -> count + 30);}
										)
								),
						new Return<>(),
						() -> { varCount.set(count -> count + 10);}
						)
				);
		func.run();
		Assert.assertNotNull("varCount", varCount.get());
		Assert.assertEquals("varCount", 6, varCount.get().intValue());
		Assert.assertNotNull("varNestedCount", varNestedCount.get());
		Assert.assertEquals("varNestedCount", 12, varNestedCount.get().intValue());
		Assert.assertNotNull("varNestedCount2", varNestedCount2.get());
		Assert.assertEquals("varNestedCount2", 18, varNestedCount2.get().intValue());
	}

	@Test
	public void testRunOk_NestedMultiLevelFunctions() {
		Var<Integer> varCount = new Var<>();
		Var<Integer> varNestedCount = new Var<>();
		Var<Integer> varNestedCount2 = new Var<>();
		Var<Integer> varNestedCount3 = new Var<>();
		Function func = new Function(
				Stream.of(
						new Function(
								Stream.of(
										() -> { varNestedCount.set(Integer.valueOf(0));},
										() -> { varNestedCount.set(count -> count + 2);},
										() -> { varNestedCount.set(count -> count + 10);},
										new Return<>(),
										() -> { varNestedCount.set(count -> count + 20);}
										)
								),
						() -> { varCount.set(Integer.valueOf(0));},
						() -> { varCount.set(count -> count + 1);},
						() -> { varCount.set(count -> count + 5);},
						new Function(
								Stream.of(
										new Function(
												Stream.of(
														() -> { varNestedCount3.set(Integer.valueOf(0));},
														() -> { varNestedCount3.set(count -> count + 4);},
														() -> { varNestedCount3.set(count -> count + 20);},
														new Return<>(),
														() -> { varNestedCount3.set(count -> count + 40);}
														)
												),
										() -> { varNestedCount2.set(Integer.valueOf(0));},
										() -> { varNestedCount2.set(count -> count + 3);},
										() -> { varNestedCount2.set(count -> count + 15);},
										new Return<>(),
										() -> { varNestedCount2.set(count -> count + 30);}
										)
								),
						new Return<>(),
						() -> { varCount.set(count -> count + 10);}
						)
				);
		func.run();
		Assert.assertNotNull("varCount", varCount.get());
		Assert.assertEquals("varCount", 6, varCount.get().intValue());
		Assert.assertNotNull("varNestedCount", varNestedCount.get());
		Assert.assertEquals("varNestedCount", 12, varNestedCount.get().intValue());
		Assert.assertNotNull("varNestedCount2", varNestedCount2.get());
		Assert.assertEquals("varNestedCount2", 18, varNestedCount2.get().intValue());
		Assert.assertNotNull("varNestedCount3", varNestedCount3.get());
		Assert.assertEquals("varNestedCount3", 24, varNestedCount3.get().intValue());
	}
	
	@Test
	public void testRunOk_ThrowException(){
		Var<Integer> varCount = new Var<>();
		Function func = new Function(
				Stream.of(
						() -> { varCount.set(Integer.valueOf(0));},
						() -> { varCount.set(count -> count + 1);},
						() -> { varCount.set(count -> count + 5);},
						new epf.function.Assert(() -> 1 == 2),
						() -> { varCount.set(count -> count + 10);}
						)
				);
		func.run();
		Assert.assertNotNull("varCount", varCount.get());
		Assert.assertEquals("varCount", 6, varCount.get().intValue());
		Assert.assertNotNull("Function.exception", func.getException());
	}
}

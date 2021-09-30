package epf.util.zip;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ZipUtilTest {
	
	static Path testDirectory;
	Path tempDirectory;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testDirectory = Files.createTempDirectory("ZipUtilTest");
		testDirectory.toFile().deleteOnExit();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		testDirectory.toFile().delete();
	}

	@Before
	public void setUp() throws Exception {
		tempDirectory = Files.createTempDirectory(testDirectory, "");
		tempDirectory.toFile().deleteOnExit();
	}

	@After
	public void tearDown() throws Exception {
		tempDirectory.toFile().delete();
	}

	@Test
	public void testZip() throws IOException {
		Path file1 = Files.createTempFile(tempDirectory, "zip", "test");
		Files.write(file1, Arrays.asList("test zip file 1"));
		Path file2 = Files.createTempFile(tempDirectory, "zip", "test");
		Files.write(file2, Arrays.asList("test zip file 2"));
		Path dir1 = Files.createTempDirectory(tempDirectory, "zip");
		Path dir1_file1 = Files.createTempFile(dir1, "zip", "test");
		Files.write(dir1_file1, Arrays.asList("test zip dir 1 file 1"));
		Path testZip = testDirectory.resolve("testZip.zip");
		ZipUtil.zip(tempDirectory, testZip);
		Assert.assertTrue("Files.exist", Files.exists(testZip));
		
		Path unZip = Files.createTempDirectory(testDirectory, "unZip");
		ZipUtil.unZip(testZip, unZip.resolve("unZip"));
		Assert.assertEquals("file.list.length", 3, unZip.resolve("unZip").toFile().list().length);
	}

	@Test
	public void testUnZip() {
		
	}

	@Test
	public void testWalkFileTree() {
		
	}

	@Test
	public void testVisitDirectory() {
		
	}

	@Test
	public void testVisitFile() {
		
	}

	@Test
	public void testSetEntryCount() {
		
	}

	@Test
	public void testGetEntryCount() {
		
	}

}

package epf.file.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author PC
 *
 */
public interface FileUtil {
	
	/**
	 * @param directory
	 * @throws IOException
	 */
	static void deleteDirectory(final Path directory) throws IOException {
	    Files.walkFileTree(directory, 
	      new SimpleFileVisitor<Path>() {
	    	
	        @Override
	        public FileVisitResult postVisitDirectory(
	          final Path dir, final IOException exc) throws IOException {
	            Files.delete(dir);
	            return FileVisitResult.CONTINUE;
	        }
	        
	        @Override
	        public FileVisitResult visitFile(
	          final Path file, final BasicFileAttributes attrs) throws IOException {
	            Files.delete(file);
	            return FileVisitResult.CONTINUE;
	        }
	    });
	}
}

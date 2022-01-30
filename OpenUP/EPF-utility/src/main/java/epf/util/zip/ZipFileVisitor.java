/**
 * 
 */
package epf.util.zip;

import java.nio.file.FileVisitor;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author PC
 *
 */
public abstract class ZipFileVisitor implements FileVisitor<ZipEntry> {
	
	/**
	 * 
	 */
	private transient final ZipFile zipFile;
	
	/**
	 * @param zipFile
	 */
	public ZipFileVisitor(final ZipFile zipFile) {
		Objects.requireNonNull(zipFile, "ZipFile");
		this.zipFile = zipFile;
	}

	public ZipFile getZipFile() {
		return zipFile;
	}
}

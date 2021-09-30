package epf.util.zip;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public abstract class ZipUtil {
	
	/**
	 * @param directory
	 * @param file
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void zip(final Path directory, final Path file) throws FileNotFoundException, IOException {
		Objects.requireNonNull(directory, "Path");
		Objects.requireNonNull(file, "Path");
		try (
	            OutputStream fos = Files.newOutputStream(file);
	            ZipOutputStream zos = new ZipOutputStream(fos)
	    ) {
	        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
	        	
	        	@Override
	            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
	                zos.putNextEntry(new ZipEntry(directory.relativize(file).toString()));
	                Files.copy(file, zos);
	                zos.closeEntry();
	                return FileVisitResult.CONTINUE;
	            }

	        	@Override
	            public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
	                final ZipEntry entry = new ZipEntry(directory.relativize(dir).toString() + "/");
	        		final int count = dir.toFile().list().length;
	                setEntryCount(entry, count);
	                zos.putNextEntry(entry);
	                zos.closeEntry();
	                return FileVisitResult.CONTINUE;
	            }
	        });
	    }
	}
	
	/**
	 * @param file
	 * @param directory
	 * @throws ZipException
	 * @throws IOException
	 */
	public static void unZip(final Path file, final Path directory) throws ZipException, IOException {
		Objects.requireNonNull(directory, "Path");
		Objects.requireNonNull(file, "Path");
		final ZipFile zipFile = new ZipFile(file.toFile());
		walkFileTree(file, new ZipFileVisitor(zipFile) {

			@Override
			public FileVisitResult preVisitDirectory(final ZipEntry dir, final BasicFileAttributes attrs) throws IOException {
				Files.createDirectory(Paths.get(directory.toString(), dir.getName()));
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(final ZipEntry file, final BasicFileAttributes attrs) throws IOException {
				try(InputStream input = getZipFile().getInputStream(file)){
					Files.copy(input, Paths.get(directory.toString(), file.getName()));
				}
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(final ZipEntry file, final IOException exc) throws IOException {
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(final ZipEntry dir, final IOException exc) throws IOException {
				return FileVisitResult.CONTINUE;
			}});
		zipFile.close();
	}
	
	/**
	 * @param path
	 * @param visitor
	 * @throws ZipException
	 * @throws IOException
	 */
	public static void walkFileTree(final Path path, final FileVisitor<ZipEntry> visitor) throws ZipException, IOException {
		Objects.requireNonNull(path, "Path");
		Objects.requireNonNull(visitor, "FileVisitor");
		final ZipFile zipFile = new ZipFile(path.toFile());
		try(InputStream input = Files.newInputStream(path)){
			try(ZipInputStream zipInput = new ZipInputStream(input)){
				final ZipEntry root = zipInput.getNextEntry();
				if(root != null) {
					visitDirectory(root, zipInput, visitor);
				}
			}
		}
		zipFile.close();
	}
	
	/**
	 * @param entry
	 * @param zipInput
	 * @param visitor
	 * @return
	 * @throws IOException
	 */
	protected static FileVisitResult visitDirectory(final ZipEntry current, final ZipInputStream zipInput, final FileVisitor<ZipEntry> visitor) throws IOException {
		FileVisitResult result = visitor.preVisitDirectory(current, null);
		if(FileVisitResult.TERMINATE.equals(result)) {
			return result;
		}
		IOException exception = null;
		try {
			final int count = getEntryCount(current);
			zipInput.closeEntry();
			int i = 0;
			ZipEntry entry;
			while(i < count) {
				entry = zipInput.getNextEntry();
				if(entry.isDirectory()) {
					result = visitDirectory(entry, zipInput, visitor);
				}
				else {
					result = visitFile(entry, zipInput, visitor);
				}
				if(FileVisitResult.TERMINATE.equals(result)) {
					return result;
				}
				i++;
			}
		}
		catch(IOException ex) {
			exception = ex;
		}
		return visitor.postVisitDirectory(current, exception);
	}
	
	/**
	 * @param current
	 * @param zipInput
	 * @param visitor
	 * @return
	 * @throws IOException
	 */
	protected static FileVisitResult visitFile(final ZipEntry current, final ZipInputStream zipInput, final FileVisitor<ZipEntry> visitor) throws IOException {
		FileVisitResult result = FileVisitResult.CONTINUE;
		try {
			result = visitor.visitFile(current, null);
			zipInput.closeEntry();
		}
		catch(IOException ex) {
			visitor.visitFileFailed(current, ex);
		}
		return result;
	}
	
	/**
	 * @param entry
	 * @param count
	 */
	protected static void setEntryCount(final ZipEntry entry, final int count) {
		entry.setExtra(String.valueOf(count).getBytes(StandardCharsets.UTF_8));
	}
	
	/**
	 * @param entry
	 * @return
	 */
	protected static int getEntryCount(final ZipEntry entry) {
		return Integer.parseInt(new String(entry.getExtra(), StandardCharsets.UTF_8));
	}
}

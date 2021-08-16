/**
 * 
 */
package epf.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * @author PC
 *
 */
public interface IOUtil {
	
	int DEFAULT_BUFFER_SIZE = 8192;
	
	/**
	 * @param in
	 * @param out
	 * @return
	 * @throws IOException
	 */
	static long transferTo(final InputStream in, final OutputStream out) throws IOException {
		Objects.requireNonNull(out, "out");
        long transferred = 0;
        final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int read;
        while ((read = in.read(buffer, 0, DEFAULT_BUFFER_SIZE)) >= 0) {
            out.write(buffer, 0, read);
            transferred += read;
        }
        return transferred;
    }
}

/**
 * 
 */
package epf.util.io;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Objects;

/**
 * @author PC
 *
 */
public interface IOUtil {
	
	int TRANSFER_BUFFER_SIZE = 8192;

	/**
	 * @param in
	 * @param out
	 * @return
	 * @throws IOException
	 */
	static long transferTo(final Reader in, final Writer out) throws IOException {
        Objects.requireNonNull(out, "out");
        long transferred = 0;
        final char[] buffer = new char[TRANSFER_BUFFER_SIZE];
        int nRead;
        while ((nRead = in.read(buffer, 0, TRANSFER_BUFFER_SIZE)) >= 0) {
            out.write(buffer, 0, nRead);
            transferred += nRead;
        }
        return transferred;
    }
}

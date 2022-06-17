package epf.file.cache;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.MappedByteBuffer;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

/**
 * 
 */
public class FileOutput implements StreamingOutput {
	
	/**
	 *
	 */
	private transient final MappedByteBuffer buffer;
	
	/**
	 * @param buffer
	 */
	public FileOutput(final MappedByteBuffer buffer) {
		this.buffer = buffer;
		
	}

	@Override
	public void write(final OutputStream output) throws IOException, WebApplicationException {
		output.write(buffer.array());
	}

}

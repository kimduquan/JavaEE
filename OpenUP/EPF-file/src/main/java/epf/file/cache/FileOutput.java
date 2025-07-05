package epf.file.cache;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.MappedByteBuffer;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.StreamingOutput;

public class FileOutput implements StreamingOutput {
	
	private transient final MappedByteBuffer buffer;
	
	public FileOutput(final MappedByteBuffer buffer) {
		this.buffer = buffer;
		
	}

	@Override
	public void write(final OutputStream output) throws IOException, WebApplicationException {
		output.write(buffer.array());
	}

}

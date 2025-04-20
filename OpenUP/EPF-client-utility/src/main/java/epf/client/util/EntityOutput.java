package epf.client.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.StreamingOutput;
import epf.util.io.IOUtil;

public class EntityOutput implements StreamingOutput {
    
    private transient final InputStream input;

    public EntityOutput(final InputStream input) {
    	Objects.requireNonNull(input, "InputStream");
        this.input = input;
    }

    @Override
    public void write(final OutputStream output) throws IOException, WebApplicationException {
        IOUtil.transferTo(input, output);
        input.close();
    }
    
}

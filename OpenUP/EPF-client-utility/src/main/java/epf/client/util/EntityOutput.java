package epf.client.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import epf.util.io.IOUtil;

/**
 *
 * @author FOXCONN
 */
public class EntityOutput implements StreamingOutput {
    
    /**
     * 
     */
    private transient final InputStream input;

    /**
     * @param input
     */
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

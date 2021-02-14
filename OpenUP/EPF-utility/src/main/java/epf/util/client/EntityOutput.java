/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.util.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

/**
 *
 * @author FOXCONN
 */
public class EntityOutput implements StreamingOutput {
    
    private InputStream in;

    public EntityOutput(InputStream in) {
        this.in = in;
    }

    @Override
    public void write(OutputStream out) throws IOException, WebApplicationException {
        try(InputStreamReader reader = new InputStreamReader(in)){
            try(OutputStreamWriter writer = new OutputStreamWriter(out)){
                reader.transferTo(writer);
            }
        }
        in.close();
    }
    
}

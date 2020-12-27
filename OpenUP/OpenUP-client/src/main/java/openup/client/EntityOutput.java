/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.CharBuffer;
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
        CharBuffer buffer = CharBuffer.allocate(1024);
        try(InputStreamReader reader = new InputStreamReader(in)){
            try(OutputStreamWriter writer = new OutputStreamWriter(out)){
                int n;
                do{
                    n = reader.read(buffer);
                    if(n > -1){
                        writer.write(buffer.array(), 0, n);
                    }
                }
                while(n > -1);
            }
            
        }
        in.close();
    }
    
}

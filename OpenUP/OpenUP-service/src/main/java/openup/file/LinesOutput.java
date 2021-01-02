/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.file;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

/**
 *
 * @author FOXCONN
 */
public class LinesOutput implements StreamingOutput {
    
    private Stream<String> lines;
    private boolean stop;
    
    public LinesOutput(Stream<String> lines){
        this.lines = lines;
        stop = false;
    }

    @Override
    public void write(OutputStream out) throws IOException, WebApplicationException {
        lines.forEach(line -> {
            if(!stop){
                try {
                    out.write(line.getBytes());
                } 
                catch (IOException ex) {
                    stop = true;
                    Logger.getLogger(LinesOutput.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
}

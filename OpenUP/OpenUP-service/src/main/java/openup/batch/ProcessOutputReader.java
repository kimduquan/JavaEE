/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.batch;

import java.io.File;
import java.io.Serializable;
import java.util.Scanner;
import javax.batch.api.chunk.ItemReader;
import javax.batch.runtime.context.JobContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author FOXCONN
 */
@Dependent
@Named("ProcessOutputReader")
public class ProcessOutputReader implements ItemReader {
    
    @Inject
    private JobContext context;
    
    private Process process;
    private Scanner scanner;
    
    public ProcessOutputReader(){
        
    }

    @Override
    public void open(Serializable arg0) throws Exception {
        ProcessBuilder builder = new ProcessBuilder();
        String command = context.getProperties().getProperty("command");
        String directory = context.getProperties().getProperty("directory");
        builder.command(command.split(" "));
        builder.directory(new File(directory));
        process = builder.start();
        scanner = new Scanner(process.getInputStream());
    }

    @Override
    public void close() throws Exception {
        scanner.close();
        process.destroyForcibly();
    }

    @Override
    public Object readItem() throws Exception {
        if(process.isAlive()){
            return scanner.nextLine();
        }
        return null;
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return null;
    }
    
}

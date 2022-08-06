package epf.function.internal;

import java.nio.file.Files;
import java.nio.file.Path;
import org.jppf.client.JPPFJob;
import org.jppf.client.event.JobEvent;
import org.jppf.client.event.JobListener;

/**
 * @author PC
 *
 */
public class EPFJob extends JPPFJob implements JobListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private transient Path tempDir;

	@Override
	public void jobStarted(final JobEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void jobEnded(final JobEvent event) {
		if(tempDir != null) {
			try {
				Files.deleteIfExists(tempDir);
			} 
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void jobDispatched(JobEvent event) {
		if(tempDir == null) {
			try {
				tempDir = Files.createTempDirectory(getUuid());
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void jobReturned(final JobEvent event) {
		// TODO Auto-generated method stub
		
	}

	public Path getTempDir() {
		return tempDir;
	}
}

/**
 * 
 */
package epf.image;

import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * @author PC
 *
 */
@Path("image")
@ApplicationScoped
public class Image implements epf.client.image.Image {
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	@Override
	public Response findContours(final InputStream input) throws Exception {
		final java.nio.file.Path path = Files.createTempFile("image", ".img");
		Files.copy(input, path);
		final Mat image = Imgcodecs.imread(path.toString());
		Files.delete(path);
		Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2GRAY);
		Imgproc.threshold(image, image, 127, 255, 0);
		final List<MatOfPoint> points = new ArrayList<>();
		final Mat h = new Mat();
		Imgproc.findContours(image, points, h, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		return Response.ok().build();
	}

}

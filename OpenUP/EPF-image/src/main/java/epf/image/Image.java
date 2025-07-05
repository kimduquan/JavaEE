package epf.image;

import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import epf.naming.Naming;
import epf.util.logging.LogManager;

@Path(Naming.IMAGE)
@ApplicationScoped
public class Image implements epf.client.image.Image {
	
	private transient final Logger LOGGER = LogManager.getLogger(Image.class.getName());
	
	@PostConstruct
	protected void postConstruct() {
		try {
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		}
		catch(Throwable ex) {
			LOGGER.log(Level.SEVERE, "postConstruct", ex);
		}
	}

	@Override
	public Response findContours(final InputStream input) throws Exception {
		final java.nio.file.Path path = Files.createTempFile(Naming.IMAGE, ".img");
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

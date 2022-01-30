package epf.file;

import java.nio.file.Path;

public interface IWatchService {

	/**
	 * @param security
	 */
	void register(Path path);

}
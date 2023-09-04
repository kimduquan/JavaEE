package epf.util.logging;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * @author PC
 *
 */
public class LogManager implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private static final Map<String, Logger> LOGGERS = new ConcurrentHashMap<>();
	
	/**
	 * @param clsName
	 * @return
	 */
	public static Logger getLogger(final String clsName) {
		Objects.requireNonNull(clsName);
		return LOGGERS.computeIfAbsent(clsName, name -> {
			return Logger.getLogger(name);
		});
	}
}

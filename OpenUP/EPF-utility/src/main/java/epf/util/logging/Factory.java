package epf.util.logging;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Factory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param injectionPoint
	 * @return
	 */
	@Produces 
	public Logger getLogger(final InjectionPoint injectionPoint) {
		return LogManager.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}
}

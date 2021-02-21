package epf.util.logging;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@ApplicationScoped
public class Factory {

	private Map<String, Logger> loggers;
	
	public Factory() {
		loggers = new ConcurrentHashMap<>();
	}
	
	@PreDestroy
	void clear() {
		loggers.clear();
	}

	@Produces 
	public Logger getLogger(InjectionPoint injectionPoint) {
		return getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}
	
	public Logger getLogger(String clsName) {
		return loggers.computeIfAbsent(clsName, name -> {
			return Logger.getLogger(name);
		});
	}
}

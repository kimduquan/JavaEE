package epf.shell;

import org.jboss.resteasy.plugins.providers.DefaultBooleanWriter;
import org.jboss.resteasy.plugins.providers.DefaultNumberWriter;
import org.jboss.resteasy.plugins.providers.DocumentProvider;
import org.jboss.resteasy.plugins.providers.IIOImageProvider;
import org.jboss.resteasy.plugins.providers.SourceProvider;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection(targets = {
		DefaultBooleanWriter.class,
		DefaultNumberWriter.class,
		DocumentProvider.class,
		IIOImageProvider.class,
		SourceProvider.class
})
public interface Quarkus {

}

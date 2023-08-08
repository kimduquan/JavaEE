package epf.shell;

import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * 
 */
@RegisterForReflection(targets = { org.h2.fulltext.FullTextLucene.class, org.h2.fulltext.FullTextLucene.FullTextTrigger.class })
public interface Native {

}

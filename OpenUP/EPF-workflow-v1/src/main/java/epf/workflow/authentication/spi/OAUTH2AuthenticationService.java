package epf.workflow.authentication.spi;

import epf.workflow.authentication.schema.OAUTH2Authentication;

public interface OAUTH2AuthenticationService {

	void autheticate(final OAUTH2Authentication oauth2Authentication) throws Exception;
}

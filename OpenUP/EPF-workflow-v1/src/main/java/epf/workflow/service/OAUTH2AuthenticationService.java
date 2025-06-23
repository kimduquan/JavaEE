package epf.workflow.service;

import epf.workflow.schema.OAUTH2Authentication;

public interface OAUTH2AuthenticationService {

	void autheticate(final OAUTH2Authentication oauth2Authentication) throws Exception;
}

package epf.webapp.search.view;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.servlet.http.HttpServletRequest;
import epf.webapp.GatewayUtil;
import epf.webapp.naming.Naming;
import epf.webapp.search.SearchCollector;
import epf.webapp.security.Session;

/**
 * 
 */
@ViewScoped
@Named(Naming.Search.VIEW)
public class SearchView implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private String text;
	
	/**
	 *
	 */
	private SearchCollector collector;
	
	/**
	 *
	 */
	@Inject
	private transient GatewayUtil gateway;
	
	/**
	 *
	 */
	@Inject
	private transient Session session;
	
	/**
	 *
	 */
	@Inject
	private HttpServletRequest request;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void initialize() {
		text = request.getParameter("text");
		if(text != null ) {
			collector = new SearchCollector(gateway, session.getToken(), text);
		}
	}

	public SearchCollector getCollector() {
		return collector;
	}
	
	/**
	 * @param object
	 * @param attribute
	 * @return
	 */
	public String getAttribute(final JsonObject object, final String attribute) {
		final JsonValue value = object.get(attribute);
		String string = "";
		if(value != null) {
			string = value.toString();
			switch(value.getValueType()) {
				case NULL:
					string = "";
					break;
				case STRING:
					string = object.getString(attribute);
				default:
					break;
				
			}
		}
		return string;
	}
}

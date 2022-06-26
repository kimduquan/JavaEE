package epf.webapp.search.view;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonValue;
import javax.servlet.http.HttpServletRequest;
import epf.util.json.JsonUtil;
import epf.webapp.internal.GatewayUtil;
import epf.webapp.internal.Session;
import epf.webapp.naming.Naming;
import epf.webapp.search.SearchCollector;

/**
 * 
 */
@ViewScoped
@Named(Naming.Search.VIEW)
public class SearchPage implements Serializable, SearchView {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private String text = "";
	
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
	 * @return
	 */
	public String search() {
		if(text != null ) {
			collector = new SearchCollector(gateway, session.getToken(), text);
		}
		return "";
	}
	
	/**
	 * @param value
	 * @return
	 */
	public String toString(final JsonValue value) {
		return JsonUtil.toString(value);
	}
	
	/**
	 * @param array
	 * @return
	 */
	public List<JsonValue> toList(final JsonValue array) {
		return array.asJsonArray().getValuesAs(JsonValue.class);
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setText(final String text) {
		this.text = text;
	}
}

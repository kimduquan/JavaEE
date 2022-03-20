package epf.view;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import epf.webapp.naming.Naming;

@ApplicationScoped
@Named(Naming.VIEW)
public class ViewUtil {

	/**
	 * @param name
	 * @param styleClass
	 * @return
	 */
	public String styleClass(final String name, final String... styleClass) {
		String style = name.replace('.', ' ');
		if(styleClass != null) {
			style += " " + String.join(" ", styleClass);
		}
		return style;
	}
	
	/**
	 * @param name
	 * @return
	 */
	public String name(final String name) {
		return name.replace('.', ' ');
	}
}

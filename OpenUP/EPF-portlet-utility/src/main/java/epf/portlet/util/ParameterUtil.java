/**
 * 
 */
package epf.portlet.util;

import javax.inject.Inject;
import javax.inject.Named;
import javax.portlet.MutableRenderParameters;
import javax.portlet.annotations.PortletRequestScoped;
import epf.portlet.naming.Naming;

/**
 * @author PC
 *
 */
@PortletRequestScoped
public class ParameterUtil {

	/**
	 * 
	 */
	@Inject @Named(Naming.Bridge.MUTABLE_RENDER_PARAMETERS)
	private transient MutableRenderParameters renderParams;
	
	/**
	 * @param name
	 * @param value
	 */
	public void setValue(final String name, final String value) {
		renderParams.setValue(name, value);
	}
	
	/**
	 * @param name
	 * @return
	 */
	public String getValue(final String name) {
		return renderParams.getValue(name);
	}
	
	/**
	 * @param name
	 * @return
	 */
	public String[] getValues(final String name) {
		return renderParams.getValues(name);
	}
	
	/**
	 * @param name
	 * @param values
	 */
	public void setValues(final String name, final String... values) {
		renderParams.setValues(name, values);
	}
}

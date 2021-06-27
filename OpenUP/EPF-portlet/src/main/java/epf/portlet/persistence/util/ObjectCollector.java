/**
 * 
 */
package epf.portlet.persistence.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import epf.client.schema.Attribute;
import epf.portlet.SessionUtil;

/**
 * @author PC
 *
 */
@RequestScoped
public class ObjectCollector {
	
	/**
	 * 
	 */
	private List<ObjectComparator> comparators;
	
	/**
	 * 
	 */
	private List<ObjectFilter> filters;
	/**
	 * 
	 */
	private Map<String, Object> filter;
	
	/**
	 * 
	 */
	private List<Map<String, Object>> list;

	/**
	 * 
	 */
	@Inject
	private transient SessionUtil sessionUtil;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		comparators = sessionUtil.getPortletAttribute(getClass(), "comparators");
		filters = sessionUtil.getPortletAttribute(getClass(), "filters");
		filter = sessionUtil.getPortletAttribute(getClass(), "filter");
	}
	
	/**
	 * @param stream
	 * @return
	 */
	public List<Map<String, Object>> collect(final Stream<Map<String, Object>> stream){
		if(list == null) {
			Stream<Map<String, Object>> newStream = stream;
			if(filters != null) {
				for(ObjectFilter f : filters) {
					newStream = newStream.filter(data -> f.filter(filter, data));
				}
			}
			if(comparators != null) {
				for(ObjectComparator comparator : comparators) {
					newStream = newStream.sorted(comparator);
				}
			}
			list = newStream.collect(Collectors.toList());
		}
		return list;
	}

	public ObjectComparator sort(final Attribute attribute, final boolean ascending) {
		if(comparators == null) {
			comparators = new ArrayList<>();
		}
		comparators.removeIf(c -> c.getAttribute().getName().equals(attribute.getName()));
		final ObjectComparator newComparator = new ObjectComparator(attribute, ascending);
		comparators.add(0, newComparator);
		sessionUtil.setPortletAttribute(getClass(), "comparators", comparators);
		return newComparator;
	}

	public ObjectFilter filter(final Attribute attribute, final boolean include) {
		if(filters == null) {
			filters = new ArrayList<>();
		}
		filters.removeIf(f -> f.getComparator().getAttribute().getName().equals(attribute.getName()));
		final ObjectFilter newFilter = new ObjectFilter(new ObjectComparator(attribute, true), include);
		filters.add(0, newFilter);
		sessionUtil.setPortletAttribute(getClass(), "filters", filters);
		return newFilter;
	}
}

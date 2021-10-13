/**
 * 
 */
package epf.portlet.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.json.JsonObject;

/**
 * @author PC
 *
 */
public class JsonObjectCollector {
	
	/**
	 * 
	 */
	private final List<String> names;
	
	/**
	 * 
	 */
	private final List<JsonObjectComparator> comparators;
	
	/**
	 * 
	 */
	private final List<JsonObjectFilter> filters;
	
	/**
	 * 
	 */
	private long skip;
	
	/**
	 * 
	 */
	private long limit;
	
	/**
	 * @param names
	 */
	public JsonObjectCollector(final List<String> names) {
		this.names = names;
		comparators = Arrays.asList(new JsonObjectComparator[names.size()]);
		filters = Arrays.asList(new JsonObjectFilter[names.size()]);
		limit = -1;
		skip = -1;
	}
	
	/**
	 * @param name
	 * @param value
	 */
	public void filter(final String name, final String value) {
		final int index = names.indexOf(name);
		JsonObjectFilter filter = filters.get(index);
		if(filter == null) {
			filter = new JsonObjectFilter(name);
			filters.set(index, filter);
		}
		filter.setValue(value);
	}
	
	/**
	 * @param name
	 */
	public void sort(final String name) {
		final int index = names.indexOf(name);
		JsonObjectComparator comparator = comparators.get(index);
		if(comparator == null) {
			comparator = new JsonObjectComparator(name);
			comparators.set(index, comparator);
		}
		else {
			comparator.setAscending(!comparator.isAscending());
		}
	}

	/**
	 * @param stream
	 * @return
	 */
	public List<JsonObject> collect(final Stream<JsonObject> stream){
		Stream<JsonObject> streamObj = stream;
		for(JsonObjectFilter filter : filters) {
			if(filter != null) {
				streamObj = streamObj.filter(filter::filter);
			}
		}
		for(JsonObjectComparator comparator : comparators) {
			if(comparator != null) {
				streamObj = streamObj.sorted(comparator);
			}
		}
		if(limit >= 0) {
			streamObj = streamObj.limit(limit);
		}
		if(skip >= 0) {
			streamObj = streamObj.skip(skip);
		}
		return streamObj.collect(Collectors.toList());
	}

	/**
	 * @param skip
	 */
	public void skip(final long skip) {
		this.skip = skip;
	}

	/**
	 * @param limit
	 */
	public void limit(final long limit) {
		this.limit = limit;
	}
	
	/**
	 * @param name
	 */
	public void move(final String name) {
		final int index = names.indexOf(name);
		final String prevName = names.get(index - 1);
		names.set(index, prevName);
		names.set(index - 1, name);
		
		final JsonObjectFilter filter = filters.get(index);
		final JsonObjectFilter prevFilter = filters.get(index - 1);
		filters.set(index, prevFilter);
		filters.set(index - 1, filter);
		
		final JsonObjectComparator comparator = comparators.get(index);
		final JsonObjectComparator prevComparator = comparators.get(index - 1);
		comparators.set(index, prevComparator);
		comparators.set(index - 1, comparator);
	}
}

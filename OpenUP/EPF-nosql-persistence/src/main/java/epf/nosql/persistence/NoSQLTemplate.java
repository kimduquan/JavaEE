package epf.nosql.persistence;

import java.io.InputStream;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.nosql.QueryMapper.MapperFrom;
import jakarta.nosql.QueryMapper.MapperNameOrder;
import jakarta.nosql.QueryMapper.MapperOrder;
import jakarta.nosql.QueryMapper.MapperQueryBuild;
import jakarta.nosql.QueryMapper.MapperWhere;
import jakarta.nosql.Template;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

/**
 * 
 */
public class NoSQLTemplate {

	/**
	 * 
	 */
	private transient final Template template;
	
	/**
	 * @param template
	 */
	public NoSQLTemplate(final Template template) {
		this.template = template;
	}
	
	/**
	 * @param type
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public Response insert(final Class<?> type, final InputStream body) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create()){
			final Object object = jsonb.fromJson(body, type);
			return Response.ok(template.insert(object)).build();
		}
	}
	
	/**
	 * @param type
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public Response update(final Class<?> type, final InputStream body) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create()){
			final Object object = jsonb.fromJson(body, type);
			return Response.ok(template.update(object)).build();
		}
	}
	
	/**
	 * @param type
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Response find(final Class<?> type, final Object id) throws Exception {
		final Optional<?> object = template.find(type, id);
		return Response.ok(object.orElseThrow(NotFoundException::new)).build();
	}
	
	/**
	 * @param type
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Response delete(final Class<?> type, final Object id) throws Exception {
		template.delete(type, id);
		return Response.ok().build();
	}
	
	/**
	 * @param type
	 * @param filters
	 * @param orderBy
	 * @param asc
	 * @param limit
	 * @param skip
	 * @return
	 * @throws Exception
	 */
	public Response select(final Class<?> type, final MultivaluedMap<String, ?> filters, final String orderBy, final Boolean asc, final Long limit, final Long skip) throws Exception {
		final MapperFrom from = template.select(type);
		MapperQueryBuild result = null;
		if(filters != null && !filters.isEmpty()) {
			MapperWhere where = null;
			for(Entry<String, ?> entry : filters.entrySet()) {
				where = where.and(entry.getKey()).in((List<?>)entry.getValue());
			}
			if(orderBy != null) {
				final MapperOrder order = where.orderBy(orderBy);
				final MapperNameOrder nameOrder = asc ? order.asc() : order.desc();
				if(limit != null) {
					if(skip != null) {
						result = nameOrder.limit(limit).skip(skip);
					}
					else {
						result = nameOrder.limit(limit);
					}
				}
				else if(skip != null) {
					result = nameOrder.skip(skip);
				}
			}
			else {
				if(limit != null) {
					if(skip != null) {
						result = where.limit(limit).skip(skip);
					}
					else {
						result = where.limit(limit);
					}
				}
				else if(skip != null) {
					result = where.skip(skip);
				}
			}
		}
		else {
			if(orderBy != null) {
				final MapperOrder order = from.orderBy(orderBy);
				final MapperNameOrder nameOrder = asc ? order.asc() : order.desc();
				if(limit != null) {
					if(skip != null) {
						result = nameOrder.limit(limit).skip(skip);
					}
					else {
						result = nameOrder.limit(limit);
					}
				}
				else if(skip != null) {
					result = nameOrder.skip(skip);
				}
			}
			else {
				if(limit != null) {
					if(skip != null) {
						result = from.limit(limit).skip(skip);
					}
					else {
						result = from.limit(limit);
					}
				}
				else if(skip != null) {
					result = from.skip(skip);
				}
			}
		}
		return Response.ok(result.result()).build();
	}
}

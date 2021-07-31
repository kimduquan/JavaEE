<<<<<<< HEAD:OpenUP/EPF-schema/src/main/java/epf/schema/h2/SearchData.java
package epf.schema.h2;
=======
package epf.client.persistence;
>>>>>>> remotes/origin/micro:OpenUP/EPF-client/src/main/java/epf/client/persistence/SearchData.java

import java.io.Serializable;
import java.util.List;

/**
 * @author PC
 *
 */
public class SearchData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String schema;
	/**
	 * 
	 */
	private String table;
	/**
	 * 
	 */
	private List<String> columns;
	/**
	 * 
	 */
	private List<String> keys;
	/**
	 * 
	 */
	private double score;
	
	public String getSchema() {
		return schema;
	}
	public void setSchema(final String schema) {
		this.schema = schema;
	}
	public String getTable() {
		return table;
	}
	public void setTable(final String table) {
		this.table = table;
	}
	public List<String> getColumns() {
		return columns;
	}
	public void setColumns(final List<String> columns) {
		this.columns = columns;
	}
	public List<String> getKeys() {
		return keys;
	}
	public void setKeys(final List<String> keys) {
		this.keys = keys;
	}
	public double getScore() {
		return score;
	}
	public void setScore(final double score) {
		this.score = score;
	}
}

package epf.persistence.schema;

/**
 * 
 */
public class Column {

	/**
	 *
	 */
	private String name;

	/**
	 *
	 */
	private boolean unique;

	/**
	 *
	 */
	private boolean nullable;

	/**
	 *
	 */
	private boolean insertable;

	/**
	 *
	 */
	private boolean updatable;

	/**
	 *
	 */
	private String columnDefinition;

	/**
	 *
	 */
	private String table;

	/**
	 *
	 */
	private int length;

	/**
	 *
	 */
	private int precision;

	/**
	 *
	 */
	private int scale;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(final boolean unique) {
		this.unique = unique;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(final boolean nullable) {
		this.nullable = nullable;
	}

	public boolean isInsertable() {
		return insertable;
	}

	public void setInsertable(final boolean insertable) {
		this.insertable = insertable;
	}

	public boolean isUpdatable() {
		return updatable;
	}

	public void setUpdatable(final boolean updatable) {
		this.updatable = updatable;
	}

	public String getColumnDefinition() {
		return columnDefinition;
	}

	public void setColumnDefinition(final String columnDefinition) {
		this.columnDefinition = columnDefinition;
	}

	public String getTable() {
		return table;
	}

	public void setTable(final String table) {
		this.table = table;
	}

	public int getLength() {
		return length;
	}

	public void setLength(final int length) {
		this.length = length;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(final int precision) {
		this.precision = precision;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(final int scale) {
		this.scale = scale;
	}
}

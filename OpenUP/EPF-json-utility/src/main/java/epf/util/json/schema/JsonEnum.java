package epf.util.json.schema;

/**
 * @author PC
 *
 */
public class JsonEnum extends Value {

	/**
	 * 
	 */
	private String[] _enum;

	public String[] getEnum() {
		return _enum;
	}

	public void setEnum(final String[] _enum) {
		this._enum = _enum;
	}
}

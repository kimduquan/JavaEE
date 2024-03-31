package epf.math;

public class Symbol {

	private final String string;
	private final long number;
	
	public Symbol(final String string, final long number) {
		this.string = string;
		this.number = number;
	}
	
	public String getString() {
		return string;
	}
	
	public long getNumber() {
		return number;
	}
}

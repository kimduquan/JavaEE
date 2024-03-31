package epf.math;

public class SequenceNumber {
	
	private final long number;
	private final long exponent;
	
	public SequenceNumber(long number, long exponent) {
		this.number = number;
		this.exponent = exponent;
	}
	
	public long getNumber() {
		return number;
	}
	
	public long getExponent() {
		return exponent;
	}
	
	public double doubleValue() {
		return Double.parseDouble("" + number + "." + exponent);
	}
	
	public static SequenceNumber fromDouble(final double number) {
		final String string = String.valueOf(number);
		final long n = Long.parseLong(string.split(".")[0]);
		final long i = Long.parseLong(string.split(".")[1]);
		return new SequenceNumber(n, i);
	}
}

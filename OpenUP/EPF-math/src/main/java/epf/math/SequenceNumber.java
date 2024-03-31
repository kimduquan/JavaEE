package epf.math;

public class SequenceNumber {
	
	private final long number;
	private final long index;
	
	public SequenceNumber(long number, long index) {
		this.number = number;
		this.index = index;
	}
	
	public long getNumber() {
		return number;
	}
	
	public long getIndex() {
		return index;
	}
	
	public double doubleValue() {
		return Double.parseDouble("" + number + "." + index);
	}
	
	public static SequenceNumber fromDouble(final double number) {
		final String string = String.valueOf(number);
		final long n = Long.parseLong(string.split(".")[0]);
		final long i = Long.parseLong(string.split(".")[1]);
		return new SequenceNumber(n, i);
	}
}

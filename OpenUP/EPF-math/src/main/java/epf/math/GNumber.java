package epf.math;

import java.util.List;

public class GNumber {

	private final SequenceNumber[] numbers;
	
	public GNumber(final List<SequenceNumber> numbers) {
		this.numbers = numbers.toArray(new SequenceNumber[0]);
	}

	public SequenceNumber[] getNumbers() {
		return numbers;
	}
	
	@Override
	public String toString() {
		final StringBuilder string = new StringBuilder();
		for(SequenceNumber number : numbers) {
			string.append('[').append(number.doubleValue()).append(']');
		}
		return string.toString();
	}
}

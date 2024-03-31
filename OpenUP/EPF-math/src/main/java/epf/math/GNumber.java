package epf.math;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

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
		BigInteger big = BigInteger.valueOf(1);
		for(SequenceNumber number : numbers) {
			BigInteger bigNumber = BigInteger.valueOf(number.getNumber()).pow((int)number.getExponent());
			big = big.multiply(bigNumber);
			
		}
		return big.toString();
	}
	
	public String toString(final List<Long> primeNumbers, final Map<Long, Symbol> symbols) {
		final StringBuilder string = new StringBuilder();
		for(SequenceNumber number : numbers) {
			string.append(symbols.get(number.getExponent()).getString());
		}
		return string.toString();
	}
}

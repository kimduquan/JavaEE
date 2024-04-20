package epf.math;

import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 */
public class MathContext {
	
	/**
	 * 
	 */
	private final List<Long> primeNumbers = new ArrayList<>();
	
	/**
	 * 
	 */
	private final Map<Long, MathObject<?>> objects = new LinkedHashMap<>();
	
	public void addPrime(final long prime) {
		primeNumbers.add(prime);
	}
	
	private static long firstPrime() {
		return 2;
	}
	
	private static long lastPrime(final List<Long> primeNumbers) {
		return primeNumbers.isEmpty() ? firstPrime() : primeNumbers.getLast();
	}
	
	private static boolean isPrime(final long first, long number) {
		for(long i = first; i <= number / 2; i++)
		{
			if(number % i == 0) {
				return  false;
			}
		}
		return true;
	}
	
	private static long nextPrime(final long first, final long number) {
		long nextNumber = number + 1;
		do {
			if(isPrime(first, nextNumber)) {
				return nextNumber;
			}
			nextNumber++;
		}
		while(true);
	}
	
	private static long newPrime(final List<Long> primeNumbers) {
		final long prime = nextPrime(firstPrime(), lastPrime(primeNumbers));
		primeNumbers.add(prime);
		return prime;
	}
	
	private static void fillPrimes(final int count, final List<Long> primeNumbers) {
		final long firstPrime = firstPrime();
		long lastPrime = primeNumbers.getLast();
		for(int i = 0; i < count; i++) {
			lastPrime = nextPrime(firstPrime, lastPrime);
			primeNumbers.add(lastPrime);
		}
	}
	
	private static int length(MathObject<?> array, final List<Long> primeNumbers) {
		return elementAt(array.getValue(), 0, primeNumbers).intValue();
	}
	
	private static Long elementAt(final BigInteger value, final int index, final List<Long> primeNumbers) {
		final BigInteger prime = BigInteger.valueOf(primeNumbers.get(index));
		long exponent = 0;
		BigInteger[] divideAndRemainder = value.divideAndRemainder(prime);
		while(!BigInteger.ZERO.equals(divideAndRemainder[0]) && BigInteger.ZERO.equals(divideAndRemainder[1])) {
			exponent++;
			divideAndRemainder = divideAndRemainder[0].divideAndRemainder(prime);
		}
		return exponent;
	}
	
	private static MathObject<?> elementAt(final MathObject<?> array, final int index, Map<Long, MathObject<?>> objects, final List<Long> primeNumbers) {
		final Long key = elementAt(array.getValue(), index, primeNumbers);
		return objects.get(key);
	}
	
	private static boolean isArray(final MathObject<?> object) {
		return !BigInteger.valueOf(object.getKey()).equals(object.getValue());
	}
	
	public static void print(final MathObject<?> object, final PrintStream out, final Map<Long, MathObject<?>> objects, final List<Long> primeNumbers) {
		if(isArray(object)) {
			out.println();
			final int length = length(object, primeNumbers);
			out.print("#" + object.getKey() + "[" + length + "]:");
			print(object, length, out, objects, primeNumbers);
		}
		else {
			out.println();
			out.print("#" + object.getKey() + ":");
			out.print(object.getObject());
		}
	}
	
	private static void print(final MathObject<?> object, final int length, final PrintStream out, final Map<Long, MathObject<?>> objects, final List<Long> primeNumbers) {
		if(isArray(object)) {
			for(int i = 0; i < length; i++) {
				final MathObject<?> element = elementAt(object, i + 1, objects, primeNumbers);
				if(isArray(element)) {
					final int elementLength = length(element, primeNumbers);
					print(element, elementLength, out, objects, primeNumbers);
				}
				else {
					out.print(element.getObject());
				}
			}
		}
		else {
			out.print(object.getObject());
		}
	}
	
	private static MathObject<?> getObject(final Object object, final Collection<MathObject<?>> objects){
		MathObject<?> mathObject = null;
		for(MathObject<?> math : objects) {
			if(math.getObject().equals(object)) {
				mathObject = math;
			}
		}
		return mathObject;
	}
	
	private static MathObject<?> getArray(final BigInteger value, final Collection<MathObject<?>> objects){
		for(MathObject<?> mathObject : objects) {
			if(isArray(mathObject) && mathObject.getValue().equals(value)) {
				return mathObject;
			}
		}
		return null;
	}
	
	private static MathObject<?> getArray(final Object[] array, final List<MathObject<?>> list, final Map<Long, MathObject<?>> objects, final List<Long> primeNumbers){
		BigInteger value = BigInteger.ONE;
		for(int i = 0; i < array.length; i++) {
			MathObject<?> object = getObject(array[i], objects.values());
			if(object != null) {
				list.add(object);
				if(i > 0) {
					final List<MathObject<?>> list1 = new LinkedList<>();
					final List<MathObject<?>> list2 = new LinkedList<>();
					if(split(list, list1, list2, objects, primeNumbers)) {
						list.clear();
						list.addAll(list1);
						list.addAll(list2);
						value = valueOf(list, primeNumbers);
						MathObject<?> mathArray = getArray(value, objects.values());
						if(mathArray != null) {
							print(mathArray, System.out, objects, primeNumbers);
						}
						return mathArray;
					}
				}
			}
			else {
				object = newObject(array[i], newPrime(primeNumbers), objects);
				print(object, System.out, objects, primeNumbers);
				list.add(object);
			}
			value = addElement(value, object.getKey(), i, primeNumbers);
		}
		value = addLength(value, list.size(), primeNumbers);
		return getArray(value, objects.values());
	}
	
	private static MathObject<?> newObject(final Object object, final Long key, final Map<Long, MathObject<?>> objects){
		final MathObject<?> mathObject = new MathObject<>(object, key);
		objects.put(key, mathObject);
		return mathObject;
	}
	
	private static MathObject<?> newArray(final String string, final Long key, final BigInteger value, final BigDecimal entropy, final Map<Long, MathObject<?>> objects){
		final MathObject<?> mathObject = new MathObject<>(string, key, value, entropy);
		objects.put(key, mathObject);
		return mathObject;
	}
	
	private static BigInteger valueOf(final Long element, final int index, final List<Long> primeNumbers) {
		if(index >= primeNumbers.size()) {
			fillPrimes(index - primeNumbers.size() + 1, primeNumbers);
		}
		return BigInteger.valueOf(primeNumbers.get(index)).pow(element.intValue());
	}
	
	private static BigInteger valueOf(final List<MathObject<?>> list, final List<Long> primeNumbers) {
		BigInteger value = BigInteger.ONE;
		int index = 0;
		for(MathObject<?> object : list) {
			value = addElement(value, object.getKey(), index, primeNumbers);
			index++;
		}
		value = addLength(value, list.size(), primeNumbers);
		return value;
	}
	
	private static BigInteger addElement(final BigInteger value, final Long element, final int index, final List<Long> primeNumbers) {
		final BigInteger beta = valueOf(element, index + 1, primeNumbers);
		return value.multiply(beta);
	}
	
	private static BigInteger addLength(final BigInteger value, final int length, final List<Long> primeNumbers) {
		return value.multiply(valueOf((long)length, 0, primeNumbers));
	}
	
	private static boolean split(final List<MathObject<?>> list, final List<MathObject<?>> list1, final List<MathObject<?>> list2, final Map<Long, MathObject<?>> objects, final List<Long> primeNumbers){
		boolean split = false;
		for(int i = 1; i < list.size(); i++) {
			final List<MathObject<?>> subList2 = list.subList(i, list.size());
			final BigInteger value2 = valueOf(subList2, primeNumbers);
			final MathObject<?> array2 = getArray(value2, objects.values());
			if(array2 != null) {
				list2.add(array2);
				split = true;
				final List<MathObject<?>> subList1 = list.subList(0, i);
				final BigInteger array1Value = valueOf(subList1, primeNumbers);
				final MathObject<?> array1 = getArray(array1Value, objects.values());
				if(array1 != null) {
					list1.add(array1);
				}
				else {
					list1.addAll(subList1);
				}
				break;
			}
		}
		return split;
	}
	
	private static BigDecimal log(final double number, final int n) {
		final BigDecimal logNumber = new BigDecimal("" + Math.log(number), java.math.MathContext.UNLIMITED);
		final BigDecimal logn =  new BigDecimal("" + Math.log(n), java.math.MathContext.UNLIMITED);
		return logNumber.divide(logn, java.math.MathContext.DECIMAL128);
	}
	
	private static BigDecimal entropy(final List<MathObject<?>> list) {
		BigDecimal entropy = BigDecimal.ONE;
		final int length = list.size();
		final Map<Long, Integer> frequency = new LinkedHashMap<>();
		for(MathObject<?> object : list) {
			frequency.put(object.getKey(), frequency.getOrDefault(object.getKey(), 0) + 1);
		}
		for(Long key : frequency.keySet()) {
			final double probability = (double) frequency.get(key) / length;
			final BigDecimal log2 = log(probability, 2);
			entropy = entropy.subtract(BigDecimal.valueOf(probability).multiply(log2, java.math.MathContext.UNLIMITED), java.math.MathContext.UNLIMITED);
		}
		return entropy;
	}
	
	private static MathObject<?> numbering(final MathObject<?>[] array, final Map<Long, MathObject<?>> objects, final List<Long> primeNumbers){
		BigInteger value = BigInteger.ONE;
		final List<MathObject<?>> list = new LinkedList<>();
		for(int i = 0; i < array.length; i++) {
			final MathObject<?> object = array[i];
			list.add(object);
			if(i > 0) {
				final List<MathObject<?>> list1 = new LinkedList<>();
				final List<MathObject<?>> list2 = new LinkedList<>();
				if(split(list, list1, list2, objects, primeNumbers)) {
					list.clear();
					if(list1.size() > 1) {
						final BigInteger value1 = valueOf(list1, primeNumbers);
						final BigDecimal entropy1 = entropy(list1);
						final MathObject<?> array1 = newArray("", newPrime(primeNumbers), value1, entropy1, objects);
						print(array1, System.out, objects, primeNumbers);
						list.add(array1);
					}
					else {
						list.addAll(list1);
					}
					if(list2.size() > 1) {
						final BigInteger value2 = valueOf(list2, primeNumbers);
						final BigDecimal entropy2 = entropy(list2);
						final MathObject<?> array2 = newArray("", newPrime(primeNumbers), value2, entropy2, objects);
						print(array2, System.out, objects, primeNumbers);
						list.add(array2);
					}
					else {
						list.addAll(list2);
					}
					value = valueOf(list, primeNumbers);
					MathObject<?> mathArray = getArray(value, objects.values());
					if(mathArray == null) {
						final BigDecimal entropy = entropy(list);
						mathArray = newArray("", newPrime(primeNumbers), value, entropy, objects);
						print(mathArray, System.out, objects, primeNumbers);
					}
					return mathArray;
				}
			}
			value = addElement(value, object.getKey(), i, primeNumbers);
		}
		value = addLength(value, list.size(), primeNumbers);
		MathObject<?> mathArray = getArray(value, objects.values());
		if(mathArray == null) {
			final BigDecimal entropy = entropy(list);
			mathArray = newArray("", newPrime(primeNumbers), value, entropy, objects);
			print(mathArray, System.out, objects, primeNumbers);
		}
		return mathArray;
	}
	
	private static MathObject<?> numbering(final Object[] array, final Map<Long, MathObject<?>> objects, final List<Long> primeNumbers){
		final List<MathObject<?>> list = new LinkedList<>();
		MathObject<?> numbering = getArray(array, list, objects, primeNumbers);
		if(numbering == null) {
			if(list.size() > 1) {
				numbering = numbering(list.toArray(new MathObject<?>[0]), objects, primeNumbers);
			}
			else if(list.size() > 0){
				return list.getFirst();
			}
		}
		return numbering;
	}
	
	public static void main(final String[] args) throws IOException {
		final MathContext context = new MathContext();
		context.addPrime(2);
		Files.lines(Path.of("C:\\GIT\\document\\specification.md")).forEach(line -> {
			final Object[] array = new Object[line.length()];
			for(int i = 0 ; i < line.length(); i++) {
				array[i] = "" + line.charAt(i);
			}
			numbering(array, context.objects, context.primeNumbers);
		});
	}
}

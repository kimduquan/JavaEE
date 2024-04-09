package epf.math;

import java.io.IOException;
import java.io.PrintStream;
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
				out.print(element.getObject());
			}
		}
		else {
			out.print(object.getObject());
		}
	}
	
	private static MathObject<?> getObject(final Object object, final Collection<MathObject<?>> objects, final List<Long> primeNumbers){
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
	
	private static BigInteger getSubArray(final List<MathObject<?>> list, final int index, final int end, final List<Long> primeNumbers) {
		BigInteger value = BigInteger.ONE;
		final MathObject<?>[] array = list.subList(index, end).toArray(new MathObject<?>[0]);
		for(int i = 0;i < array.length; i++) {
			value = addElement(value, array[i].getKey(), i, primeNumbers);
		}
		value = addLength(value, array.length, primeNumbers);
		return value;
	}
	
	private static MathObject<?> getArray(final Object[] array, final List<MathObject<?>> list, final Map<Long, MathObject<?>> objects, final List<Long> primeNumbers){
		BigInteger value = BigInteger.ONE;
		for(int i = 0; i < array.length; i++) {
			MathObject<?> object = getObject(array[i], objects.values(), primeNumbers);
			if(object != null) {
				list.add(object);
				value = addElement(value, object.getKey(), i, primeNumbers);
				if(i > 0) {
					final List<MathObject<?>> list1 = new LinkedList<>();
					final List<MathObject<?>> list2 = new LinkedList<>();
					if(split(list, list1, list2, objects, primeNumbers)) {
						value = BigInteger.ONE;
						list.clear();
						list.addAll(list1);
						list.addAll(list2);
						int j = 0;
						for(MathObject<?> newObject : list) {
							value = addElement(value, newObject.getKey(), j, primeNumbers);
							j++;
						}
						break;
					}
				}
			}
			else {
				object = newObject(array[i], newPrime(primeNumbers), objects);
				print(object, System.out, objects, primeNumbers);
				list.add(object);
				value = addElement(value, object.getKey(), i, primeNumbers);
			}
		}
		value = addLength(value, list.size(), primeNumbers);
		return getArray(value, objects.values());
	}
	
	private static MathObject<?> newObject(final Object object, final Long key, final Map<Long, MathObject<?>> objects){
		final MathObject<?> mathObject = new MathObject<>(object, key);
		objects.put(key, mathObject);
		return mathObject;
	}
	
	private static MathObject<?> newArray(final String string, final Long key, final BigInteger value, final Map<Long, MathObject<?>> objects){
		final MathObject<?> mathObject = new MathObject<>(string, key, value);
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
	
	private static MathObject<?>[] toArray(final MathObject<?> array, final Map<Long, MathObject<?>> objects, final List<Long> primeNumbers) {
		final int length = length(array, primeNumbers);
		final MathObject<?>[] mathArray = new MathObject<?>[length];
		for(int i = 0; i < length; i++) {
			mathArray[i] = elementAt(array, i + 1, objects, primeNumbers);
		}
		return mathArray;
	}
	
	private static BigInteger addLength(final BigInteger value, final int length, final List<Long> primeNumbers) {
		return value.multiply(valueOf((long)length, 0, primeNumbers));
	}
	
	private static boolean split(final List<MathObject<?>> list, final List<MathObject<?>> list1, final List<MathObject<?>> list2, final Map<Long, MathObject<?>> objects, final List<Long> primeNumbers){
		boolean split = false;
		for(int j = 0; j < list.size(); j++) {
			final int subArrayLength = list.size() - j;
			final BigInteger subArrayValue = getSubArray(list, j, list.size(), primeNumbers);
			final MathObject<?> subArray = getArray(subArrayValue, objects.values());
			final int subArray1Length = j - 0;
			final BigInteger subArray1Value = getSubArray(list, 0, j, primeNumbers);
			final MathObject<?> subArray1 = getArray(subArray1Value, objects.values());
			if(subArrayLength > 1 && subArray1Length > 1) {
				if(subArray != null && subArray1 != null) {
					print(subArray, System.out, objects, primeNumbers);
					print(subArray1, System.out, objects, primeNumbers);
					list1.add(subArray1);
					list2.add(subArray);
					split = true;
				}
				else if(subArray != null) {
					print(subArray, System.out, objects, primeNumbers);
					list1.addAll(list.subList(0, j));
					list2.add(subArray);
					split = true;
				}
				else if(subArray1 != null) {
					print(subArray1, System.out, objects, primeNumbers);
					list1.add(subArray1);
					list2.addAll(list.subList(j, list.size()));
					split = true;
				}
			}
		}
		return split;
	}
	
	private static boolean beta(final BigInteger p, final BigInteger q, final BigInteger element, final int index) {
		return p.remainder(q.multiply(BigInteger.valueOf(index).add(BigInteger.ONE)).add(BigInteger.ONE)).compareTo(element) == 0;
	}
	
	private static boolean beta(final BigInteger p, final BigInteger q, final MathObject<?>[] array) {
		for(int i = 0; i < array.length; i++) {
			if(!beta(p, q, array[i].getValue(), i)) {
				return false;
			}
		}
		return true;
	}
	
	private static MathObject<?> numbering(final MathObject<?>[] array, final Map<Long, MathObject<?>> objects, final List<Long> primeNumbers){
		BigInteger value = BigInteger.ONE;
		final List<MathObject<?>> list = new LinkedList<>();
		for(int i = 0; i < array.length; i++) {
			final MathObject<?> object = array[i];
			list.add(object);
			value = addElement(value, object.getKey(), i, primeNumbers);
			if(i > 0) {
				final List<MathObject<?>> list1 = new LinkedList<>();
				final List<MathObject<?>> list2 = new LinkedList<>();
				if(split(list, list1, list2, objects, primeNumbers)) {
					value = BigInteger.ONE;
					list.clear();
					if(list1.size() > 1) {
						final BigInteger value1 = valueOf(list1, primeNumbers);
						final MathObject<?> array1 = newArray("", newPrime(primeNumbers), value1, objects);
						print(array1, System.out, objects, primeNumbers);
						list.add(array1);
					}
					if(list2.size() > 1) {
						final BigInteger value2 = valueOf(list2, primeNumbers);
						final MathObject<?> array2 = newArray("", newPrime(primeNumbers), value2, objects);
						print(array2, System.out, objects, primeNumbers);
						list.add(array2);
					}
					value = valueOf(list, primeNumbers);
					MathObject<?> mathArray = getArray(value, objects.values());
					if(mathArray == null) {
						mathArray = newArray("", newPrime(primeNumbers), value, objects);
						print(mathArray, System.out, objects, primeNumbers);
					}
					return mathArray;
				}
			}
		}
		value = addLength(value, list.size(), primeNumbers);
		MathObject<?> mathArray = getArray(value, objects.values());
		if(mathArray == null) {
			mathArray = newArray("", newPrime(primeNumbers), value, objects);
			print(mathArray, System.out, objects, primeNumbers);
		}
		return mathArray;
	}
	
	public static void beta(final MathObject<?> array, final MathObject<?> first, final Map<Long, MathObject<?>> objects, final List<Long> primeNumbers) {
		final MathObject<?>[] mathArray = toArray(array, objects, primeNumbers);
		BigInteger max = BigInteger.valueOf(0);
		for(int i = 0; i < mathArray.length; i++) {
			if(mathArray[i].getValue().compareTo(max) > 0) {
				max = mathArray[i].getValue();
			}
		}
		final BigInteger n = max.pow(mathArray.length);
		for(BigInteger p = BigInteger.ONE; p.compareTo(n) < 0; p = p.add(BigInteger.ONE)) {
			for(BigInteger q = BigInteger.ONE; q.compareTo(p) < 0; q = q.add(BigInteger.ONE)) {
				if(beta(p, q, mathArray)) {
					System.out.println();
					System.out.println("p=" + p + ",q=" + q);
				}
			}
		}
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
		//numbering(new String[] {".", ",", "?", "\t", "\n", "\r", "\"", "'", "[", "]", "{", "}", "(", ")", "-", ":", "/", " "}, context.objects, context.primeNumbers);
		final String[] ascii = new String[126 - 32 + 1];
		int ii = 0;
		for (char c = 32; c <= 126; c++) {
			ascii[ii++] = "" + c;
		}
		numbering(ascii, context.objects, context.primeNumbers);
		Files.lines(Path.of("C:\\GIT\\document\\specification.txt")).forEach(line -> {
			//final Object[] array = line.split(" ");
			final Object[] array = new Object[line.length()];
			for(int i = 0 ; i < line.length(); i++) {
				array[i] = "" + line.charAt(i);
			}
			numbering(array, context.objects, context.primeNumbers);
		});
		//context.numbering("this is".split(" "), context.objects, context.primeNumbers);
		//final MathObject<?> o = context.numbering("this is a test".split(" "), context.objects, context.primeNumbers);
		//print(o, System.out, context.objects, context.primeNumbers);
		/*context.objects.forEach((key, object) -> {
			print(object, System.out, context.objects, context.primeNumbers);
		});*/
	}
}

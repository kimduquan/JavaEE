package epf.math;

import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
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
	
	private long lastPrime() {
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
	
	private long newPrime() {
		final long prime = nextPrime(firstPrime(), lastPrime());
		primeNumbers.add(prime);
		return prime;
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
	
	private static BigInteger addElements(BigInteger value, final MathObject<?>[] array, final List<Long> primeNumbers) {
		for(int i = 0; i < array.length; i++) {
			value = addElement(value, array[i].getKey(), i, primeNumbers);
		}
		return value;
	}
	
	private static void print(final MathObject<?> object, final PrintStream out, final Map<Long, MathObject<?>> objects, final List<Long> primeNumbers) {
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
					print(element, length(element, primeNumbers), out, objects, primeNumbers);
				}
				else {
					out.print("[" + element.getObject() + "]");
				}
			}
		}
		else {
			out.print(object.getObject());
		}
	}
	
	private static MathObject<?> getObject(final Object object, final Collection<MathObject<?>> objects, final List<Long> primeNumbers){
		MathObject<?> mathObject = null;
		if(object.getClass().isArray()) {
			final Object[] array = (Object[]) object;
			final MathObject<?>[] mathArray = new MathObject<?>[array.length];
			mathObject = getArray(array, mathArray, objects, primeNumbers);
		}
		else {
			for(MathObject<?> math : objects) {
				if(math.getObject().equals(object)) {
					mathObject = math;
				}
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
	
	private static MathObject<?> getArray(final Object[] array, final MathObject<?>[] mathArray, final Collection<MathObject<?>> objects, final List<Long> primeNumbers){
		boolean allElementsExist = true;
		for(int i = 0; i < array.length; i++) {
			mathArray[i] = getObject(array[i], objects, primeNumbers);
			if(mathArray[i] == null) {
				allElementsExist = false;
			}
		}
		if(allElementsExist) {
			BigInteger value = addLength(BigInteger.ONE, array.length, primeNumbers);
			value = addElements(value, mathArray, primeNumbers);
			return getArray(value, objects);
		}
		return null;
	}
	
	private MathObject<?> newObject(final Object object, final Long key){
		final MathObject<?> mathObject = new MathObject<>(object, key);
		objects.put(key, mathObject);
		return mathObject;
	}
	
	private MathObject<?> newArray(final String string, final Long key, final BigInteger value){
		final MathObject<?> mathObject = new MathObject<>(string, key, value);
		objects.put(key, mathObject);
		return mathObject;
	}
	
	private static BigInteger valueOf(final Long element, final int index, final List<Long> primeNumbers) {
		return BigInteger.valueOf(primeNumbers.get(index)).pow(element.intValue());
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
	
	private MathObject<?> numbering(final MathObject<?>[] array, final Map<Long, MathObject<?>> objects, final List<Long> primeNumbers){
		BigInteger value = BigInteger.ONE;
		final List<MathObject<?>> list = Arrays.asList(array);
		final Map<Long, Integer> indexes = new LinkedHashMap<>();
		for(int i = 0; i < array.length; i++) {
			final MathObject<?> object = array[i];
			/*if(indexes.containsKey(object.getKey())) {
				final int prevIndex = indexes.get(object.getKey());
				final List<MathObject<?>> split = new LinkedList<>();
				
				final List<MathObject<?>> split1 = new LinkedList<>();
				if(0 < prevIndex) {
					split1.addAll(list.subList(0, prevIndex));
					if(split1.size() > 1) {
						final MathObject<?> object1 = numbering(split1.toArray(new MathObject<?>[0]), objects, primeNumbers);
						split.add(object1);
					}
					else {
						split.addAll(split1);
					}
				}
				split.add(array[prevIndex]);
				
				final List<MathObject<?>> split2 = new LinkedList<>();
				if(prevIndex + 1 < i - 1) {
					split2.addAll(list.subList(prevIndex + 1, i));
					if(split2.size() > 1) {
						final MathObject<?> object2 = numbering(split2.toArray(new MathObject<?>[0]), objects, primeNumbers);
						split.add(object2);
					}
					else {
						split.addAll(split2);
					}
				}
				split.add(array[i]);
				
				final List<MathObject<?>> split3 = new LinkedList<>();
				if(i + 1 < array.length) {
					split3.addAll(list.subList(i + 1, array.length));
					if(split3.size() > 1) {
						final MathObject<?> object3 = numbering(split3.toArray(new MathObject<?>[0]), objects, primeNumbers);
						split.add(object3);
					}
					else {
						split.addAll(split3);
					}
				}
				value = addLength(BigInteger.ONE, split.size(), primeNumbers);
				value = addElements(value, split.toArray(new MathObject<?>[0]), primeNumbers);
				break;
			}
			else*/ {
				value = addElement(value, object.getKey(), i, primeNumbers);
				indexes.put(object.getKey(), i);
				BigInteger existingValue = addLength(value, i + 1, primeNumbers);
				final MathObject<?> existingArray = getArray(existingValue, objects.values());
				if(existingArray != null) {
					final List<MathObject<?>> newList = new LinkedList<>();
					newList.add(existingArray);
					newList.addAll(list.subList(i + 1, array.length));
					return numbering(newList.toArray(new MathObject<?>[0]), objects, primeNumbers);
				}
			}
		}
		value = addLength(value, array.length, primeNumbers);
		MathObject<?> mathArray = getArray(value, objects.values());
		if(mathArray == null) {
			mathArray = newArray("", newPrime(), value);
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
	
	private MathObject<?> numbering(final Object[] array, final Map<Long, MathObject<?>> objects, final List<Long> primeNumbers){
		final MathObject<?>[] mathArray = new MathObject<?>[array.length];
		MathObject<?> numbering = getArray(array, mathArray, objects.values(), primeNumbers);
		if(numbering == null) {
			for(int i = 0; i < array.length; i++) {
				MathObject<?> mathObject = mathArray[i];
				if(mathObject == null) {
					mathObject = newObject(array[i], newPrime());
					print(mathObject, System.out, objects, primeNumbers);
					mathArray[i] = mathObject;
				}
			}
			if(array.length > 1) {
				numbering = numbering(mathArray, objects, primeNumbers);
			}
			else if(array.length > 0){
				return mathArray[0];
			}
		}
		return numbering;
	}
	
	public static void main(final String[] args) throws IOException {
		final MathContext context = new MathContext();
		context.addPrime(2);
		//context.numbering(new String[] {".", ",", "?", "\t", "\n", "\r", "\"", "'", "[", "]", "{", "}", "(", ")", "-", ":", "/", " "}, context.first, context.objects);
		Files.lines(Path.of("C:\\GIT\\document\\specification.txt")).forEach(line -> {
			final Object[] array = line.split(" ");
			context.numbering(array, context.objects, context.primeNumbers);
		});
		//context.numbering("this is".split(" "), context.objects, context.primeNumbers);
		//final MathObject<?> o = context.numbering("this is a test".split(" "), context.objects, context.primeNumbers);
		//print(o, System.out, context.objects, context.primeNumbers);
		/*context.objects.forEach((key, object) -> {
			print(object, System.out, context.objects, context.primeNumbers);
		});*/
	}
}

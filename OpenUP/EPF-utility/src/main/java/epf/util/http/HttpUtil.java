package epf.util.http;

import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 * @author PC
 *
 */
public abstract class HttpUtil {

	/**
	 * @param s
	 * @return
	 */
	public static Hashtable<String, String[]> parseQueryString(final String s) {

        String valArray[] = null;
	
        if (s == null) {
            throw new IllegalArgumentException();
        }

        final Hashtable<String, String[]> ht = new Hashtable<String, String[]>();
        final StringBuilder sb = new StringBuilder();
        final StringTokenizer st = new StringTokenizer(s, "&");
        while (st.hasMoreTokens()) {
        	final String pair = st.nextToken();
        int pos = pair.indexOf('=');
        if (pos == -1) {
            // XXX
            // should give more detail about the illegal argument
            throw new IllegalArgumentException();
        }
        final String key = parseName(pair.substring(0, pos), sb);
        final String val = parseName(pair.substring(pos+1, pair.length()), sb);
        if (ht.containsKey(key)) {
        	final String oldVals[] = ht.get(key);
            valArray = new String[oldVals.length + 1];
            for (int i = 0; i < oldVals.length; i++) {
                valArray[i] = oldVals[i];
            }
            valArray[oldVals.length] = val;
        } else {
            valArray = new String[1];
            valArray[0] = val;
        }
        ht.put(key, valArray);
    }

	return ht;
    }
	
	/**
	 * @param s
	 * @param sb
	 * @return
	 */
	private static String parseName(final String s, final StringBuilder sb) {
        sb.setLength(0);
        for (int i = 0; i < s.length(); i++) {
        	final char c = s.charAt(i); 
            switch (c) {
                case '+':
                    sb.append(' ');
                    break;
                case '%':
                    try {
                        sb.append((char) Integer.parseInt(s.substring(i+1, i+3), 
                                16));
                        i += 2;
                    } 
                    catch (NumberFormatException e) {
                        // XXX
                        // need to be more specific about illegal arg
                        throw new IllegalArgumentException();
                    } 
                    catch (StringIndexOutOfBoundsException e) {
                    	final String rest  = s.substring(i);
                        sb.append(rest);
                        if (rest.length()==2)
                            i++;
                        }

                        break;
                default:
                    sb.append(c);
                    break;
            }
        }

        return sb.toString();
    }
}

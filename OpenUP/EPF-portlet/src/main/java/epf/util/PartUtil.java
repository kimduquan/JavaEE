package epf.util;

import javax.servlet.http.Part;

public class PartUtil {

	public static String getFileName(Part part) {

		String contentDisposition = part.getHeader("Content-Disposition");

		if (contentDisposition != null) {
			String[] nameValuePairs = contentDisposition.split(";");

			for (String nameValuePair : nameValuePairs) {
				nameValuePair = nameValuePair.trim();

				if (nameValuePair.startsWith("filename")) {
					int pos = nameValuePair.indexOf("=");

					if (pos > 0) {
						return nameValuePair.substring(pos + 1).replace("\"", "");
					}
				}
			}
		}

		return null;
	}
}

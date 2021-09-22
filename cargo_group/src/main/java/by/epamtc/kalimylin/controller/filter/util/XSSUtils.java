package by.epamtc.kalimylin.controller.filter.util;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.owasp.esapi.ESAPI;

/**
 * Clears a string from XSS
 */
public class XSSUtils {
	
	/**
	 * Use of external data cleansing libraries.
	 * Get the value and canonicalize it, use ESAPI. 
	 * ESAPI is an open-source web application security 
	 * management library available in OWASP.
	 * Check the value by XSS templates. If the value is suspicious, 
	 * it will be set in an empty string. It uses Jsoup , which provides 
	 * some simple cleaning functions. For more control, it is necessary
	 * to create own regular expressions, but this may be more prone 
	 * to error than using a library.
	 * 
	 * @param value  string with data for clears
	 * @return {@link String} without XSS elements
	 */
    public static String stripXSS(String value) {
        if (value == null) {
            return null;
        }
        value = ESAPI.encoder()
            .canonicalize(value)
              .replaceAll("\0", "");
        return Jsoup.clean(value, Whitelist.none());
    }
}

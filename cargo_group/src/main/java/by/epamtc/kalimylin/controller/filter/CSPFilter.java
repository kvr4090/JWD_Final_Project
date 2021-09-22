package by.epamtc.kalimylin.controller.filter;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.CSP;
import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.POLICY;

import java.io.IOException;

import by.epamtc.kalimylin.controller.util.AttributeAndParameter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The filter describes safe resource boot sources,
 * sets rules for using embedded styles, scripts.
 * Downloading from non-white list resources is blocked.
 * White list can see in {@link AttributeAndParameter} variable POLICY
 * A directive report-uri: https://example.com/csp/report; 
 * can be used to report violations.
 * Every time a CSP violation is registered, the directive 
 * sends a HTTP POST request to the specified address. 
 * The query body contains a JSON object that contains all the necessary details
 * It is difficult to deploy CSP properly and properly on real resource, 
 * as each page needs a separate policy. Filter made for training purposes.
 */
public class CSPFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		if (response instanceof HttpServletResponse) {  
			((HttpServletResponse)response).setHeader(CSP, POLICY);  
		}
		chain.doFilter(request, response);
	}
}
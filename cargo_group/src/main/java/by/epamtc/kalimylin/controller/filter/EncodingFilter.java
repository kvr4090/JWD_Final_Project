package by.epamtc.kalimylin.controller.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import by.epamtc.kalimylin.controller.util.AttributeAndParameter;

/**
 * The filter sets the specified encoding.
 */
public class EncodingFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, 
			ServletResponse servletResponse, FilterChain filterChain) 
			throws IOException, ServletException {

		servletRequest.setCharacterEncoding(AttributeAndParameter.ENCODING);
		filterChain.doFilter(servletRequest, servletResponse);
	}
}

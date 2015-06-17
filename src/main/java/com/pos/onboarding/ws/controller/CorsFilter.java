package com.pos.onboarding.ws.controller;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

public class CorsFilter extends OncePerRequestFilter {

	protected static final Logger log = LogManager.getLogger(CorsFilter.class);
	private static final String CORS_ORIGIN_STRING_KEY = "Access-Control-Allow-Origin";
	private static final String CORS_REQUEST_METHOD_KEY = "Access-Control-Request-Method";
	private static final String CORS_ORIGIN_STRING_VALUE = "*";
	private static final String CORS_ALLOW_METHOD_KEY = "Access-Control-Allow-Methods";
	private static final String CORS_ALLOW_METHOD_VALUE = "GET, POST, PUT, DELETE";
	private static final String CORS_ALLOW_HEADER_KEY = "Access-Control-Allow-Headers";
	private static final String CORS_ALLOW_HEADER_VALUE = "Authorization";
	private static final String CORS_MAX_AGE_KEY = "Access-Control-Max-Age";
	private static final String CORS_MAX_AGE_VALUE = "1728000";

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		log.debug("Enter method doFilterInternal. Method params: {}",
				Arrays.asList(request, response, filterChain));

		response.addHeader(CORS_ORIGIN_STRING_KEY, CORS_ORIGIN_STRING_VALUE);
		if (request.getHeader(CORS_REQUEST_METHOD_KEY) != null
				&& "OPTIONS".equals(request.getMethod()))
			;
		{
			// CORS "pre-flight" request
			response.addHeader(CORS_ALLOW_METHOD_KEY, CORS_ALLOW_METHOD_VALUE);
			response.addHeader(CORS_ALLOW_HEADER_KEY, CORS_ALLOW_HEADER_VALUE);
			response.addHeader(CORS_MAX_AGE_KEY, CORS_MAX_AGE_VALUE);
		}
		log.debug(
				"Return method doFilterInternal. Method params: {}. Result: {}",
				Arrays.asList(request, response, filterChain), response);

		filterChain.doFilter(request, response);
	}

}
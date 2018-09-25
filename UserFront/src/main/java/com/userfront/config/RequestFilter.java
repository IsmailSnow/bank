package com.userfront.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		res.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
		res.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
		res.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		res.setHeader("Access-Control-Max-Age", "3600");
		res.setHeader("Access-Control-Allow-Credentials", "true");

		if (!req.getMethod().equalsIgnoreCase("OPTIONS")) {
			chain.doFilter(req, res);
		} else {
			System.out.println("Pre-flight");
			res.setHeader("Access-Control-Allow-Methods", "POST,GET,DELETE");
			res.setHeader("Access-Control-Max-Age", "3600");
			res.setHeader("Access-Control-Allow-Headers", "authorization, content-type,"
					+ "access-control-request-headers,access-control-request-method,accept,origin,authorization,x-requested-with");
			res.setStatus(HttpServletResponse.SC_OK);
		}

	}

	@Override
	public void destroy() {
	}

}

package org.nico.mocker.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.nico.mocker.utils.HttpContextUtils;
import org.springframework.stereotype.Component;

@Component
public class MockerFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestUrl = httpRequest.getRequestURI();
		String httpMethod = httpRequest.getMethod();
		
		if("/m/apis".equalsIgnoreCase(requestUrl)) {
			chain.doFilter(httpRequest, response);
		}else {
			httpRequest.setAttribute("requestUrl", requestUrl);
			httpRequest.setAttribute("httpMethod", httpMethod);
			httpRequest.getRequestDispatcher("/m/mock").forward(request,response);
		}
	}

	@Override
	public void destroy() {
		HttpContextUtils.clear();
	}

}

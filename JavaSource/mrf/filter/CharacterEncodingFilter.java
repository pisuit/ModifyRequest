package mrf.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;

public class CharacterEncodingFilter implements Filter {
	
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
	      req.setCharacterEncoding("UTF-8");
	      resp.setCharacterEncoding("UTF-8");
	      chain.doFilter(req, resp);
	   }

	   public void init(FilterConfig filterConfig) throws ServletException {
	      
	   }
	   
	   public void destroy() {
	      
	   }
}

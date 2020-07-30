package com.sanal.JwtSecurity.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

public class AuthFilter extends GenericFilterBean {
	
	private static final String SECURITYKEY = "securitykey";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpreq = (HttpServletRequest) request;
		HttpServletResponse httpres = (HttpServletResponse) response;

		if (httpreq.getMethod().equals(HttpMethod.OPTIONS.name())) {
			chain.doFilter(httpreq, httpres);
		} else {
			String headerinfo = httpreq.getHeader("Authorization");
			
			httpres.setHeader("Access-Control-Allow-Origin", httpreq.getHeader("Origin"));
			httpres.setHeader("Access-Control-Allow-Crendentials", "true");
			httpres.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE");
			
			if((headerinfo==null )|| !headerinfo.startsWith("Bearer"))
		    	 throw new ServletException("Token not found");
			String tokeninfo = headerinfo.substring(7);
			
			try {
				JwtParser parser = Jwts.parser().setSigningKey(SECURITYKEY);
				Jwt jwtObj = parser.parse(tokeninfo);
				
				Claims claims = (Claims) jwtObj.getBody();
				
				String userId = claims.getSubject();
				Date date = claims.getIssuedAt();
			} catch (SignatureException e) {
				throw new ServletException("Token not found");
			} catch (MalformedJwtException e) {
				throw new ServletException("Token modified by unauthorized person");
			}
			chain.doFilter(request, response);
		}
	}

}

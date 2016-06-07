package soundlink.server.config.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;

public class CsrfTokenResponseCookieBindingFilter {

	protected static final String REQUEST_ATTRIBUTE_NAME = "_csrf";

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		CsrfToken token = (CsrfToken) request.getAttribute(REQUEST_ATTRIBUTE_NAME);

		Cookie cookie = new Cookie(CSRF.RESPONSE_COOKIE_NAME, token.getToken());
		cookie.setPath("/");
		// cookie.setHttpOnly(true);

		response.addCookie(cookie);

		filterChain.doFilter(request, response);
	}
}

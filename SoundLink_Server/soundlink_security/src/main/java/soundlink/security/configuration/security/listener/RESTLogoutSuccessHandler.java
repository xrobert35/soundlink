package soundlink.security.configuration.security.listener;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * An authentication logout success handler implementation adapted to a REST
 * approach.
 */
public class RESTLogoutSuccessHandler implements LogoutSuccessHandler {
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		Cookie cookie = new Cookie("JSESSIONID", "");
		cookie.setPath("/SoundLink_Server/");
		cookie.setHttpOnly(true);
		response.addCookie(cookie);

	}
}

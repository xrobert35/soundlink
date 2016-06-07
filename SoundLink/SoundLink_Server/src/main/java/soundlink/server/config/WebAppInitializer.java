package soundlink.server.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

import soundlink.server.listener.SoundLinkSessionListener;

@SpringBootApplication
public class WebAppInitializer extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WebAppInitializer.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebAppInitializer.class);
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);

		// servletContext.getSessionCookieConfig().setPath("/");
		// servletContext.getSessionCookieConfig().setHttpOnly(false);
		servletContext.addListener(SoundLinkSessionListener.class);
	}
}
package soundlink.security.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import soundlink.security.configuration.security.filter.CsrfTokenResponseCookieBindingFilter;
import soundlink.security.configuration.security.filter.RESTCrossDomainFilter;
import soundlink.security.configuration.security.listener.RESTAuthenticationEntryPoint;
import soundlink.security.configuration.security.listener.RESTAuthenticationSuccessHandler;
import soundlink.security.configuration.security.listener.RESTLogoutSuccessHandler;
import soundlink.security.configuration.security.service.SoundLinkUserDetailsService;

@Configuration
public class SecurityBeanConfig {

	@Bean(name = "soundlinkUserDetails")
	public UserDetailsService soundlinkUserDetailsService() {
		return new SoundLinkUserDetailsService();
	}

	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		return passwordEncoder;
	}

	@Bean
	public RESTAuthenticationEntryPoint authenticationEntryPoint() {
		return new RESTAuthenticationEntryPoint();
	}

	@Bean
	public SimpleUrlAuthenticationFailureHandler authenticationFailureHandler() {
		return new SimpleUrlAuthenticationFailureHandler();
	}

	@Bean
	public RESTAuthenticationSuccessHandler authenticationSuccessHandler() {
		return new RESTAuthenticationSuccessHandler();
	}

	@Bean
	public RESTCrossDomainFilter corsFilter() {
		return new RESTCrossDomainFilter();
	}

	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
		return new RESTLogoutSuccessHandler();
	}

	@Bean
	public CsrfTokenResponseCookieBindingFilter csrfTokenFilter() {
		return new CsrfTokenResponseCookieBindingFilter();
	}
}

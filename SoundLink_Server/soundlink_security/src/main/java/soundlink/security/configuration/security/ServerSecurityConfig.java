package soundlink.security.configuration.security;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import soundlink.security.configuration.security.filter.CsrfTokenResponseCookieBindingFilter;
import soundlink.security.configuration.security.filter.RESTCrossDomainFilter;
import soundlink.security.configuration.security.filter.StatelessAuthenticationFilter;
import soundlink.security.configuration.security.filter.StatelessLoginFilter;
import soundlink.security.configuration.security.service.TokenAuthenticationService;

/**
 * Spring security configuration with csrf configuration
 *
 * with this configuration we can use /login to login in (with credential + csrf
 * token that you can obtain by calling /login a first time in OPTIONS mode)
 *
 */
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class ServerSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private RESTCrossDomainFilter crossDomainFilter;
    @Resource
    private CsrfTokenResponseCookieBindingFilter csrfTokenFilter;
    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Resource
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Resource
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Resource
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    @Qualifier("soundlinkUserDetails")
    private UserDetailsService userDetailsService;

    @Autowired
    @Qualifier("bCryptPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    public ServerSecurityConfig() {
        super(true);
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Allow unauthenticaded request on /login
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests = http
                .authorizeRequests();

        http.exceptionHandling().and().anonymous().and().servletApi().and().headers().cacheControl();

        authorizeRequests = authorizeRequests.antMatchers("/security/login", "/ws/music", "/message/**/*").permitAll();
        authorizeRequests = authorizeRequests.antMatchers("/admin/**").hasRole("ADMIN");
        authorizeRequests = authorizeRequests.anyRequest().authenticated();

        // http.logout().logoutRequestMatcher(new
        // AntPathRequestMatcher("/security/logout"));

        // Handlers and entry points
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
        http.formLogin().successHandler(authenticationSuccessHandler);
        http.formLogin().failureHandler(authenticationFailureHandler);
        http.logout().logoutUrl("/security/logout");

        http.csrf().disable();

        // Add cross domain filter and csrf filter to add token into response
        // cookie
        http.addFilterBefore(crossDomainFilter, ChannelProcessingFilter.class);

        // custom JSON based authentication by POST of
        // {"username":"<name>","password":"<password>"} which sets the token
        // header upon authentication
        http.addFilterBefore(new StatelessLoginFilter("/security/login", tokenAuthenticationService, userDetailsService,
                authenticationManager()), UsernamePasswordAuthenticationFilter.class);

        // custom Token based authentication based on the header
        // previously given to the client
        http.addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService),
                UsernamePasswordAuthenticationFilter.class);

        // http.addFilterAfter(csrfTokenFilter, CsrfFilter.class);
    }
}
package soundlink.server.config;

import java.util.Locale;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
@ComponentScan(basePackages = "soundlink")
@PropertySource("classpath:application.properties")
public class AppConfig extends WebMvcConfigurerAdapter {

	private static final String BUNDLE_BASE_NAME = "classpath:/locale/msg/messages";
	private static final String BUNDLE_DEFAULT_ENCODING = "UTF-8";

	@Bean
	public SerializableResourceBundleMessageSource serializableResourceBundleMessageSource() {
		SerializableResourceBundleMessageSource resourceBundleMessage = new SerializableResourceBundleMessageSource();
		resourceBundleMessage.setBasename(BUNDLE_BASE_NAME);
		resourceBundleMessage.setDefaultEncoding(BUNDLE_DEFAULT_ENCODING);

		return resourceBundleMessage;
	}

	@Bean
	public SessionLocaleResolver sessionLocaleResolver() {
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(new Locale("fr"));
		return resolver;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	public class SerializableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {
		public Properties getAllProperties(Locale locale) {
			clearCacheIncludingAncestors();
			PropertiesHolder propertiesHolder = getMergedProperties(locale);
			Properties properties = propertiesHolder.getProperties();
			return properties;
		}
	}
}

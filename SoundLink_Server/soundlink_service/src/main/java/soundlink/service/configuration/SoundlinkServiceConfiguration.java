package soundlink.service.configuration;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import soundlink.service.converter.AbstractDtoConverter;

@Configuration
@ComponentScan("soundlink.service")
public class SoundlinkServiceConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void initialiseConverter() {
        Map<String, AbstractDtoConverter> beansOfType = applicationContext.getBeansOfType(AbstractDtoConverter.class);
        beansOfType.values().stream().forEach(bean -> {
            Class<?> entityClass = (Class<?>) ((ParameterizedType) bean.getClass().getGenericSuperclass())
                    .getActualTypeArguments()[0];
            Class<?> dtoClass = (Class<?>) ((ParameterizedType) bean.getClass().getGenericSuperclass())
                    .getActualTypeArguments()[1];
            bean.setDtoClass(dtoClass);
            bean.setEntityClass(entityClass);
        });
    }
}

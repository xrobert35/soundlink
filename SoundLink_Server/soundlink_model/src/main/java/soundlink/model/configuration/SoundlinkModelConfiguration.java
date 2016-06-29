package soundlink.model.configuration;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("soundlink.model")
@EntityScan(basePackages = "soundlink.model.entities")
public class SoundlinkModelConfiguration {

}

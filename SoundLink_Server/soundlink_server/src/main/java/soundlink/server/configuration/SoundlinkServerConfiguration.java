package soundlink.server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@ComponentScan("soundlink")
public class SoundlinkServerConfiguration {

    @Bean(name = "bCryptPasswordEncoder")
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
        return bCryptPasswordEncoder;
    }

}

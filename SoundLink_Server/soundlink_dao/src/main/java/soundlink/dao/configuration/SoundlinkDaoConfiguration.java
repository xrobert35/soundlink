package soundlink.dao.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import soundlink.dao.repositories.UserRepository;

/**
 *
 * @author xrobert
 *
 */
@Configuration
@ComponentScan("soundlink.dao")
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@EnableTransactionManagement
public class SoundlinkDaoConfiguration {

}

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

    // @Bean
    // public DataSource dataSource() {
    // DriverManagerDataSource builder = new DriverManagerDataSource();
    // builder.setDriverClassName(arg0);
    // builder.setUrl(url);
    // builder.setUsername(username);
    // builder.setPassword(password);
    // return builder.setType(EmbeddedDatabaseType.HSQL).build();
    // }
    //
    // @Bean
    // public EntityManagerFactory entityManagerFactory() {
    //
    // HibernateJpaVendorAdapter vendorAdapter = new
    // HibernateJpaVendorAdapter();
    // vendorAdapter.setGenerateDdl(true);
    //
    // LocalContainerEntityManagerFactoryBean factory = new
    // LocalContainerEntityManagerFactoryBean();
    // factory.setJpaVendorAdapter(vendorAdapter);
    // factory.setPackagesToScan("com.acme.domain");
    // factory.setDataSource(dataSource());
    // factory.afterPropertiesSet();
    //
    // return factory.getObject();
    // }
    //
    // @Bean
    // public PlatformTransactionManager transactionManager() {
    //
    // JpaTransactionManager txManager = new JpaTransactionManager();
    // txManager.setEntityManagerFactory(entityManagerFactory());
    // return txManager;
    // }

}

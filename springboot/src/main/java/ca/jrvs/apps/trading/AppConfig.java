package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.dao.config.MarketDataConfig;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {"ca.jrvs.apps.trading.*"})
@EnableJpaRepositories(basePackages = {"ca.jrvs.apps.trading.repo"})
@EnableAutoConfiguration
public class AppConfig {

  private final Logger logger = LoggerFactory.getLogger(AppConfig.class);

  @Bean
  public MarketDataConfig marketDataConfig() {
    MarketDataConfig config = new MarketDataConfig();
    config.setHost("https://cloud.iexapis.com/v1");
    config.setToken(System.getenv("IEX_PUB_TOKEN"));
    return config;
  }

  @Bean
  public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
    PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
    manager.setMaxTotal(50);
    manager.setDefaultMaxPerRoute(50);
    return manager;
  }

  @Bean
  public DataSource dataSource() {
    String url;
    String user;
    String password;

    if (System.getenv("RDS_HOSTNAME") != null) {
      url = "jdbc:postgresql://"
          + System.getenv("RDS_HOSTNAME")
          + ":" + System.getenv("RDS_PORT")
          + "/"
          + "";
      user = System.getenv("RDS_USERNAME");
      password = System.getenv("RDS_PASSWORD");
    } else {
      url = System.getenv("PSQL_URL");
      user = System.getenv("PSQL_USER");
      password = System.getenv("PSQL_PASSWORD");
    }

    BasicDataSource basicDataSource = new BasicDataSource();
    basicDataSource.setUrl(url);
    basicDataSource.setUsername(user);
    basicDataSource.setPassword(password);
    return basicDataSource;
  }

}

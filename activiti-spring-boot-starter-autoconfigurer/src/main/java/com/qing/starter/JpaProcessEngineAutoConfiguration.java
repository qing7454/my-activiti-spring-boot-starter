package com.qing.starter;

import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author qing
 */
@Configuration
@AutoConfigureBefore(DataSourceProcessEngineAutoConfiguration.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class JpaProcessEngineAutoConfiguration {

  @Configuration
  @ConditionalOnClass(name= "javax.persistence.EntityManagerFactory")
  @EnableConfigurationProperties(ActivitiProperties.class)
  public static class JpaConfiguration extends AbstractProcessEngineAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
      return new JpaTransactionManager(emf);
    }

    @Bean
    @ConditionalOnMissingBean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(
            DataSource dataSource, EntityManagerFactory entityManagerFactory,
            PlatformTransactionManager transactionManager, SpringAsyncExecutor springAsyncExecutor) throws IOException {

      SpringProcessEngineConfiguration config = this.baseSpringProcessEngineConfiguration(dataSource, 
          transactionManager, springAsyncExecutor);
      config.setJpaEntityManagerFactory(entityManagerFactory);
      config.setTransactionManager(transactionManager);
      config.setJpaHandleTransaction(false);
      config.setJpaCloseEntityManager(false);
      return config;
    }
  }

}

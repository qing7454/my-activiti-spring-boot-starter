package com.qing.starter;

import org.activiti.engine.*;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides sane definitions for the various beans required to be productive with Activiti in Spring.
 *
 * @author qing
 */
public abstract class AbstractProcessEngineConfiguration {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractProcessEngineConfiguration.class);

  public ProcessEngineFactoryBean springProcessEngineBean(SpringProcessEngineConfiguration configuration) {
    ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
    processEngineFactoryBean.setProcessEngineConfiguration(configuration);
    return processEngineFactoryBean;
  }

  public SpringProcessEngineConfiguration processEngineConfigurationBean(Resource[] processDefinitions,
                                                                         DataSource dataSource,
                                                                         PlatformTransactionManager transactionManager,
                                                                         SpringAsyncExecutor springAsyncExecutor)
        throws IOException {

    SpringProcessEngineConfiguration engine = new SpringProcessEngineConfiguration();
    if (processDefinitions != null && processDefinitions.length > 0) {
      engine.setDeploymentResources(processDefinitions);
    }
    engine.setDataSource(dataSource);
    engine.setTransactionManager(transactionManager);

    if (null != springAsyncExecutor) {
      engine.setAsyncExecutor(springAsyncExecutor);
    }

    return engine;
  }

  public List<Resource> discoverProcessDefinitionResources(ResourcePatternResolver applicationContext, String prefix, List<String> suffixes, boolean checkPDs) throws IOException {
    if (checkPDs) {

    	List<Resource> result = new ArrayList<Resource>();
    	for (String suffix : suffixes) {
    		String path = prefix + suffix;
    		Resource[] resources = applicationContext.getResources(path);
    		if (resources != null && resources.length > 0) {
    			for (Resource resource : resources) {
    				result.add(resource);
    			}
    		}
    	}
    	
    	if (result.isEmpty()) {
      	logger.info(String.format("No process definitions were found for autodeployment"));
    	}
    	
      return result;
    }
    return new ArrayList<Resource>();
  }

  public RuntimeService runtimeServiceBean(ProcessEngine processEngine) {
    return processEngine.getRuntimeService();
  }

  public RepositoryService repositoryServiceBean(ProcessEngine processEngine) {
    return processEngine.getRepositoryService();
  }

  public TaskService taskServiceBean(ProcessEngine processEngine) {
    return processEngine.getTaskService();
  }

  public HistoryService historyServiceBean(ProcessEngine processEngine) {
    return processEngine.getHistoryService();
  }

  public ManagementService managementServiceBeanBean(ProcessEngine processEngine) {
    return processEngine.getManagementService();
  }

  public FormService formServiceBean(ProcessEngine processEngine) {
    return processEngine.getFormService();
  }

  public IdentityService identityServiceBean(ProcessEngine processEngine) {
    return processEngine.getIdentityService();
  }
}

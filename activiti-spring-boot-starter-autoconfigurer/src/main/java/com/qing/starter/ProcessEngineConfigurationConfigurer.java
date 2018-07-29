package com.qing.starter;

import org.activiti.spring.SpringProcessEngineConfiguration;

/**
 * Interface to be implemented by a bean that does some extra configuration of the SpringProcessEngineConfiguration.
 * If such a bean is defined, it will be called when the process engine configuration is created 
 * and the default values have been set. 
 * 
 * @author qing
 */
public interface ProcessEngineConfigurationConfigurer {
	
	void configure(SpringProcessEngineConfiguration processEngineConfiguration);

}

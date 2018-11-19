package cz.fi.muni.pa165.secretagency.service.config;

import cz.fi.muni.pa165.secretagency.entity.Agent;
import cz.fi.muni.pa165.secretagency.service.dto.AgentDTO;
import org.dozer.DozerBeanMapper;
import cz.fi.muni.pa165.secretagency.SecretAgencyPersistenceApplicationContext;
import cz.fi.muni.pa165.secretagency.service.AgentServiceImpl;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Author: Adam Kral <433328>
 * Date: 11/19/18
 * Time: 2:59 PM
 */
@Configuration
@Import(SecretAgencyPersistenceApplicationContext.class)
@ComponentScan(basePackageClasses={AgentServiceImpl.class})
public class ServiceConfiguration {
    @Bean
    public Mapper dozer() {
        DozerBeanMapper dozer = new DozerBeanMapper();
        dozer.addMapping(new DozerCustomConfig());
        return dozer;
    }

    public class DozerCustomConfig extends BeanMappingBuilder {
        @Override
        protected void configure() {
            mapping(Agent.class, AgentDTO.class);
        }
    }
}

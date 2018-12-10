package cz.muni.fi.pa165.secretagency;

import cz.fi.muni.pa165.secretagency.service.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
 * Loads init data.
 *
 * @author Jan Pavlu
 */
@Configuration
@Import(ServiceConfiguration.class)
@ComponentScan(basePackageClasses = {InitDataFacadeImpl.class})
public class InitDataConfiguration {

    @Autowired
    private InitDataFacade initDataFacade;

    @PostConstruct
    public void loadData() {
        initDataFacade.loadInitData();
    }
}

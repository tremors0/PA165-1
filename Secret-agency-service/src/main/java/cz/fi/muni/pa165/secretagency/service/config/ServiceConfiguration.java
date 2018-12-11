package cz.fi.muni.pa165.secretagency.service.config;

import cz.fi.muni.pa165.secretagency.SecretAgencyPersistenceApplicationContext;
import cz.fi.muni.pa165.secretagency.dto.AgentDTO;
import cz.fi.muni.pa165.secretagency.dto.DepartmentDTO;
import cz.fi.muni.pa165.secretagency.dto.MissionDTO;
import cz.fi.muni.pa165.secretagency.entity.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Author: Adam Kral <433328>, Jan Pavlu (487548)
 */
@Configuration
@Import(SecretAgencyPersistenceApplicationContext.class)
@ComponentScan(basePackages = {"cz.fi.muni.pa165.secretagency"})
public class ServiceConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);

        // convert nested collection of other DTOs to collection of their ids
        Converter<Collection<Agent>, Set<Long>> agentConvertor = createConvertor();
        Converter<Collection<Mission>, Set<Long>> missionConvertor = createConvertor();
        Converter<Collection<Report>, Set<Long>> reportConvertor = createConvertor();

        // mapping from Department -> DepartmentDTO
        modelMapper.createTypeMap(Department.class, DepartmentDTO.class)
                .addMappings(mapper -> mapper.using(agentConvertor).map(Department::getAgents,
                                                                        DepartmentDTO::setAgentIds));

        // mapping from Agent -> AgentDTO
        modelMapper.createTypeMap(Agent.class, AgentDTO.class)
                .addMappings(mapper -> mapper.using(missionConvertor).map(Agent::getMissions,
                        AgentDTO::setMissionIds))
                .addMappings(mapper -> mapper.using(reportConvertor).map(Agent::getReports,
                                                                         AgentDTO::setReportIds));
        // mapping from Mission -> MissionDTO
        modelMapper.createTypeMap(Mission.class, MissionDTO.class)
                .addMappings(mapper -> mapper.using(agentConvertor).map(Mission::getAgents,
                                                                        MissionDTO::setAgentIds))
                .addMappings(mapper -> mapper.using(reportConvertor).map(Mission::getReports,
                                                                         MissionDTO::setReportIds));
        return modelMapper;
    }

    /**
     * Creates convertor for mapping entities to DTOs. If entity has attribute, which is collection of other
     *   entities, it gets converted to set of ids of those entities. The reason is to avoid circular
     *   dependencies between DTOs.
     * @param <T> entity, which is converted
     * @return convertor
     */
    private <T extends Identifiable<Long>> Converter<Collection<T>, Set<Long>> createConvertor() {
        @SuppressWarnings("Convert2MethodRef")
        Converter<Collection<T>, Set<Long>> converter = mappingContext -> mappingContext.getSource().stream()
                .map(t -> t.getId()).collect(Collectors.toSet());
        return converter;
    }
}

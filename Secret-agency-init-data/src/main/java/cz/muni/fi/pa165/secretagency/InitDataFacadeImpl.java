package cz.muni.fi.pa165.secretagency;

import cz.fi.muni.pa165.secretagency.entity.Agent;
import cz.fi.muni.pa165.secretagency.entity.Department;
import cz.fi.muni.pa165.secretagency.entity.Mission;
import cz.fi.muni.pa165.secretagency.entity.Report;
import cz.fi.muni.pa165.secretagency.enums.*;
import cz.fi.muni.pa165.secretagency.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of init data facade. Loads data into in memory database.
 *
 * @author Jan Pavlu (487548)
 */
@Service
public class InitDataFacadeImpl implements InitDataFacade {

    @Autowired
    private AgentService agentService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private MissionService missionService;

    @Autowired
    private ReportService reportService;

    @Override
    @Transactional
    public void loadInitData() {
        // creating agents
        Agent babis = createAgent("Andrej Babis", LocalDate.of(1970, 6, 7),
                getSetOfLanguages(LanguageEnum.SK), AgentRankEnum.AGENT_IN_CHARGE, "Bures",
                "nikdyNeodstoupim");
        Agent bond = createAgent("James Bond", LocalDate.of(1950, 2,28),
                getSetOfLanguages(LanguageEnum.EN, LanguageEnum.JP), AgentRankEnum.SENIOR, "007",
                "Shaken,notStirred");
        Agent orange = createAgent("John A. Thornburn", LocalDate.of(1905, 3, 10),
                getSetOfLanguages(LanguageEnum.EN), AgentRankEnum.JUNIOR, "Orange", "vietnam");
        Agent zMan = createAgent("Milos Zeman", LocalDate.of(1938, 3, 7),
                getSetOfLanguages(LanguageEnum.CZ, LanguageEnum.RU), AgentRankEnum.SENIOR, "Ovar",
                "putinJeKamosNeNepritel");

        // creating departments
        Department prague = createDepartment("Prague", "Czech Republic", 50.08804, 14.42076,
                DepartmentSpecialization.INTERNATIONAL_RELATIONSHIP);
        Department london = createDepartment("London", "Great Britain", 51.509865, -0.118092,
                DepartmentSpecialization.ASSASSINATION);

        // creating missions
        Mission transportBabisJrToKrym = createMission(47.304324, 39.521161, MissionTypeEnum.SABOTAGE,
                LocalDate.of(2017, 5, 1), LocalDate.of(2018, 11, 1));
        Mission getDrunkDuringCeremony = createMission(50.08804, 14.42076, MissionTypeEnum.SABOTAGE,
                LocalDate.of(2014, 11, 7), LocalDate.of(2014, 11, 7));

        // creating reports
        Report transportBabisJrToKrymReport = createReport("Transport of Babis Jr to Krym was successful.",
                LocalDate.of(2018, 11, 1), ReportStatus.NEW, MissionResultReportEnum.COMPLETED);
        Report getDrunkReport = createReport("I got really drunk. Almost threw up on crown jewels.",
                LocalDate.of(2014, 11, 10), ReportStatus.APPROVED, MissionResultReportEnum.COMPLETED);

        // add relations between entities
        // department + agent
        prague.addAgent(babis);
        prague.addAgent(zMan);
        london.addAgent(bond);
        london.addAgent(orange);

        // mission + agent + report
        transportBabisJrToKrym.addAgent(babis);
        transportBabisJrToKrym.addReport(transportBabisJrToKrymReport, babis);
        getDrunkDuringCeremony.addAgent(zMan);
        getDrunkDuringCeremony.addReport(getDrunkReport, zMan);

        // save entities
        // order of loading entities into database: Department -> Agent -> Mission -> Report
        saveDepartments(prague, london);
        saveAgents(babis, bond, zMan, orange);
        saveMissions(transportBabisJrToKrym, getDrunkDuringCeremony);
        saveReports(getDrunkReport, transportBabisJrToKrymReport);
    }

    /**
     * Creates agent without relations to other entities. This method does not save created agent.
     * @return created agent
     */
    private Agent createAgent(String name, LocalDate birthday, Set<LanguageEnum> languages,
                             AgentRankEnum rank, String codeName, String password) {
        Agent agent = new Agent();
        agent.setName(name);
        agent.setBirthDate(birthday);
        agent.setLanguages(languages);
        agent.setRank(rank);
        agent.setCodeName(codeName);
        agent.setPasswordHash(AgentServiceImpl.createHash(password));
        return agent;
    }

    /**
     * Creates department without relations to other entities. This method does not save created department.
     * @return created department
     */
    private Department createDepartment(String city, String country, Double latitude,
                                       Double longitude, DepartmentSpecialization specialization) {
        Department department = new Department();
        department.setCity(city);
        department.setCountry(country);
        department.setLatitude(latitude);
        department.setLongitude(longitude);
        department.setSpecialization(specialization);
        return department;
    }

    /**
     * Creates mission without relations to other entities. This method does not save created mission.
     * @return created mission
     */
    private Mission createMission(Double latitude, Double longitude, MissionTypeEnum missionType, LocalDate started,
                                 LocalDate ended) {
        Mission mission = new Mission();
        mission.setLatitude(latitude);
        mission.setLongitude(longitude);
        mission.setMissionType(missionType);
        mission.setStarted(started);
        mission.setEnded(ended);
        return mission;
    }

    /**
     * Creates report without relations to other entities. This method does not save created report.
     * @return created report
     */
    private Report createReport(String text, LocalDate date, ReportStatus status,
                               MissionResultReportEnum missionResult) {
        Report report = new Report();
        report.setText(text);
        report.setDate(date);
        report.setReportStatus(status);
        report.setMissionResult(missionResult);
        return report;
    }

    private Set<LanguageEnum> getSetOfLanguages(LanguageEnum... languages) {
        return new HashSet<>(Arrays.asList(languages));
    }

    private void saveAgents(Agent... agents) {
        for (Agent agent : agents) {
            agentService.save(agent);
        }
    }

    private void saveDepartments(Department... departments) {
        for (Department department : departments) {
            departmentService.save(department);
        }
    }

    private void saveMissions(Mission... missions) {
        for (Mission mission : missions) {
            missionService.save(mission);
        }
    }

    private void saveReports(Report... reports) {
        for (Report report : reports) {
            reportService.save(report);
        }
    }
}

package cz.fi.muni.pa165.secretagency.entity;

import cz.fi.muni.pa165.secretagency.enums.AgentRankEnum;
import cz.fi.muni.pa165.secretagency.enums.LanguageEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;

/**
 * Agent entity for the Secret agency project.
 *
 * @author (Adam Kral<433328>)
 */
@Entity
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private LocalDate birthDate;

    @ElementCollection
    @NotNull
    private Set<LanguageEnum> languages = new HashSet<>();

    @Enumerated
    @NotNull
    private AgentRankEnum rank;

    @NotNull
    private String codeName;

    @ManyToMany
    @JoinTable(name = "agent_mission",
            joinColumns = @JoinColumn(name = "agent_id"),
            inverseJoinColumns = @JoinColumn(name = "mission_id")
    )
    private List<Mission> missions = new ArrayList<Mission>();

    @ManyToOne
    @JoinColumn(name = "department_id")
    @NotNull
    private Department department;

    @OneToMany
    private List<Report> reports = new ArrayList<>();

    /**
     *
     * @param id of agent
     * @param name of agent
     * @param birthDate of agent
     * @param languages agent can speak these languages
     * @param rank of agent
     */
    public Agent(Long id, String name, LocalDate birthDate, Set<LanguageEnum> languages, AgentRankEnum rank, String codeName, List<Mission> missions, Department department, List<Report> reports) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.languages = languages;
        this.rank = rank;
        this.codeName = codeName;
        this.missions = missions;
        this.department = department;
        this.reports = reports;
    }

    /**
     * @param id of agent
     */
    public Agent(Long id) {
        this.id = id;
    }

    /** Create empty Agent **/
    public Agent() {}

    /**
     * @return Agent's id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id agent's id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return agent'a name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name agent's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return agent's birth date
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * @return agents' known languages
     */
    public Set<LanguageEnum> getLanguages() {
        return languages;
    }

    /**
     * @param languages agent's newly learned languages
     */
    public void setLanguages(Set<LanguageEnum> languages) {
        this.languages = languages;
    }

    /**
     * @param language agent's newly learned language
     */
    public void addLanguage(LanguageEnum language) {
        this.languages.add(language);
    }

    /**
     * @return agents' rank
     */
    public AgentRankEnum getRank() {
        return rank;
    }

    /**
     * @param rank agents rank
     */
    public void setRank(AgentRankEnum rank) {
        this.rank = rank;
    }

    /**
     * @param birthDate agent's birth date
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return agent's code name
     */
    public String getCodeName() {
        return codeName;
    }

    /**
     * @param codeName set agent's code name
     */
    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    /**
     * @return List of agent's missions
     */
    public List<Mission> getMissions() {
        return missions;
    }

    /**
     * @param mission adds mission to agent's mission
     * @throws NullPointerException when mission is null
     */
    public void addMission(Mission mission) {
        if (mission == null) {
            throw new NullPointerException("Cannot add mission to agent if mission is null");
        }
        this.missions.add(mission);
    }

    /**
     * @return get department in which agent is working
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * @param department set department in which agent is working
     * @throws NullPointerException when department is null
     */
    public void setDepartment(Department department) {
        if (department == null) {
            throw new NullPointerException("Cannot department for agent when department is null");
        }
        this.department = department;
    }


    /**
     * Get all agent's reports
     * @return all reports of agent
     */
    public List<Report> getReports() {
        return reports;
    }

    /**
     * Add report about a given mission
     * @param report agent's written report
     * @param mission mission where report should be added
     * @throws NullPointerException when report or mission is null
     */
    public void addReport(Report report, Mission mission) {
        if (report == null) {
            throw new NullPointerException("Cannot add report when report is null");
        }

        if (mission == null) {
            throw new NullPointerException("Cannot add report when mission is null");
        }

        this.reports.add(report);
        mission.addReport(report);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Agent)) return false;
        Agent agent = (Agent) o;
        return Objects.equals(getName(), agent.getName()) &&
                Objects.equals(getBirthDate(), agent.getBirthDate()) &&
                Objects.equals(getLanguages(), agent.getLanguages()) &&
                Objects.equals(getRank(), agent.getRank()) &&
                Objects.equals(getCodeName(), agent.getCodeName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getBirthDate(), getLanguages(), getRank(), getCodeName());
    }
}

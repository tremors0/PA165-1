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
@SuppressWarnings("JpaDataSourceORMInspection")
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
    @Column(unique = true)
    private String codeName;

    private String passwordHash;

    @ManyToMany
    @JoinTable(name = "agent_mission",
            joinColumns = @JoinColumn(name = "agent_id"),
            inverseJoinColumns = @JoinColumn(name = "mission_id")
    )
    private List<Mission> missions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "department_id")
    @NotNull
    private Department department;

    @OneToMany(mappedBy = "agent", fetch = FetchType.LAZY)
    private List<Report> reports = new ArrayList<>();

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
        return Collections.unmodifiableSet(languages);
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
     * @return hash of the password
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * @param passwordHash hash of the password
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * @return List of agent's missions
     */
    public List<Mission> getMissions() {
        return missions;
    }

    /**
     * <b>SHOULD NOT BE CALLED MANUALLY.</b><br>
     * If you want to add agent to mission use {@link Mission#addAgent(Agent)}
     * @param mission selected mission
     * @throws NullPointerException when mission is null
     */
    public void addMission(Mission mission) {
        if (mission == null) {
            throw new NullPointerException("Cannot add mission to agent when mission is null");
        }
        this.missions.add(mission);
    }

    /**
     * <b>SHOULD NOT BE CALLED MANUALLY.</b><br>
     * If you want to remove agent from mission use {@link Mission#removeAgent(Agent)}
     * @param mission selected mission
     * @throws NullPointerException when mission is null
     */
    public void removeMission(Mission mission) { this.missions.remove(mission); }

    /**
     * @return get department in which agent is working
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * @param department set department in which agent is working
     */
    public void setDepartment(Department department) {
        this.department = department;
    }


    /**
     * Get all agent's reports
     * @return all reports of agent
     */
    public List<Report> getReports() {
        return Collections.unmodifiableList(reports);
    }

    /**
     * <b>SHOULD NOT BE CALLED MANUALLY.</b><br>
     * If you want to add report from agent, use {@link Mission#addReport(Report, Agent)}
     * @param report agent's written report
     * @throws NullPointerException when report or mission is null
     */
    public void addReport(Report report) {
        if (report == null) {
            throw new NullPointerException("Cannot add report when report is null");
        }
        this.reports.add(report);
    }

    /**
     * <b>SHOULD NOT BE CALLED MANUALLY.</b><br>
     *  If you want to add report from agent, use {@link Mission#removeReport(Report, Agent)}
     * @param report report
     * @throws NullPointerException when report is null
     */
    public void removeReport(Report report) {
        if (report == null) {
            throw new NullPointerException("Cannot add report when report is null");
        }
        this.reports.remove(report);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Agent)) return false;
        Agent agent = (Agent) o;
        return Objects.equals(getCodeName(), agent.getCodeName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodeName());
    }
}

package cz.fi.muni.pa165.secretagency.dto;

import cz.fi.muni.pa165.secretagency.enums.AgentRankEnum;
import cz.fi.muni.pa165.secretagency.enums.LanguageEnum;

import java.time.LocalDate;
import java.util.*;

/**
 * Author: Adam Kral <433328>
 * Date: 11/25/18
 * Time: 1:06 PM
 */
public class AgentDTO {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private Set<LanguageEnum> languages = new HashSet<>();
    private AgentRankEnum rank;
    private String codeName;
    private String passwordHash;
    private DepartmentDTO department;
    private List<Long> missionIds = new ArrayList<>();
    private List<Long> reportIds = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Set<LanguageEnum> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<LanguageEnum> languages) {
        this.languages = languages;
    }

    public AgentRankEnum getRank() {
        return rank;
    }

    public void setRank(AgentRankEnum rank) {
        this.rank = rank;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    public List<Long> getMissionIds() {
        return missionIds;
    }

    public void setMissionIds(List<Long> missionIds) {
        this.missionIds = missionIds;
    }

    public List<Long> getReportIds() {
        return reportIds;
    }

    public void setReportIds(List<Long> reportIds) {
        this.reportIds = reportIds;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AgentDTO)) return false;
        AgentDTO agentDTO = (AgentDTO) o;
        return Objects.equals(getCodeName(), agentDTO.getCodeName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodeName());
    }

    @Override
    public String toString() {
        return "AgentDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", languages=" + languages +
                ", rank=" + rank +
                ", codeName='" + codeName + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", department=" + department +
                ", missionIds=" + missionIds +
                ", reportIds=" + reportIds +
                '}';
    }
}


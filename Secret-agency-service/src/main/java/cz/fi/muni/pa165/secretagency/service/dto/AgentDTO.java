package cz.fi.muni.pa165.secretagency.service.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import cz.fi.muni.pa165.secretagency.enums.AgentRankEnum;
import cz.fi.muni.pa165.secretagency.enums.LanguageEnum;

/**
 * Agent DTO
 *
 * @author (Adam Kral<433328>)
 */
public class AgentDTO {
    private Long id;

    private String name;

    private LocalDate birthDate;

    private Set<LanguageEnum> languages = new HashSet<LanguageEnum>();

    private AgentRankEnum rank;

    private String codeName;

//    TODO: probably need MissionDTO, ReportDTO & DepartmentDTO

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AgentDTO)) return false;
        AgentDTO agentDTO = (AgentDTO) o;
        return Objects.equals(getId(), agentDTO.getId()) &&
                Objects.equals(getName(), agentDTO.getName()) &&
                Objects.equals(getBirthDate(), agentDTO.getBirthDate()) &&
                Objects.equals(getLanguages(), agentDTO.getLanguages()) &&
                getRank() == agentDTO.getRank() &&
                Objects.equals(getCodeName(), agentDTO.getCodeName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getBirthDate(), getLanguages(), getRank(), getCodeName());
    }
}

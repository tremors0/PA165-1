package cz.fi.muni.pa165.secretagency.dto;

import cz.fi.muni.pa165.secretagency.enums.DepartmentSpecialization;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * DTO for editing specialization of existing department
 */
public class DepartmentUpdateSpecializationDTO {

    @NotNull
    private Long departmentId;

    @NotNull
    private DepartmentSpecialization specialization;

    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }

    public DepartmentSpecialization getSpecialization() { return specialization; }
    public void setSpecialization(DepartmentSpecialization specialization) { this.specialization = specialization; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DepartmentUpdateSpecializationDTO)) return false;
        DepartmentUpdateSpecializationDTO that = (DepartmentUpdateSpecializationDTO) o;
        return Objects.equals(getDepartmentId(), that.getDepartmentId()) &&
                getSpecialization() == that.getSpecialization();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDepartmentId(), getSpecialization());
    }

    @Override
    public String toString() {
        return "DepartmentUpdateSepcializationDTO{" +
                "departmentId=" + departmentId +
                ", specialization=" + specialization +
                '}';
    }
}

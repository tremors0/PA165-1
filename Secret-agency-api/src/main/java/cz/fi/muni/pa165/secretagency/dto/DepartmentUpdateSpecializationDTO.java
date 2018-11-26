package cz.fi.muni.pa165.secretagency.dto;

import cz.fi.muni.pa165.secretagency.enums.DepartmentSpecialization;

import javax.validation.constraints.NotNull;

/**
 * DTO for editing specialization of existing department
 *
 * @author Milos Silhar (433614)
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
    public String toString() {
        return "DepartmentUpdateSepcializationDTO{" +
                "departmentId=" + departmentId +
                ", specialization=" + specialization +
                '}';
    }
}

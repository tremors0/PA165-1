package cz.fi.muni.pa165.secretagency.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO for editing text of the existing report. There is no need to override equals and hashcode
 *    because this class should not be used in a collection.
 *
 * @author Jan Pavlu
 */
public class ReportUpdateTextDTO {

    @NotNull
    private Long id;

    @NotNull
    @Size(min = 10)
    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ReportUpdateTextDTO{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}

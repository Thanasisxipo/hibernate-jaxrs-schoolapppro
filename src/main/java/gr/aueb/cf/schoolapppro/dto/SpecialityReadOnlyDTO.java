package gr.aueb.cf.schoolapppro.dto;

import gr.aueb.cf.schoolapppro.model.Teacher;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SpecialityReadOnlyDTO extends BaseDTO {

    private String speciality;
    private Set<Teacher> teachers;

    public SpecialityReadOnlyDTO(@NotNull Long id, String speciality, Set<Teacher> teachers) {
        this.setId(id);
        this.speciality = speciality;
        this.teachers = teachers;
    }
}

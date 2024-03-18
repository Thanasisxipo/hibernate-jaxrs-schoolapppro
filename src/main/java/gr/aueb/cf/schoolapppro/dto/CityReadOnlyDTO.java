package gr.aueb.cf.schoolapppro.dto;

import gr.aueb.cf.schoolapppro.model.Student;
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
public class CityReadOnlyDTO extends BaseDTO {
    private String city;
    private Set<Student> students;

    public CityReadOnlyDTO(@NotNull Long id, String city, Set<Student> students) {
        this.setId(id);
        this.city = city;
        this.students = students;
    }
}

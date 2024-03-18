package gr.aueb.cf.schoolapppro.dto;

import gr.aueb.cf.schoolapppro.model.Student;
import gr.aueb.cf.schoolapppro.model.Teacher;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserReadOnlyDTO extends BaseDTO {

    private String username;
    private String password;
    private Teacher teacher;
    private Student student;

    public UserReadOnlyDTO(@NotNull Long id, String username, String password, Teacher teacher, Student student) {
        this.setId(id);
        this.username = username;
        this.password = password;
        this.teacher = teacher;
        this.student = student;
    }
}

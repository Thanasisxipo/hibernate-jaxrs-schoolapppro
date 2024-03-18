package gr.aueb.cf.schoolapppro.dto;

import gr.aueb.cf.schoolapppro.model.Meeting;
import gr.aueb.cf.schoolapppro.model.Speciality;
import gr.aueb.cf.schoolapppro.model.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeacherUpdateDTO extends BaseDTO {

    @NotNull(message = "Error firstname should not be null")
    @Size(min = 2, max = 45, message = "Error in firstname lenght")
    private String firstname;

    @NotNull(message = "Error firstname should not be null")
    @Size(min = 2, max = 45, message = "Error in lastname lenght")
    private String lastname;

    @NotNull(message = "Error ssn should not be null")
    @Size(min = 9, max = 9, message = "Error in ssn lenght")
    private  String ssn;

    private Speciality speciality;
    private User user;
    private Set<Meeting> meetings;

    public TeacherUpdateDTO(@NotNull Long id, String firstname, String lastname, String ssn, Speciality speciality, User user, Set<Meeting> meetings) {
        this.setId(id);
        this.firstname = firstname;
        this.lastname = lastname;
        this.ssn = ssn;
        this.speciality = speciality;
        this.user = user;
        this.meetings = meetings;
    }
}

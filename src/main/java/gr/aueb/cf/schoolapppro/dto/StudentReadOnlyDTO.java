package gr.aueb.cf.schoolapppro.dto;

import gr.aueb.cf.schoolapppro.model.City;
import gr.aueb.cf.schoolapppro.model.Meeting;
import gr.aueb.cf.schoolapppro.model.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentReadOnlyDTO extends BaseDTO {

    private String firstname;
    private String lastname;
    private String gender;
    private Date birthDate;
    private City city;
    private User user;
    private Set<Meeting> meetings;

    public StudentReadOnlyDTO(@NotNull Long id, String firstname, String lastname, String gender, Date birthDate, City city, User user, Set<Meeting> meetings) {
        this.setId(id);
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.city = city;
        this.user = user;
        this.meetings = meetings;
    }
}

package gr.aueb.cf.schoolapppro.dto;

import gr.aueb.cf.schoolapppro.model.Student;
import gr.aueb.cf.schoolapppro.model.Teacher;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MeetingUpdateDTO extends BaseDTO {

    @NotNull(message = "Error meeting room should not be null")
    @Size(min = 5, max = 45, message = "Error in meeting room lenght")
    private String meetingRoom;

    @NotNull(message = "Error meeting date should not be null")
    private Date meetingDate;

    private Teacher teacher;
    private Student student;

    public MeetingUpdateDTO(@NotNull Long id, String meetingRoom, Date meetingDate, Teacher teacher, Student student) {
        this.setId(id);
        this.meetingRoom = meetingRoom;
        this.meetingDate = meetingDate;
        this.teacher = teacher;
        this.student = student;
    }
}

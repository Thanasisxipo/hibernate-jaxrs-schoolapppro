package gr.aueb.cf.schoolapppro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//import java.sql.Date;

import java.util.Date;

@Entity
@Table(name = "meetings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "meeting_room", length = 45, nullable = false)
    private String meetingRoom;

    @Column(name = "meeting_date", nullable = false)
    private Date meetingDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

}

package gr.aueb.cf.schoolapppro.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teachers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String firstname;

    @Column(length = 45, nullable = false)
    private String lastname;

    @Column(length = 9, nullable = false)
    private String ssn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "speciality_id", referencedColumnName = "id")
    private Speciality speciality;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    private User user;

    @OneToMany(mappedBy = "teacher")
    @Getter(AccessLevel.PROTECTED)
    private Set<Meeting> meetings = new HashSet<>();

    public Set<Meeting> getAllMeetings() {
        return Collections.unmodifiableSet(meetings);
    }

    public void addMeeting(Meeting meeting) {
        this.meetings.add(meeting);
        meeting.setTeacher(this);
    }
}

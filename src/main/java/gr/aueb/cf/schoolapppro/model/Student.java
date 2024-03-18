package gr.aueb.cf.schoolapppro.model;

import jakarta.persistence.*;
import lombok.*;

//import java.sql.Date;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String firstname;

    @Column(length = 45, nullable = false)
    private String lastname;

    @Column(length = 1)
    private String gender;

    @Column(name = "birth_date")
    private Date birthDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    private User user;

    @OneToMany(mappedBy = "student")
    @Getter(AccessLevel.PROTECTED)
    private Set<Meeting> meetings = new HashSet<>();

    public Set<Meeting> getAllMeetings() {
        return Collections.unmodifiableSet(meetings);
    }

    public void addMeeting(Meeting meeting) {
        this.meetings.add(meeting);
        meeting.setStudent(this);
    }
}

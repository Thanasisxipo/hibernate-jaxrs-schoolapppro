package gr.aueb.cf.schoolapppro.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "specialities")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Speciality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String speciality;

    @OneToMany(mappedBy = "speciality")
    @Getter(AccessLevel.PROTECTED)
    private Set<Teacher> teachers = new HashSet<>();

    public Set<Teacher> getAllTeachers() {
        return Collections.unmodifiableSet(teachers);
    }

    public void addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
        teacher.setSpeciality(this);
    }
}

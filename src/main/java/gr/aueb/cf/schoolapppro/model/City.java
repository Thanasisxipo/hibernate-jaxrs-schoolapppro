package gr.aueb.cf.schoolapppro.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cities")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String city;

    @OneToMany(mappedBy = "city")
    @Getter(AccessLevel.PROTECTED)
    private Set<Student> students = new HashSet<>();

    public Set<Student> getAllStudents() {
        return Collections.unmodifiableSet(students);
    }

    public void addStudent(Student student) {
        this.students.add(student);
        student.setCity(this);
    }
}

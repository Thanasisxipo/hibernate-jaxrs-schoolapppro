package gr.aueb.cf.schoolapppro.dao;

import gr.aueb.cf.schoolapppro.model.Student;

import java.util.List;

public interface IStudentDAO {
    Student insert(Student student);
    Student update(Student student);
    void delete(Long id);
    List<Student> getByLastname(String lastname);
    Student getById(Long id);
}

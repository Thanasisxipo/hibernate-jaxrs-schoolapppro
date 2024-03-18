package gr.aueb.cf.schoolapppro.dao;

import gr.aueb.cf.schoolapppro.model.Teacher;

import java.util.List;

public interface ITeacherDAO {
    Teacher insert(Teacher teacher);
    Teacher update(Teacher teacher);
    void delete(Long id);
    List<Teacher> getByLastname(String lastname);
    Teacher getById(Long id);
}

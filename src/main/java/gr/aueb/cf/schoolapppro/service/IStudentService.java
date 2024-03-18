package gr.aueb.cf.schoolapppro.service;

import gr.aueb.cf.schoolapppro.dto.StudentInsertDTO;
import gr.aueb.cf.schoolapppro.dto.StudentUpdateDTO;
import gr.aueb.cf.schoolapppro.model.Student;
import gr.aueb.cf.schoolapppro.service.exception.EntityNotFoundException;

import java.util.List;

public interface IStudentService {
    Student insertStudent(StudentInsertDTO dto) throws Exception;
    Student updateStudent(StudentUpdateDTO dto) throws EntityNotFoundException;
    void deleteStudent(Long id) throws EntityNotFoundException;
    List<Student> getStudentByLastname(String lastname) throws EntityNotFoundException;
    Student getById(Long id) throws EntityNotFoundException;
}

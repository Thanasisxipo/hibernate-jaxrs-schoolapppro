package gr.aueb.cf.schoolapppro.service;

import gr.aueb.cf.schoolapppro.dao.IStudentDAO;
import gr.aueb.cf.schoolapppro.dto.StudentInsertDTO;
import gr.aueb.cf.schoolapppro.dto.StudentUpdateDTO;
import gr.aueb.cf.schoolapppro.mapper.Mapper;
import gr.aueb.cf.schoolapppro.model.Student;
import gr.aueb.cf.schoolapppro.service.exception.EntityNotFoundException;
import gr.aueb.cf.schoolapppro.service.util.JPAHelper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Provider
@ApplicationScoped
public class StudentServiceImpl implements IStudentService{
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Inject
    private IStudentDAO dao;

    @Override
    public Student insertStudent(StudentInsertDTO dto) throws Exception {
        Student student = null;

        try {
            JPAHelper.beginTransaction();
            student = Mapper.mapToStudent(dto);
            student = dao.insert(student);
            if (student.getId() == null) {
                throw new Exception("Insert Error");
            }
            JPAHelper.commitTransaction();
            logger.info("Student with id " + student.getId() + " was inserted.");
        } catch (Exception e) {
            JPAHelper.rollbackTransaction();
            logger.error("Error - student not inserted -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return student;
    }

    @Override
    public Student updateStudent(StudentUpdateDTO dto) throws EntityNotFoundException {
        Student studentToUpdate = null;
        Student updatedStudent = null;

        try {
            JPAHelper.beginTransaction();
            Optional.ofNullable(dao.getById(dto.getId()))
                    .orElseThrow(() -> new EntityNotFoundException(Student.class, dto.getId()));
            studentToUpdate = Mapper.mapToStudent(dto);
            updatedStudent = dao.update(studentToUpdate);
            JPAHelper.commitTransaction();
            logger.info("Student with id " + updatedStudent.getId() + " was updated.");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.error("Error - student was not found -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return updatedStudent;
    }

    @Override
    public void deleteStudent(Long id) throws EntityNotFoundException {
        try {
            JPAHelper.beginTransaction();
            Optional.ofNullable(dao.getById(id)).orElseThrow(() -> new EntityNotFoundException(Student.class, id));
            dao.delete(id);
            JPAHelper.commitTransaction();
            logger.info("Student with id " + id + " was deleted.");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - student was not found -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public List<Student> getStudentByLastname(String lastname) throws EntityNotFoundException {
        List<Student> students;
        try {
            JPAHelper.beginTransaction();
            students = Optional.of(dao.getByLastname(lastname))
                    .orElseThrow(() -> new EntityNotFoundException(List.class, 0L));
            JPAHelper.commitTransaction();
            logger.info("Students were found");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - students were not found -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return students;
    }

    @Override
    public Student getById(Long id) throws EntityNotFoundException {
        Student student = null;

        try {
            JPAHelper.beginTransaction();
            Optional.ofNullable(dao.getById(id)).orElseThrow(() -> new EntityNotFoundException(Student.class, id));
            student = dao.getById(id);
            JPAHelper.commitTransaction();
            logger.info("Student with id " + id + " was found.");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - student was not found -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return student;
    }
}

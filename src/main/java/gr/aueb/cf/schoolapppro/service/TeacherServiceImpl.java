package gr.aueb.cf.schoolapppro.service;

import gr.aueb.cf.schoolapppro.dao.ITeacherDAO;
import gr.aueb.cf.schoolapppro.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapppro.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolapppro.mapper.Mapper;
import gr.aueb.cf.schoolapppro.model.Teacher;
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
public class TeacherServiceImpl implements ITeacherService{
    private static final Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);

    @Inject
    private ITeacherDAO dao;

    @Override
    public Teacher insertTeacher(TeacherInsertDTO dto) throws Exception {
        Teacher teacher = null;

        try {
            JPAHelper.beginTransaction();
            teacher = Mapper.mapToTeacher(dto);
            teacher = dao.insert(teacher);
            if (teacher.getId() == null) {
                throw new Exception("Insert Error");
            }
            JPAHelper.commitTransaction();
            logger.info("Teacher with id " + teacher.getId() + " was inserted.");
        } catch (Exception e) {
            JPAHelper.rollbackTransaction();
            logger.error("Error - teacher not inserted -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return teacher;
    }

    @Override
    public Teacher updateTeacher(TeacherUpdateDTO dto) throws EntityNotFoundException {
        Teacher teacherToUpdate = null;
        Teacher updatedTeacher = null;

        try {
            JPAHelper.beginTransaction();
            Optional.ofNullable(dao.getById(dto.getId()))
                    .orElseThrow(() -> new EntityNotFoundException(Teacher.class, dto.getId()));
            teacherToUpdate = Mapper.mapToTeacher(dto);
            updatedTeacher = dao.update(teacherToUpdate);
            JPAHelper.commitTransaction();
            logger.info("Teacher with id " + updatedTeacher.getId() + " was updated.");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.error("Error - teacher was not found -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return updatedTeacher;
    }

    @Override
    public void deleteTeacher(Long id) throws EntityNotFoundException {
        try {
            JPAHelper.beginTransaction();
            Optional.ofNullable(dao.getById(id)).orElseThrow(() -> new EntityNotFoundException(Teacher.class, id));
            dao.delete(id);
            JPAHelper.commitTransaction();
            logger.info("Teacher with id " + id + " was deleted.");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - teacher was not found -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public List<Teacher> getTeacherByLastname(String lastname) throws EntityNotFoundException {
        List<Teacher> teachers;
        try {
            JPAHelper.beginTransaction();
            teachers = Optional.of(dao.getByLastname(lastname))
                    .orElseThrow(() -> new EntityNotFoundException(List.class, 0L));
            JPAHelper.commitTransaction();
            logger.info("Teachers were found");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - teachers were not found -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return teachers;
    }

    @Override
    public Teacher getTeacherById(Long id) throws EntityNotFoundException {
        Teacher teacher = null;

        try {
            JPAHelper.beginTransaction();
            Optional.ofNullable(dao.getById(id)).orElseThrow(() -> new EntityNotFoundException(Teacher.class, id));
            teacher = dao.getById(id);
            JPAHelper.commitTransaction();
            logger.info("Teacher with id " + id + " was found.");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - teacher was not found -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return teacher;
    }
}

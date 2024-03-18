package gr.aueb.cf.schoolapppro.service;

import gr.aueb.cf.schoolapppro.dao.ISpecialityDAO;
import gr.aueb.cf.schoolapppro.dto.SpecialityInsertDTO;
import gr.aueb.cf.schoolapppro.dto.SpecialityUpdateDTO;
import gr.aueb.cf.schoolapppro.mapper.Mapper;
import gr.aueb.cf.schoolapppro.model.Speciality;
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
public class SpecialityServiceImpl implements ISpecialityService{
    private static final Logger logger = LoggerFactory.getLogger(SpecialityServiceImpl.class);

    @Inject
    private ISpecialityDAO dao;

    @Override
    public Speciality insertSpeciality(SpecialityInsertDTO dto) throws Exception {
        Speciality speciality = null;

        try {
            JPAHelper.beginTransaction();
            speciality = Mapper.mapToSpeciality(dto);
            speciality = dao.insert(speciality);
            if (speciality.getId() == null) {
                throw new Exception("Insert Error");
            }
            JPAHelper.commitTransaction();
            logger.info("Speciality with id " + speciality.getId() + " was inserted.");
        } catch (Exception e) {
            JPAHelper.rollbackTransaction();
            logger.error("Error - speciality not inserted -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return speciality;
    }

    @Override
    public Speciality updateSpeciality(SpecialityUpdateDTO dto) throws EntityNotFoundException {
        Speciality specialityToUpdate = null;
        Speciality updatedSpeciality = null;

        try {
            JPAHelper.beginTransaction();
            Optional.ofNullable(dao.getById(dto.getId()))
                    .orElseThrow(() -> new EntityNotFoundException(Speciality.class, dto.getId()));
            specialityToUpdate = Mapper.mapToSpeciality(dto);
            updatedSpeciality = dao.update(specialityToUpdate);
            JPAHelper.commitTransaction();
            logger.info("Speciality with id " + updatedSpeciality.getId() + " was updated.");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.error("Error - speciality was not found -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return updatedSpeciality;
    }

    @Override
    public void deleteSpeciality(Long id) throws EntityNotFoundException {
        try {
            JPAHelper.beginTransaction();
            Optional.ofNullable(dao.getById(id)).orElseThrow(() -> new EntityNotFoundException(Speciality.class, id));
            dao.delete(id);
            JPAHelper.commitTransaction();
            logger.info("Speciality with id " + id + " was deleted.");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - speciality was not found -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public List<Speciality> getSpecialityBySpeciality(String speciality) throws EntityNotFoundException {
        List<Speciality> specialities;
        try {
            JPAHelper.beginTransaction();
            specialities = Optional.of(dao.getBySpecialityname(speciality))
                    .orElseThrow(() -> new EntityNotFoundException(List.class, 0L));
            JPAHelper.commitTransaction();
            logger.info("Specialities were found");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - specialities were not found -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return specialities;
    }

    @Override
    public Speciality getSpecialityById(Long id) throws EntityNotFoundException {
        Speciality speciality = null;

        try {
            JPAHelper.beginTransaction();
            Optional.ofNullable(dao.getById(id)).orElseThrow(() -> new EntityNotFoundException(Speciality.class, id));
            speciality = dao.getById(id);
            JPAHelper.commitTransaction();
            logger.info("Speciality with id " + id + " was found.");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - speciality was not found -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return speciality;
    }
}

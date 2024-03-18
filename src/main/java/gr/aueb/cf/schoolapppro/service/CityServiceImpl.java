package gr.aueb.cf.schoolapppro.service;

import gr.aueb.cf.schoolapppro.dao.ICityDAO;
import gr.aueb.cf.schoolapppro.dto.CityInsertDTO;
import gr.aueb.cf.schoolapppro.dto.CityUpdateDTO;
import gr.aueb.cf.schoolapppro.mapper.Mapper;
import gr.aueb.cf.schoolapppro.model.City;
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
public class CityServiceImpl implements ICityService{
    private static final Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);

    @Inject
    private ICityDAO dao;

    @Override
    public City insertCity(CityInsertDTO dto) throws Exception {
        City city = null;

        try {
            JPAHelper.beginTransaction();
            city = Mapper.mapToCity(dto);
            city = dao.insert(city);
            if (city.getId() == null) {
                throw new Exception("Insert Error");
            }
            JPAHelper.commitTransaction();
            logger.info("City with id " + city.getId() + " was inserted.");
        } catch (Exception e) {
            JPAHelper.rollbackTransaction();
            logger.error("Error - city not inserted -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return city;
    }

    @Override
    public City updateCity(CityUpdateDTO dto) throws EntityNotFoundException {
        City cityToUpdate = null;
        City updatedCity = null;

        try {
            JPAHelper.beginTransaction();
            Optional.ofNullable(dao.getById(dto.getId()))
                    .orElseThrow(() -> new EntityNotFoundException(City.class, dto.getId()));
            cityToUpdate = Mapper.mapToCity(dto);
            updatedCity = dao.update(cityToUpdate);
            JPAHelper.commitTransaction();
            logger.info("City with id " + updatedCity.getId() + " was updated.");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.error("Error - city was not found -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return updatedCity;
    }

    @Override
    public void deleteCity(Long id) throws EntityNotFoundException {
        try {
            JPAHelper.beginTransaction();
            Optional.ofNullable(dao.getById(id)).orElseThrow(() -> new EntityNotFoundException(City.class, id));
            dao.delete(id);
            JPAHelper.commitTransaction();
            logger.info("City with id " + id + " was deleted.");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - city was not found -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public List<City> getCityByCity(String city) throws EntityNotFoundException {
        List<City> cities;
        try {
            JPAHelper.beginTransaction();
            cities = Optional.of(dao.getByCityName(city))
                    .orElseThrow(() -> new EntityNotFoundException(List.class, 0L));
            JPAHelper.commitTransaction();
            logger.info("Cities were found");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - cities were not found -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return cities;
    }

    @Override
    public City getCityById(Long id) throws EntityNotFoundException {
        City city = null;

        try {
            JPAHelper.beginTransaction();
            Optional.ofNullable(dao.getById(id)).orElseThrow(() -> new EntityNotFoundException(City.class, id));
            city = dao.getById(id);
            JPAHelper.commitTransaction();
            logger.info("City with id " + id + " was found.");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - city was not found -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return city;
    }
}

package gr.aueb.cf.schoolapppro.dao;

import gr.aueb.cf.schoolapppro.model.City;

import java.util.List;

public interface ICityDAO {
    City insert(City city);
    City update(City city);
    void delete(Long id);
    List<City> getByCityName(String city);
    City getById(Long id);
}

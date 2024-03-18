package gr.aueb.cf.schoolapppro.dao;

import gr.aueb.cf.schoolapppro.model.Speciality;

import java.util.List;

public interface ISpecialityDAO {
    Speciality insert(Speciality speciality);
    Speciality update(Speciality speciality);
    void delete(Long id);
    List<Speciality> getBySpecialityname(String speciality);
    Speciality getById(Long id);
}

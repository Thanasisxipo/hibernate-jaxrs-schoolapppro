package gr.aueb.cf.schoolapppro.service;

import gr.aueb.cf.schoolapppro.dto.SpecialityInsertDTO;
import gr.aueb.cf.schoolapppro.dto.SpecialityUpdateDTO;
import gr.aueb.cf.schoolapppro.model.Speciality;
import gr.aueb.cf.schoolapppro.service.exception.EntityNotFoundException;

import java.util.List;

public interface ISpecialityService {
    Speciality insertSpeciality(SpecialityInsertDTO dto) throws Exception;
    Speciality updateSpeciality(SpecialityUpdateDTO dto) throws EntityNotFoundException;
    void deleteSpeciality(Long id) throws EntityNotFoundException;
    List<Speciality> getSpecialityBySpeciality(String speciality) throws EntityNotFoundException;
    Speciality getSpecialityById(Long id) throws EntityNotFoundException;
}

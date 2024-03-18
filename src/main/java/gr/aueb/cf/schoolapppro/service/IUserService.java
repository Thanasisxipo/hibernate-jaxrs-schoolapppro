package gr.aueb.cf.schoolapppro.service;

import gr.aueb.cf.schoolapppro.dto.UserInsertDTO;
import gr.aueb.cf.schoolapppro.dto.UserUpdateDTO;
import gr.aueb.cf.schoolapppro.model.User;
import gr.aueb.cf.schoolapppro.service.exception.EntityNotFoundException;

import java.util.List;

public interface IUserService {
    User insertUser(UserInsertDTO dto) throws Exception;
    User updateUser(UserUpdateDTO dto) throws EntityNotFoundException;
    void deleteUser(Long id) throws EntityNotFoundException;
    List<User> getUserByUsername(String username) throws EntityNotFoundException;
    User getUserById(Long id) throws EntityNotFoundException;
}

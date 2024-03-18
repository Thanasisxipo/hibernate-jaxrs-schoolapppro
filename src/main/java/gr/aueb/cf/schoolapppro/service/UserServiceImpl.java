package gr.aueb.cf.schoolapppro.service;

import gr.aueb.cf.schoolapppro.dao.IUserDAO;
import gr.aueb.cf.schoolapppro.dto.UserInsertDTO;
import gr.aueb.cf.schoolapppro.dto.UserUpdateDTO;
import gr.aueb.cf.schoolapppro.mapper.Mapper;
import gr.aueb.cf.schoolapppro.model.Teacher;
import gr.aueb.cf.schoolapppro.model.User;
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
public class UserServiceImpl implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Inject
    private IUserDAO dao;

    @Override
    public User insertUser(UserInsertDTO dto) throws Exception {
        User user = null;

        try {
            JPAHelper.beginTransaction();
            user = Mapper.mapToUser(dto);
            user = dao.insert(user);
            if (user.getId() == null) {
                throw new Exception("Insert Error");
            }
            JPAHelper.commitTransaction();
            logger.info("User with id " + user.getId() + " was inserted.");
        } catch (Exception e) {
            JPAHelper.rollbackTransaction();
            logger.error("Error - user not inserted -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return user;
    }

    @Override
    public User updateUser(UserUpdateDTO dto) throws EntityNotFoundException {
        User userToUpdate = null;
        User updatedUser = null;

        try {
            JPAHelper.beginTransaction();
            Optional.ofNullable(dao.getById(dto.getId()))
                    .orElseThrow(() -> new EntityNotFoundException(User.class, dto.getId()));
            userToUpdate = Mapper.mapToUser(dto);
            updatedUser = dao.update(userToUpdate);
            JPAHelper.commitTransaction();
            logger.info("User with id " + updatedUser.getId() + " was updated.");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.error("Error - user was not found -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return updatedUser;
    }

    @Override
    public void deleteUser(Long id) throws EntityNotFoundException {
        try {
            JPAHelper.beginTransaction();
            Optional.ofNullable(dao.getById(id)).orElseThrow(() -> new EntityNotFoundException(User.class, id));
            dao.delete(id);
            JPAHelper.commitTransaction();
            logger.info("User with id " + id + " was deleted.");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - user was not found -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public List<User> getUserByUsername(String username) throws EntityNotFoundException {
        List<User> users;
        try {
            JPAHelper.beginTransaction();
            users = Optional.of(dao.getByUsername(username))
                    .orElseThrow(() -> new EntityNotFoundException(List.class, 0L));
            JPAHelper.commitTransaction();
            logger.info("Users were found");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - users were not found -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return users;
    }

    @Override
    public User getUserById(Long id) throws EntityNotFoundException {
        User user = null;

        try {
            JPAHelper.beginTransaction();
            Optional.ofNullable(dao.getById(id)).orElseThrow(() -> new EntityNotFoundException(User.class, id));
            user = dao.getById(id);
            JPAHelper.commitTransaction();
            logger.info("User with id " + id + " was found.");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - user was not found -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return user;
    }
}

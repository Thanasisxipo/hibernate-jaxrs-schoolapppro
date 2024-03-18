package gr.aueb.cf.schoolapppro.dao;

import gr.aueb.cf.schoolapppro.model.Teacher;
import gr.aueb.cf.schoolapppro.model.User;
import gr.aueb.cf.schoolapppro.service.util.JPAHelper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import jakarta.ws.rs.ext.Provider;

import java.util.List;

@Provider
@ApplicationScoped
public class UserDAOImpl implements IUserDAO{
    @Override
    public User insert(User user) {
        getEntityManager().persist(user);
        return user;
    }

    @Override
    public User update(User user) {
        getEntityManager().merge(user);
        return user;
    }

    @Override
    public void delete(Long id) {
        EntityManager em = getEntityManager();
        User userToDelete = em.find(User.class, id);
        em.remove(userToDelete);
    }

    @Override
    public List<User> getByUsername(String username) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> selectQuery = builder.createQuery(User.class);
        Root<User> root = selectQuery.from(User.class);

        ParameterExpression<String> paramUsername = builder.parameter(String.class);
        selectQuery.select(root).where(builder.like(root.get("username"), paramUsername));

        return getEntityManager().createQuery(selectQuery)
                .setParameter(paramUsername, username + "%")
                .getResultList();
    }

    @Override
    public User getById(Long id) {
        return getEntityManager().find(User.class, id);
    }

    private EntityManager getEntityManager() {
        return JPAHelper.getEntityManager();
    }

}

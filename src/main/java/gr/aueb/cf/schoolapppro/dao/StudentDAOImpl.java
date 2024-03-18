package gr.aueb.cf.schoolapppro.dao;

import gr.aueb.cf.schoolapppro.model.Student;
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
public class StudentDAOImpl implements IStudentDAO{
    @Override
    public Student insert(Student student) {
        getEntityManager().persist(student);
        return student;    }

    @Override
    public Student update(Student student) {
        getEntityManager().merge(student);
        return student;    }

    @Override
    public void delete(Long id) {
        EntityManager em = getEntityManager();
        Student studentToDelete = em.find(Student.class, id);
        em.remove(studentToDelete);
    }

    @Override
    public List<Student> getByLastname(String lastname) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Student> selectQuery = builder.createQuery(Student.class);
        Root<Student> root = selectQuery.from(Student.class);

        ParameterExpression<String> paramLastname = builder.parameter(String.class);
        selectQuery.select(root).where(builder.like(root.get("lastname"), paramLastname));

        return getEntityManager().createQuery(selectQuery)
                .setParameter(paramLastname, lastname + "%")
                .getResultList();
    }

    @Override
    public Student getById(Long id) {
        return getEntityManager().find(Student.class, id);    }

    private EntityManager getEntityManager() {
        return JPAHelper.getEntityManager();
    }

}

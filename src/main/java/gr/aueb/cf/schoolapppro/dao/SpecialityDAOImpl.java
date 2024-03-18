package gr.aueb.cf.schoolapppro.dao;

import gr.aueb.cf.schoolapppro.model.Speciality;
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
public class SpecialityDAOImpl implements ISpecialityDAO{
    @Override
    public Speciality insert(Speciality speciality) {
        getEntityManager().persist(speciality);
        return speciality;    }

    @Override
    public Speciality update(Speciality speciality) {
        getEntityManager().merge(speciality);
        return speciality;    }

    @Override
    public void delete(Long id) {
        EntityManager em = getEntityManager();
        Speciality specialityToDelete = em.find(Speciality.class, id);
        em.remove(specialityToDelete);
    }

    @Override
    public List<Speciality> getBySpecialityname(String speciality) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Speciality> selectQuery = builder.createQuery(Speciality.class);
        Root<Speciality> root = selectQuery.from(Speciality.class);

        ParameterExpression<String> paramSpeciality = builder.parameter(String.class);
        selectQuery.select(root).where(builder.like(root.get("speciality"), paramSpeciality));

        return getEntityManager().createQuery(selectQuery)
                .setParameter(paramSpeciality, speciality + "%")
                .getResultList();    }

    @Override
    public Speciality getById(Long id) {
        return getEntityManager().find(Speciality.class, id);
    }

    private EntityManager getEntityManager() {
        return JPAHelper.getEntityManager();
    }

}

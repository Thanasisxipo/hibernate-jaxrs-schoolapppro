package gr.aueb.cf.schoolapppro.dao;

import gr.aueb.cf.schoolapppro.model.City;
import gr.aueb.cf.schoolapppro.model.Teacher;
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
public class CityDAOImpl implements ICityDAO{
    @Override
    public City insert(City city) {
        getEntityManager().persist(city);
        return city;
    }

    @Override
    public City update(City city) {
        getEntityManager().merge(city);
        return city;
    }

    @Override
    public void delete(Long id) {
        EntityManager em = getEntityManager();
        City cityToDelete = em.find(City.class, id);
        em.remove(cityToDelete);
    }

    @Override
    public List<City> getByCityName(String city) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<City> selectQuery = builder.createQuery(City.class);
        Root<City> root = selectQuery.from(City.class);

        ParameterExpression<String> paramCityname = builder.parameter(String.class);
        selectQuery.select(root).where(builder.like(root.get("city"), paramCityname));

        return getEntityManager().createQuery(selectQuery)
                .setParameter(paramCityname, city + "%")
                .getResultList();
    }

    @Override
    public City getById(Long id) {
        return getEntityManager().find(City.class, id);
    }

    private EntityManager getEntityManager() {
        return JPAHelper.getEntityManager();
    }

}

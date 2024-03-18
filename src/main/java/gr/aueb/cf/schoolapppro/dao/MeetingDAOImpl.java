package gr.aueb.cf.schoolapppro.dao;

import gr.aueb.cf.schoolapppro.model.Meeting;
import gr.aueb.cf.schoolapppro.model.Teacher;
import gr.aueb.cf.schoolapppro.service.util.JPAHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class MeetingDAOImpl implements IMeetingDAO {
    @Override
    public Meeting insert(Meeting meeting) {
        getEntityManager().persist(meeting);
        return meeting;    }

    @Override
    public Meeting update(Meeting meeting) {
        getEntityManager().merge(meeting);
        return meeting;    }

    @Override
    public void delete(Long id) {
        EntityManager em = getEntityManager();
        Meeting meetingToDelete = em.find(Meeting.class, id);
        em.remove(meetingToDelete);
    }

    @Override
    public List<Meeting> getByRoom(String room) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Meeting> selectQuery = builder.createQuery(Meeting.class);
        Root<Meeting> root = selectQuery.from(Meeting.class);

        ParameterExpression<String> paramRoom = builder.parameter(String.class);
        selectQuery.select(root).where(builder.like(root.get("room"), paramRoom));

        return getEntityManager().createQuery(selectQuery)
                .setParameter(paramRoom, room + "%")
                .getResultList();    }


    @Override
    public Meeting getById(Long id) {
        return getEntityManager().find(Meeting.class, id);
    }

    private EntityManager getEntityManager() {
        return JPAHelper.getEntityManager();
    }

}

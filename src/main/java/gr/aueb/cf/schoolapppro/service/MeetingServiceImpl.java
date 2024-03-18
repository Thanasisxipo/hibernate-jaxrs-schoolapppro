package gr.aueb.cf.schoolapppro.service;

import gr.aueb.cf.schoolapppro.dao.IMeetingDAO;
import gr.aueb.cf.schoolapppro.dto.MeetingInsertDTO;
import gr.aueb.cf.schoolapppro.dto.MeetingUpdateDTO;
import gr.aueb.cf.schoolapppro.mapper.Mapper;
import gr.aueb.cf.schoolapppro.model.Meeting;
import gr.aueb.cf.schoolapppro.model.Teacher;
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
public class MeetingServiceImpl implements IMeetingService{
    private static final Logger logger = LoggerFactory.getLogger(MeetingServiceImpl.class);

    @Inject
    private IMeetingDAO dao;

    @Override
    public Meeting insertMeeting(MeetingInsertDTO dto) throws Exception {
        Meeting meeting = null;

        try {
            JPAHelper.beginTransaction();
            meeting = Mapper.mapToMeeting(dto);
            meeting = dao.insert(meeting);
            if (meeting.getId() == null) {
                throw new Exception("Insert Error");
            }
            JPAHelper.commitTransaction();
            logger.info("Meeting with id " + meeting.getId() + " was inserted.");
        } catch (Exception e) {
            JPAHelper.rollbackTransaction();
            logger.error("Error - meeting not inserted -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return meeting;    }

    @Override
    public Meeting updateMeeting(MeetingUpdateDTO dto) throws EntityNotFoundException {
        Meeting meetingToUpdate = null;
        Meeting updatedMeeting= null;

        try {
            JPAHelper.beginTransaction();
            Optional.ofNullable(dao.getById(dto.getId()))
                    .orElseThrow(() -> new EntityNotFoundException(Meeting.class, dto.getId()));
            meetingToUpdate = Mapper.mapToMeeting(dto);
            updatedMeeting = dao.update(meetingToUpdate);
            JPAHelper.commitTransaction();
            logger.info("Meeting with id " + updatedMeeting.getId() + " was updated.");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.error("Error - Meeting was not found -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return updatedMeeting;    }

    @Override
    public void deleteMeeting(Long id) throws EntityNotFoundException {
        try {
            JPAHelper.beginTransaction();
            Optional.ofNullable(dao.getById(id)).orElseThrow(() -> new EntityNotFoundException(Meeting.class, id));
            dao.delete(id);
            JPAHelper.commitTransaction();
            logger.info("Meeting with id " + id + " was deleted.");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - meeting was not found -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public List<Meeting> getMeetingByMeetingRoom(String meetingRoom) throws EntityNotFoundException {
        List<Meeting> meetings;
        try {
            JPAHelper.beginTransaction();
            meetings = Optional.of(dao.getByRoom(meetingRoom))
                    .orElseThrow(() -> new EntityNotFoundException(List.class, 0L));
            JPAHelper.commitTransaction();
            logger.info("Meetings were found");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - meetings were not found -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return meetings;
    }

    @Override
    public Meeting getMeetingById(Long id) throws EntityNotFoundException {
        Meeting meeting = null;

        try {
            JPAHelper.beginTransaction();
            Optional.ofNullable(dao.getById(id)).orElseThrow(() -> new EntityNotFoundException(Meeting.class, id));
            meeting = dao.getById(id);
            JPAHelper.commitTransaction();
            logger.info("Meeting with id " + id + " was found.");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - meeting was not found -- " + e.getMessage() );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return meeting;    }
}

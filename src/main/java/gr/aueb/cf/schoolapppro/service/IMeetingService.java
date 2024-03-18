package gr.aueb.cf.schoolapppro.service;

import gr.aueb.cf.schoolapppro.dto.MeetingInsertDTO;
import gr.aueb.cf.schoolapppro.dto.MeetingUpdateDTO;
import gr.aueb.cf.schoolapppro.model.Meeting;
import gr.aueb.cf.schoolapppro.service.exception.EntityNotFoundException;

import java.util.List;

public interface IMeetingService {
    Meeting insertMeeting(MeetingInsertDTO dto) throws Exception;
    Meeting updateMeeting(MeetingUpdateDTO dto) throws EntityNotFoundException;
    void deleteMeeting(Long id) throws EntityNotFoundException;
    List<Meeting> getMeetingByMeetingRoom(String meetingRoom) throws EntityNotFoundException;
    Meeting getMeetingById(Long id) throws EntityNotFoundException;
}

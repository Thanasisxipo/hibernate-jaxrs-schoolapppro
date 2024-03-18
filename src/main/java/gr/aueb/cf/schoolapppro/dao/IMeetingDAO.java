package gr.aueb.cf.schoolapppro.dao;

import gr.aueb.cf.schoolapppro.model.Meeting;

import java.util.List;

public interface IMeetingDAO {
    Meeting insert(Meeting meeting);
    Meeting update(Meeting meeting);
    void delete(Long id);
    List<Meeting> getByRoom(String room);
    Meeting getById(Long id);
}

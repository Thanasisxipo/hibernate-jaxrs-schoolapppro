package gr.aueb.cf.schoolapppro.mapper;

import gr.aueb.cf.schoolapppro.dto.*;
import gr.aueb.cf.schoolapppro.model.*;

import java.util.Set;

public class Mapper {

    private Mapper() {}

    public static Teacher mapToTeacher(TeacherInsertDTO dto) {
        return new Teacher(null, dto.getFirstname(), dto.getLastname(), dto.getSsn(), dto.getSpeciality(), dto.getUser(), dto.getMeetings());
    }

    public static Teacher mapToTeacher(TeacherUpdateDTO dto) {
        return new Teacher(dto.getId(), dto.getFirstname(), dto.getLastname(), dto.getSsn(), dto.getSpeciality(), dto.getUser(), dto.getMeetings());
    }

    public static TeacherReadOnlyDTO mapTeacherToReadOnly(Teacher teacher) {
        return new TeacherReadOnlyDTO(teacher.getId(), teacher.getFirstname(), teacher.getLastname(), teacher.getSsn(), teacher.getSpeciality(),teacher.getUser(),teacher.getAllMeetings());
    }

    public static Student mapToStudent(StudentInsertDTO dto) {
        return new Student(null, dto.getFirstname(), dto.getLastname(), dto.getGender(), dto.getBirthDate(), dto.getCity(), dto.getUser(), dto.getMeetings());
    }

    public static Student mapToStudent(StudentUpdateDTO dto) {
        return new Student(dto.getId(), dto.getFirstname(), dto.getLastname(), dto.getGender(), dto.getBirthDate(), dto.getCity(), dto.getUser(), dto.getMeetings());
    }

    public static StudentReadOnlyDTO mapStudentToReadOnly(Student student) {
        return new StudentReadOnlyDTO(student.getId(), student.getFirstname(), student.getLastname(), student.getGender(), student.getBirthDate(), student.getCity(), student.getUser(), student.getAllMeetings());
    }

    public static User mapToUser(UserInsertDTO dto) {
        return new User(null, dto.getUsername(), dto.getPassword(), dto.getTeacher(), dto.getStudent() );
    }

    public static User mapToUser(UserUpdateDTO dto) {
        return new User(dto.getId(), dto.getUsername(), dto.getPassword(), dto.getTeacher(), dto.getStudent() );
    }

    public static UserReadOnlyDTO mapUserToReadOnly(User user) {
        return new UserReadOnlyDTO(user.getId(), user.getUsername(), user.getPassword(), user.getTeacher(), user.getStudent());
    }

    public static Speciality mapToSpeciality(SpecialityInsertDTO dto) {
        return new Speciality(null, dto.getSpeciality(), dto.getTeachers());
    }

    public static Speciality mapToSpeciality(SpecialityUpdateDTO dto) {
        return new Speciality(dto.getId(), dto.getSpeciality(), dto.getTeachers());
    }

    public static SpecialityReadOnlyDTO mapSpecialityToReadOnly(Speciality speciality) {
        return new SpecialityReadOnlyDTO(speciality.getId(), speciality.getSpeciality(), speciality.getAllTeachers());
    }

    public static City mapToCity(CityInsertDTO dto) {
        return new City(null, dto.getCity(), dto.getStudents());
    }

    public static City mapToCity(CityUpdateDTO dto) {
        return new City(dto.getId(), dto.getCity(), dto.getStudents());
    }

    public static CityReadOnlyDTO mapCityToReadOnly(City city) {
        return new CityReadOnlyDTO(city.getId(), city.getCity(), city.getAllStudents());
    }

    public static Meeting mapToMeeting(MeetingInsertDTO dto) {
        return new Meeting(null, dto.getMeetingRoom(), dto.getMeetingDate(),dto.getTeacher(),dto.getStudent());
    }

    public static Meeting mapToMeeting(MeetingUpdateDTO dto) {
        return new Meeting(dto.getId(), dto.getMeetingRoom(), dto.getMeetingDate(),dto.getTeacher(),dto.getStudent());
    }

    public static MeetingReadOnlyDTO mapMeetingToReadOnly(Meeting meeting) {
        return new MeetingReadOnlyDTO(meeting.getId(), meeting.getMeetingRoom(), meeting.getMeetingDate(), meeting.getTeacher(), meeting.getStudent());
    }
}

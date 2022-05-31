package com.example.demo.UserService;

import com.example.demo.domain.*;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Locker saveLocker(Locker locker);
    List<Locker>getLockers();
    List<Locker>getLockersByRoom(String name);

    Locker updateLocker(Long id, Locker locker);

    Room saveRoom(Room room);

    List<Room> getOnlyRooms();

    List<Room>getRooms();

    void deleteLocker(Long id);

    Role addRole(Role role);

    void addLockerToRoom(String roomName, Integer number);

    Student findStudentByName(String Name);
    List<Student>getStudents();
    Student saveStudent(Student student);

    Room updateRoom(Long id, Room room);
    Room findRoomByName(String name);

    Student updateStudent(Long id, Student student);

    void deleteRoom(Long id);

    void deleteStudent(Long id);

    void addStudentToLocker(String name, Integer number);
    void removeStudentFromLocker(Integer number);
}

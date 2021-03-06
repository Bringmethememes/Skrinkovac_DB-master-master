package com.example.demo.UserService;

import com.example.demo.domain.*;
import com.example.demo.repo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService {
    private final StudentRepo studentRepo;
    private final RoomRepo roomRepo;
    private final LockerRepo lockerRepo;
    private final RoleRepository roleRepository;

    @Override
    public List<Locker> getLockers() {
        return lockerRepo.findAll();
    }

    @Override
    public List<Locker> getLockersByRoom(String name) {
        Room room = roomRepo.findByRoomName(name);
        return room.getLockers();
    }

    @Override
    public Locker saveLocker(Locker locker) {
        return lockerRepo.save(locker);
    }

    @Override
    public Locker updateLocker(Long id, Locker locker){
     locker.setId(id);
     return lockerRepo.save(locker);
    }

    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }
    @Override
    public Room saveRoom(Room room) {
        return roomRepo.save(room);
    }

    @Override
    public List<Room> getOnlyRooms() {
        Room room;
        List<Room> rooms = new ArrayList<>();
        rooms = roomRepo.findAll();
        for (int i = 0; i < rooms.size(); i++) {
            room = rooms.get(i);
            room.setLockers(null);
        }
        return rooms;
    }
    @Override
    public List<Room> getRooms() {
        return roomRepo.findAll();
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepo.save(student);
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        student.Id(id);
        return studentRepo.save(student);
    }

    @Override
    public Room updateRoom(Long id, Room room) {
        room.setId(id);
        return roomRepo.save(room);
    }

    @Override
    public Room findRoomByName(String name) {
        return roomRepo.findByRoomName(name);
    }

    @Override
    public void deleteRoom(Long id){
        roomRepo.deleteById(id);
    }

    @Override
    public void deleteStudent(Long id){
        studentRepo.deleteById(id);
    }

    @Override
    public void addStudentToLocker(String name, Integer number) {
        Locker locker = lockerRepo.findByNumber(number);
        Student student = studentRepo.findByStudentName(name);
        locker.setStudent(student);

    }

    @Override
    public void removeStudentFromLocker(Integer number) {
        Locker locker = lockerRepo.findByNumber(number);
        locker.setStudent(null);
    }

    @Override
    public void deleteLocker(Long id){
        lockerRepo.deleteById(id);
    }

    @Override
    public void addLockerToRoom(String roomName,Integer number) {
        Room room = roomRepo.findByRoomName(roomName);
        Locker locker = lockerRepo.findByNumber(number);
        room.getLockers().add(locker);

    }

    @Override
    public Student findStudentByName(String ziaci) {
        log.info("Geting Ziak {}", ziaci);
        return studentRepo.findByStudentName(ziaci);
    }


    @Override
    public List<Student> getStudents() {
        log.info("Getting all Students");
        return studentRepo.findAll();
    }
}

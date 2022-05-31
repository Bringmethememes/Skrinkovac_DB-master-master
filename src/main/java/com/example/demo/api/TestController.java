package com.example.demo.api;

import com.example.demo.UserService.UserService;
import com.example.demo.domain.Locker;
import com.example.demo.domain.Room;
import com.example.demo.domain.Student;
import com.example.demo.payload.forms.LockerToRoomForm;
import com.example.demo.payload.forms.StudentToLockerForm;
import com.example.demo.repo.LockerRepo;
import com.example.demo.repo.RoomRepo;
import com.example.demo.repo.StudentRepo;
import com.sipios.springsearch.anotation.SearchSpec;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/")
public class TestController {

    private final UserService userService;
    private final StudentRepo studentRepo;
    private final LockerRepo lockerRepo;
    private final RoomRepo roomRepo;

    public TestController(UserService userService, StudentRepo studentRepo, LockerRepo lockerRepo,RoomRepo roomRepo) {
        this.userService = userService;
        this.studentRepo = studentRepo;
        this.lockerRepo = lockerRepo;
        this.roomRepo = roomRepo;
    }

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    /*Rooms*/

    @GetMapping("/rooms")
    public HashMap<String, Room>getRooms(){
        HashMap<String ,Room> rooms = new HashMap<>();
        List<Room> list = userService.getOnlyRooms();
        for (Room room: list) {
            rooms.put(room.getRoomName(),room);
        }
        return rooms;
    }
    @GetMapping("/roomsAll")
    public HashMap<String, Room>getRoomsAll(){
        HashMap<String ,Room> rooms = new HashMap<>();
        List<Room> list = userService.getRooms();
        for (Room room: list) {
            rooms.put(room.getRoomName(),room);
        }
        return rooms;
    }

    @GetMapping("/findRoom")
    public ResponseEntity<List<Room>>searchForRoom(@SearchSpec Specification<Locker> specification){
        return new ResponseEntity(roomRepo.findAll(Specification.where(specification)), HttpStatus.OK);
    }

    @PostMapping("/room/lockers")
    public HashMap<Integer,Locker>getRoomLocker(@Valid @RequestBody String Name){
        HashMap<Integer,Locker> lockers = new HashMap<>();
        Room room = userService.findRoomByName(Name);
        for (Locker locker : room.getLockers()){
            lockers.put(locker.getNumber(),locker);
        }
        return lockers;
    }

    @PostMapping("/add/room")
    public ResponseEntity<Room>saveRoom(@RequestBody Room room){
        return ResponseEntity.ok().body(userService.saveRoom(room));
    }

    @PutMapping("/edit/room/{id}")
    public ResponseEntity<Room>updateRoom(@PathVariable(value = "id") Long roomId, @Valid @RequestBody Room roomDetails) throws ResourceNotFoundException {
        return new ResponseEntity(userService.updateRoom(roomId,roomDetails), HttpStatus.OK);
    }

    @DeleteMapping("/delete/room/{id}")
    public void deleteRoom(@PathVariable(value = "id") Long roomId){
        userService.deleteRoom(roomId);
    }

    /*Lockers*/

    @GetMapping("/lockers")
    public HashMap<Integer, Locker>getLockers(){
        HashMap<Integer ,Locker> lockers = new HashMap<>();
        List<Locker> list= userService.getLockers();
        for (Locker locker: list) {
            lockers.put(locker.getNumber(),locker);
        }
        return lockers;
    }

    @GetMapping("/findLocker")
    public ResponseEntity<List<Locker>>searchForLocker(@SearchSpec Specification<Locker> specification){
        return new ResponseEntity<>(lockerRepo.findAll(Specification.where(specification)), HttpStatus.OK);
    }

    @PostMapping("/add/locker")
    public ResponseEntity<Locker>saveLocker(@RequestBody Locker locker){
        return ResponseEntity.ok().body(userService.saveLocker(locker));
    }
    @PostMapping("/add/lockerToRoom")
    public void lockerToRoom(@RequestBody LockerToRoomForm form){
        userService.addLockerToRoom(form.getName(), form.getNumber());
    }

    @PutMapping("/edit/locker/{id}")
    public ResponseEntity<Student>updateLocker(@PathVariable(value = "id") Long lockerId, @Valid @RequestBody Locker lockerDetail) throws ResourceNotFoundException {
        return new ResponseEntity(userService.updateLocker(lockerId,lockerDetail),HttpStatus.OK);
    }

    @DeleteMapping("/delete/locker/{id}")
    public void deleteLocker(@PathVariable(value = "id") Long lockerId){
        userService.deleteLocker(lockerId);
    }

    /*Students*/
    @GetMapping("/students")
    public ResponseEntity<List<Student>>getStudents(){
        return ResponseEntity.ok().body(userService.getStudents());
    }

    @GetMapping("/findStudent")
    public ResponseEntity<List<Student>>searchForZiak(@SearchSpec Specification<Student> specification){
        return new ResponseEntity<>(studentRepo.findAll(Specification.where(specification)), HttpStatus.OK);
    }

    @PostMapping("/add/student")
    public ResponseEntity<Student>saveStudent(@RequestBody Student student){
        return ResponseEntity.ok().body(userService.saveStudent(student));
    }
    @PostMapping("/add/studentToLocker")
    public void studentToLocker(@RequestBody StudentToLockerForm form){
        userService.addStudentToLocker(form.getName(),form.getNumber());
    }

    @PutMapping("/edit/student/{id}")
    public ResponseEntity<Student>updateStudent(@PathVariable(value = "id") Long studentId, @Valid @RequestBody Student studentDetails) throws ResourceNotFoundException {
        return new ResponseEntity(userService.updateStudent(studentId,studentDetails), HttpStatus.OK);
    }

    @DeleteMapping("/delete/studentFromLocker")
    public void deleteStudentFromLocker(@RequestBody Integer integer){
        userService.removeStudentFromLocker(integer);

    }

    @DeleteMapping("/delete/student/{id}")
    public void deleteStudent(@PathVariable(value = "id") Long studentId){
        userService.deleteStudent(studentId);
    }
}

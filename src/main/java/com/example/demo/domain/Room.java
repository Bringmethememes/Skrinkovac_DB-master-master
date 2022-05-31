package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints ={@UniqueConstraint(columnNames = {"x","y"})})
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String roomName;
    @Column(name = "x")
    private Integer x;
    @Column(name = "y")
    private Integer y;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Locker> lockers = new ArrayList<Locker>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

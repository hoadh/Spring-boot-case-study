package com.codegym.model;

import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "football_players")
public class FootballPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;

    @NumberFormat
    private int age;


    public FootballPlayer() {
    }


    public String img;


    @NotEmpty
    private String address;

    public FootballPlayer(String firstName, String lastName, String img, String address, int age, String height, String weight) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.img = img;
        this.address = address;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    @NotEmpty
    private String height;

    @NotEmpty
    private String weight;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}

package com.exam.ExamJavaSpring.entyties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="room")
public class Room 
{
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "title_img", nullable = false)
    private String titleImg;
    @Column(name = "img2", nullable = true)
    private String img2;
    @Column(name = "img3", nullable = true)
    private String img3;
    @Column(name = "img4", nullable = true)
    private String img4;
    @Column(name = "img5", nullable = true)
    private String img5;
    @Column(name = "img6", nullable = true)
    private String img6;
    @Column(name = "img7", nullable = true)
    private String img7;
    @Column(name = "img8", nullable = true)
    private String img8;
    @Column(name = "img9", nullable = true)
    private String img9;
    @Column(name = "img10", nullable = true)
    private String img10;
    @Column(name = "country", nullable = false)
    private String country;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "street", nullable = false)
    private String street;
    @Column(name = "house_number", nullable = false)
    private String houseNumber;
    @Column(name = "floor_number", nullable = true)
    private int floorNumber;
    @Column(name = "apartment_number", nullable = true)
    private int apartmentNumber;
    @Column(name = "price", nullable = false)
    private float price;
    @Column(name = "type_of_rental", nullable = false)
    private boolean typeOfRental;
    @Column(name = "user_id", nullable = false)
    private String userId;
}

package com.airport.domain;

public class Airport {
    private Long id;
    private String name;
    private String code;
    private City city;

    // Constructors
    public Airport(){}

    public Airport(String name, String code, City city){
        this.name = name;
        this.code = code;
        this.city = city;
    }

    // Getters and Setters 
    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String toString(){
        return "AirportDTO{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", code='" + code + '\'' +
        ", city=" + city +
        '}';
    }
    }


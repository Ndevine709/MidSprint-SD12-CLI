package com.airport.domain;

import java.util.List;

public class Aircraft {
    private Long id;
    private String tailNumber;
    private String model;
    private int capacity;
    private List<Airport> airports;

    public Aircraft() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTailNumber() {
        return tailNumber;
    }

    public void setTailNumber(String tailNumber) {
        this.tailNumber = tailNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Airport> getAirports() {
        return airports;
    }

    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }

    @Override
    public String toString() {
        return "AircraftDTO{" +
                "id=" + id +
                ", tailNumber='" + tailNumber + '\'' +
                ", model='" + model + '\'' +
                ", capacity=" + capacity +
                ", airports=" + airports +
                '}';
    }
}

package com.airport.domain;

import java.util.List;

public class Passenger {
    private Long id;
    private String birthday;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private List<Aircraft> flights;

    public Passenger() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Aircraft> getFlights() {
        return flights;
    }

    public void setFlights(List<Aircraft> flights) {
        this.flights = flights;
    }

    @Override
    public String toString() {
        return "PassengerDTO{" +
                "id=" + id +
                ", birthday='" + birthday + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", flights=" + flights +
                '}';
    }
}

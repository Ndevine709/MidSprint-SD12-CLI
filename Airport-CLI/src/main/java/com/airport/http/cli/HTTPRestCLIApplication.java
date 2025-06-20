package com.airport.http.cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.airport.domain.Aircraft;
import com.airport.domain.Airport;
import com.airport.domain.City;
import com.airport.domain.Passenger;
import com.airport.http.client.RESTClient;

public class HTTPRestCLIApplication {
    private RESTClient restClient;

    public RESTClient getRestClient() {
        if (restClient == null) {
            restClient = new RESTClient(null);
        }
        return restClient;
    }

    public void setRestClient(RESTClient restClient) {
        this.restClient = restClient;
    }

    public void printAirportsGroupedByCity() {
        List<Airport> airports = getRestClient().getAllAirports();
        Map<String, List<Airport>> cityAirportMap = new HashMap<>();

        for (Airport airport : airports) {
            City city = airport.getCity();
            if (city == null || city.getName() == null) {
                continue;
            }

            String cityName = city.getName();

            if (!cityAirportMap.containsKey(cityName)) {
                cityAirportMap.put(cityName, new ArrayList<>());
            }

            cityAirportMap.get(cityName).add(airport);
        }

        for (String city : cityAirportMap.keySet()) {
            System.out.println("City " + city);
            for (Airport airport : cityAirportMap.get(city)) {
                System.out.println("  - " + airport.getName() + "  (" + airport.getCode() + ")  ");
            }
        }
    }

    public void printAircraftByPassenger() {
        List<Passenger> passengers = getRestClient().getAllPassengers();

        for (Passenger p : passengers) {
            String name = p.getFirstName() + " " + p.getLastName();
            System.out.println("Passenger: " + name);

            List<Aircraft> flights = p.getFlights();
            if (flights == null || flights.isEmpty()) {
                System.out.println("  - No flights recorded.");
            } else {
                for (Aircraft a : flights) {
                    System.out.println("  - "
                            + a.getModel()
                            + " (" + a.getTailNumber() + "), capacity "
                            + a.getCapacity());
                }
            }
            System.out.println();
        }
    }

    public void printAirportsByAircraft() {
        // Fetch every aircraft (each one comes with its airports set)
        List<Aircraft> all = getRestClient().getAllAircraft();

        if (all.isEmpty()) {
            System.out.println("No aircraft found.");
            return;
        }

        for (Aircraft a : all) {
            System.out.println("\nAircraft Tail #: " + a.getTailNumber());
            System.out.println("  - Allowed to use:");
            if (a.getAirports() == null || a.getAirports().isEmpty()) {
                System.out.println("     * (none)");
            } else {
                for (Airport ap : a.getAirports()) {
                    System.out.printf("     * %s (%s)%n",
                            ap.getName(),
                            ap.getCode());
                }
            }
        }
    }

    public void printAirportsUsedByPassengers() {
        List<Passenger> allPassengers = getRestClient().getAllPassengers();
        
        if (allPassengers.isEmpty()){
            System.out.println("No passengers found.");
            return;
        }
        
        for (Passenger passenger : allPassengers){
            System.out.println("\nPassenger: " + passenger.getFirstName() + " " + passenger.getLastName());
            
            List<Aircraft> flights = passenger.getFlights();
            if (flights == null || flights.isEmpty()){
                System.out.println(" - No flights recorded.");
                continue;
            }
            
            System.out.println(" - Airports used:");
            boolean foundAirports = false;

            for (Aircraft aircraft : flights){
                List<Airport> airports = aircraft.getAirports();
                if (airports != null && !airports.isEmpty()){
                    for (Airport airport : airports){
                        System.out.printf("   * %s (%s)%n",
                        airport.getName(),
                        airport.getCode());
                        foundAirports = true;
                    }
                }
            }
            if (!foundAirports){
            System.out.println("   * (No airports linked to flights)");
        }
        } 
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter server URL: ");
        String serverURL = scanner.nextLine();

        HTTPRestCLIApplication cliApp = new HTTPRestCLIApplication();
        RESTClient restClient = new RESTClient(serverURL);
        cliApp.setRestClient(restClient);

        while (true) {
            System.out.println("\nChoose an option: ");
            System.out.println("1. What airports are there in each city?");
            System.out.println("2. What aircraft has each passenger flown on?");
            System.out.println("3. What airport do aircrafts take off from and land at?");
            System.out.println("4. What airports have passengers used?");
            System.out.println("5. Exit");

            System.out.println("Enter your choice (1-5): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    cliApp.printAirportsGroupedByCity();
                    break;

                case "2":
                    cliApp.printAircraftByPassenger();
                    break;

                case "3":
                    cliApp.printAirportsByAircraft();
                    break;

                case "4":
                    cliApp.printAirportsUsedByPassengers();
                    break;

                case "5":
                    System.out.println("Exiting program...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Pick from 1-5");
            }
        }
    }
}

package com.airport.http.cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.airport.domain.Airport;
import com.airport.domain.City;
import com.airport.http.client.RESTClient;

public class HTTPRestCLIApplication {
    private RESTClient restClient;

    public RESTClient getRestClient() {
        if(restClient == null) {
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

    // Place holders for group members
    public void printAircraftByPassenger() {}

    public void printAirportsByAircraft() {}

    public void printAirportsUsedByPassengers() {}

    
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

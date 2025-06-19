package com.airport.http.cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (args.length < 2) {
            System.out.println("CLI Usage: java -jar cli-app.jar <serverURL> <queryType>");
            return;
        }

        String serverURL = args[0];
        String queryType = args[1];

        HTTPRestCLIApplication cliApp = new HTTPRestCLIApplication();
        RESTClient restClient = new RESTClient(serverURL);
        cliApp.setRestClient(restClient);

        switch (queryType) {
            case "airportsByCity":
                cliApp.printAirportsGroupedByCity();
                break;

            case "aircraftByPassenger":
                cliApp.printAircraftByPassenger();
                break;

            case "airportsByAircraft":
                cliApp.printAirportsByAircraft();
                break;

            case "airportsUsedByPassengers":
                cliApp.printAirportsUsedByPassengers();
                break;
        
            default:
                System.out.println("Invalid query type. Valid options include: airportsByCity, aircraftByPassenger, airportsByAircraft, airportsUsedByPassengers");
        }
    }
}

package com.airport.http.client;

import com.airport.domain.Passenger;
import com.airport.domain.Aircraft;
import com.airport.domain.City;
import com.airport.domain.Airport;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.ArrayList;
import java.util.List;

public class RESTClient {
    private String serverURL;
    private HttpClient client;
    private final ObjectMapper mapper;

    public RESTClient(String serverURL) {
        this.serverURL = serverURL;
        this.mapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public String getServerURL() {
        return serverURL;
    }
    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    private HttpClient getClient() {
        if (client == null) {
            client = HttpClient.newHttpClient();
        }
        return client;
    }

    private HttpResponse<String> httpSender(HttpRequest request)
            throws IOException, InterruptedException {
        HttpResponse<String> response =
            getClient().send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        if (status >= 200 && status < 300) {
            System.out.println("***** Response Body *****");
            System.out.println(response.body());
        } else {
            System.err.println("Error HTTP Status Code: " + status);
        }
        return response;
    }

    public List<Passenger> buildPassengerListFromResponse(String json) throws IOException {
        return mapper.readValue(json, new TypeReference<List<Passenger>>() {});
    }

    public List<Aircraft> buildAircraftListFromResponse(String json) throws IOException {
        return mapper.readValue(json, new TypeReference<List<Aircraft>>() {});
    }

    public List<City> buildCitiesListFromResponse(String json) throws IOException {
        return mapper.readValue(json, new TypeReference<List<City>>() {});
    }

    public List<Airport> buildAirportListFromResponse(String json) throws IOException {
        return mapper.readValue(json, new TypeReference<List<Airport>>() {});
    }

    public List<Passenger> getAllPassengers() {
        List<Passenger> list = new ArrayList<>();
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(serverURL + "/passengers"))
            .GET().build();
        try {
            HttpResponse<String> resp = httpSender(req);
            list = buildPassengerListFromResponse(resp.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Aircraft> getAllAircraft() {
        List<Aircraft> list = new ArrayList<>();
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(serverURL + "/aircrafts"))
            .GET().build();
        try {
            HttpResponse<String> resp = httpSender(req);
            list = buildAircraftListFromResponse(resp.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Airport> getAllAirports(){
        List<Airport> list = new ArrayList<>();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(serverURL + "/airport"))
            .GET().build();
        try{
            HttpResponse<String> response = httpSender(request);
            list = buildAirportListFromResponse(response.body());
        } catch (IOException | InterruptedException exception){
            exception.printStackTrace();
        }
        return list;
    }

    public List<Airport> getAirportsByCityId(Long cityId){
        List<Airport> list = new ArrayList<>();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(serverURL + "/airport/city/" + cityId))
            .GET()
            .build();
        try{
            HttpResponse<String> response = httpSender(request);
            list = buildAirportListFromResponse(response.body());
        } catch (IOException | InterruptedException exception){
            exception.printStackTrace();
        }
        return list;
    }

}

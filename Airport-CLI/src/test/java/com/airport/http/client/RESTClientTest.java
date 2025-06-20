package com.airport.http.client;

import com.airport.domain.Passenger;
import com.airport.domain.Aircraft;
import com.airport.domain.Airport;
import com.airport.domain.City;

import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.MockResponse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RESTClientTest {

    // reate a MockWebServer which will act as a fake http server
    private static MockWebServer mockServer;

    // RESTClient under test, which will send its requests to our mock server
    private RESTClient client;

    // This method runs once before any @Test methods. It starts the MockWebServer
    // and points our RESTClient at it.
    @BeforeAll
    static void startMockServer() throws Exception {
        mockServer = new MockWebServer();
        mockServer.start();
    }

    // shuts down the MockWebServer so no ports remain in use
    @AfterAll
    static void shutdownMockServer() throws Exception {
        mockServer.shutdown();
    }

    // ensures that for every test, we re-create a fresh RESTClient
    @BeforeEach
    void setUp() {
        String baseUrl = mockServer.url("/").toString();
        client = new RESTClient(baseUrl);
    }

    @Test
    void getAllPassengers_emptyArrayResultsInEmptyList() {
        mockServer.enqueue(new MockResponse()
            .setBody("[]")
            .setResponseCode(200)
        );

        List<Passenger> list = client.getAllPassengers();

        assertNotNull(list, "Should never return null");
        assertTrue(list.isEmpty(), "Empty JSON array should produce empty list");
    }

    @Test
    void getAllPassengers_parsesCorrectly() {
        mockServer.enqueue(new MockResponse()
                .setBody(
                        """
                                [
                                  {"id":1,"birthday":"1990-01-01","firstName":"Alice","lastName":"Smith","phoneNumber":"555-1234","flights":[]},
                                  {"id":2,"birthday":"1985-05-05","firstName":"Bob","lastName":"Jones","phoneNumber":"555-5678","flights":[]}
                                ]
                                """));

        List<Passenger> p = client.getAllPassengers();

        assertAll("Passengers",
                () -> assertEquals("Alice", p.get(0).getFirstName()),
                () -> assertEquals("Jones", p.get(1).getLastName()));
    }

    @Test
    void getAllAircraft_emptyArrayResultsInEmptyList() {
        mockServer.enqueue(new MockResponse().setBody("[]"));

        assertTrue(client.getAllAircraft().isEmpty());
    }

    @Test
    void getAllAircraft_parsesNonEmptyArrayCorrectly() {
        mockServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("""
                        [
                          {"id":10,"tailNumber":"C-ABC","model":"Boeing 737"},
                          {"id":20,"tailNumber":"C-XYZ","model":"Airbus A320"}
                        ]
                        """));

        List<Aircraft> list = client.getAllAircraft();

        assertAll("Aircraft list",
                () -> assertEquals(2, list.size(), "Should parse two aircraft"),
                () -> assertEquals("C-ABC", list.get(0).getTailNumber()),
                () -> assertEquals("Boeing 737", list.get(0).getModel()),
                () -> assertEquals("C-XYZ", list.get(1).getTailNumber()),
                () -> assertEquals("Airbus A320", list.get(1).getModel()));
    }
}

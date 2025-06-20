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

    // This method runs once before any @Test methods. It starts the MockWebServer and points our RESTClient at it.
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

    // add tests here

}

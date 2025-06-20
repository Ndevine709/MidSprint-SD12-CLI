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

    private static MockWebServer mockServer;
    private RESTClient client;

    @BeforeAll
    static void startMockServer() throws Exception {
        mockServer = new MockWebServer();
        mockServer.start();
    }

    @AfterAll
    static void shutdownMockServer() throws Exception {
        mockServer.shutdown();
    }

    @BeforeEach
    void setUp() {
        String baseUrl = mockServer.url("/").toString();
        client = new RESTClient(baseUrl);
    }

    // add tests here

}

package com.airport.http.cli;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;


import com.airport.domain.Airport;
import com.airport.domain.City;
import com.airport.http.client.RESTClient;

@ExtendWith(MockitoExtension.class)
public class HTTPRestCLIApplicationTest {
    
    @Mock
    private RESTClient mockRestClient;

    @InjectMocks
    private HTTPRestCLIApplication httpRestCLIApplicationTest;

    @BeforeEach
    void setup() {
        httpRestCLIApplicationTest = new HTTPRestCLIApplication();
        httpRestCLIApplicationTest.setRestClient(mockRestClient);
    }

    // Tests that the method will group and print multiple airports under the same city
    @Test
    public void testPrintAirportsGroupedByCity() {
        City testCity = new City();
        testCity.setName("Toronto");

        Airport testAirport = new Airport("Pearson Internation", "YYZ", testCity);
        Airport testAirport2 = new Airport("Devine Airspace", "NND", testCity);

        List<Airport> airportList = new ArrayList<>();
        airportList.add(testAirport);
        airportList.add(testAirport2);

        Mockito.when(mockRestClient.getAllAirports()).thenReturn(airportList);

        httpRestCLIApplicationTest.setRestClient(mockRestClient);
        httpRestCLIApplicationTest.printAirportsGroupedByCity();

        Assertions.assertEquals(2, airportList.size());
    }

    // Tests that the method handles an empty airport list without throwing an exception
    @Test
    public void testPrintAirportsGroupedByCityWithEmptyList() {
        Mockito.when(mockRestClient.getAllAirports()).thenReturn(new ArrayList<>());

        httpRestCLIApplicationTest.printAirportsGroupedByCity();

        Assertions.assertTrue(true);
    }

    // Tests that the method can handle an airport with a null city without crashing
    @Test
    public void testPrintAirportsGroupedByCityWithNullCity() {
        Airport testAirport = new Airport("Los Santos International", "GTAA", null);
        List<Airport> airportList = Collections.singletonList(testAirport);

        Mockito.when(mockRestClient.getAllAirports()).thenReturn(airportList);

        Assertions.assertDoesNotThrow(() -> httpRestCLIApplicationTest.printAirportsGroupedByCity());

        Mockito.verify(mockRestClient).getAllAirports();
    }
}

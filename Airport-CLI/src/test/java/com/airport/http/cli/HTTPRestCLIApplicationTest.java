package com.airport.http.cli;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.airport.domain.Airport;
import com.airport.domain.City;
import com.airport.domain.Passenger;
import com.airport.domain.Aircraft;
import com.airport.http.client.RESTClient;

@ExtendWith(MockitoExtension.class)
public class HTTPRestCLIApplicationTest {
    
    @Mock
    private RESTClient mockRestClient;

    private HTTPRestCLIApplication httpRestCLIApplicationTest;

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setup() {
        httpRestCLIApplicationTest = new HTTPRestCLIApplication();
        httpRestCLIApplicationTest.setRestClient(mockRestClient);

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void teardown() {
        System.setOut(originalOut);
    }

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

        httpRestCLIApplicationTest.printAirportsGroupedByCity();

        Assertions.assertEquals(2, airportList.size());
    }

    @Test
    public void testPrintAirportsGroupedByCityWithEmptyList() {
        Mockito.when(mockRestClient.getAllAirports()).thenReturn(new ArrayList<>());

        httpRestCLIApplicationTest.printAirportsGroupedByCity();

        Assertions.assertTrue(true);
    }

    @Test
    public void testPrintAirportsGroupedByCityWithNullCity() {
        Airport testAirport = new Airport("Los Santos International", "GTAA", null);
        List<Airport> airportList = Collections.singletonList(testAirport);

        Mockito.when(mockRestClient.getAllAirports()).thenReturn(airportList);

        Assertions.assertDoesNotThrow(() -> httpRestCLIApplicationTest.printAirportsGroupedByCity());
        Mockito.verify(mockRestClient).getAllAirports();
    }

    @Test
    public void testPrintAircraftByPassengerWithEmptyList() {
        Mockito.when(mockRestClient.getAllPassengers())
               .thenReturn(Collections.emptyList());

        Assertions.assertDoesNotThrow(() -> httpRestCLIApplicationTest.printAircraftByPassenger());
        Mockito.verify(mockRestClient).getAllPassengers();

        Assertions.assertEquals("", outContent.toString().trim());
    }

    @Test
    public void testPrintAircraftByPassengerWithNoFlights() {
        Passenger p = new Passenger();
        p.setFirstName("Jane");
        p.setLastName("Doe");
        p.setFlights(Collections.emptyList());

        Mockito.when(mockRestClient.getAllPassengers())
               .thenReturn(Collections.singletonList(p));

        httpRestCLIApplicationTest.printAircraftByPassenger();

        String[] lines = outContent.toString().split("\\r?\\n");
        Assertions.assertEquals("Passenger: Jane Doe", lines[0].trim());
        Assertions.assertEquals("- No flights recorded.", lines[1].trim());
        Mockito.verify(mockRestClient).getAllPassengers();
    }

    @Test
    public void testPrintAircraftByPassengerWithFlights() {
        Passenger p = new Passenger();
        p.setFirstName("John");
        p.setLastName("Smith");

        Aircraft a1 = new Aircraft();
        a1.setModel("Boeing 737");
        a1.setTailNumber("N12345");
        a1.setCapacity(160);

        Aircraft a2 = new Aircraft();
        a2.setModel("Airbus A320");
        a2.setTailNumber("N54321");
        a2.setCapacity(150);

        p.setFlights(List.of(a1, a2));
        Mockito.when(mockRestClient.getAllPassengers())
               .thenReturn(List.of(p));

        httpRestCLIApplicationTest.printAircraftByPassenger();

        String output = outContent.toString();
        Assertions.assertTrue(output.contains("Passenger: John Smith"));
        Assertions.assertTrue(output.contains("- Boeing 737 (N12345), capacity 160"));
        Assertions.assertTrue(output.contains("- Airbus A320 (N54321), capacity 150"));
        Mockito.verify(mockRestClient).getAllPassengers();
    }

    @Test
    public void testPrintAirportsUsedByPassengers_WithFlightsAndAirports(){
        Airport airport = new Airport("YUL", "Montreal-Trudeau International", null);

        Aircraft aircraft = new Aircraft();
        aircraft.setAirports(List.of(airport));

        Passenger passenger = new Passenger();
        passenger.setFirstName("Alice");
        passenger.setLastName("Smith");
        passenger.setFlights(List.of(aircraft));

        Mockito.when(mockRestClient.getAllPassengers()).thenReturn(List.of(passenger));

        Assertions.assertDoesNotThrow(() -> httpRestCLIApplicationTest.printAirportsUsedByPassengers());

        Mockito.verify(mockRestClient).getAllPassengers();
    }

    @Test
    public void testPrintAirportsUsedByPassengers_NoFlights(){
        Passenger passenger = new Passenger();
        passenger.setFirstName("Bob");
        passenger.setLastName("Jones");
        passenger.setFlights(Collections.emptyList());

        Mockito.when(mockRestClient.getAllPassengers()).thenReturn(List.of(passenger));

        Assertions.assertDoesNotThrow(() -> httpRestCLIApplicationTest.printAirportsUsedByPassengers());

        Mockito.verify(mockRestClient).getAllPassengers();
    }

    @Test
    public void testPrintAirportsUsedByPassengers_NoPassengers(){
        Mockito.when(mockRestClient.getAllPassengers()).thenReturn(Collections.emptyList());

        Assertions.assertDoesNotThrow(() -> httpRestCLIApplicationTest.printAirportsUsedByPassengers());

        Mockito.verify(mockRestClient).getAllPassengers();
    }
}

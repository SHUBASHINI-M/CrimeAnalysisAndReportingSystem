package com.hexa.dao;
import com.hexa.entity.Incident;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class Unittesting {
    private CrimeAnalysisServiceImpl service;

    @Before
    public void setUp() {
        service = new CrimeAnalysisServiceImpl();
    }

    @Test
    public void testGetIncidentById() {
        // Assuming there's an incident with ID 1 in the database
        // Retrieve the incident by ID and assert that it is not null
        Incident incident = service.getIncidentById(1);
        assertNotNull(incident);
    }

    @Test
    public void testUpdateIncidentStatus() {
        boolean result = service.updateIncidentStatus("Closed", 1);
        assertTrue(result);
    }
}
 
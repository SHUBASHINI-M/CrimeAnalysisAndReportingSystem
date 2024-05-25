package com.hexa.dao;

import com.hexa.entity.*;

import java.util.Collection;
import java.util.Date;

public interface ICrimeAnalysisService {
    boolean createIncident(Incident incident);
    boolean updateIncidentStatus(String status, int incidentId);
    Collection<Incident> getIncidentsInDateRange(Date startDate, Date endDate);
    Collection<Incident> searchIncidents(String criteria);
    Report generateIncidentReport(Incident incident);
    Cases createCase(String caseDescription, Collection<Incident> incidents);
    Cases getCaseDetails(int caseId);
    boolean updateCaseDetails(Cases caseObj);
    Collection<Cases> getAllCases();
	Incident getIncidentById(int id);
}

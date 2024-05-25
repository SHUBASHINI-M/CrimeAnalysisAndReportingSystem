package com.hexa.main;


import com.hexa.dao.CrimeAnalysisServiceImpl;
import com.hexa.entity.Cases;
import com.hexa.entity.Incident;
import com.hexa.entity.Report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class mainmod {
    public static void main(String[] args) {
    	System.out.println("Crime Analysis and Reporting System");
    	System.out.println("**************************");
        CrimeAnalysisServiceImpl service = new CrimeAnalysisServiceImpl();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Create Incident");
            System.out.println("2. Update Incident Status");
            System.out.println("3. Get Incidents in Date Range");
            System.out.println("4. Search Incidents");
            System.out.println("5. Generate Incident Report");
            System.out.println("6. Create Case");
            System.out.println("7. Get Case Details");
            System.out.println("8. Update Case Details");
            System.out.println("9. Get All Cases");
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    // Create Incident
                    System.out.println("Enter Incident ID: ");
                    int incidentID = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.println("Enter Incident Type: ");
                    String incidentType = scanner.nextLine();
                    System.out.println("Enter Incident Date (YYYY-MM-DD): ");
                    String incidentDateStr = scanner.nextLine();
                    Date incidentDate = java.sql.Date.valueOf(incidentDateStr);
                    System.out.println("Enter Location: ");
                    String location = scanner.nextLine();
                    System.out.println("Enter Description: ");
                    String description = scanner.nextLine();
                    System.out.println("Enter Status: ");
                    String status = scanner.nextLine();
                    System.out.println("Enter Victim ID: ");
                    int victimID = scanner.nextInt();
                    System.out.println("Enter Suspect ID: ");
                    int suspectID = scanner.nextInt();

                    Incident incident = new Incident(incidentID, incidentType, incidentDate, location, description, status, victimID, suspectID);
                    boolean incidentCreated = service.createIncident(incident);
                    if (incidentCreated) {
                        System.out.println("Incident created successfully.");
                    } else {
                        System.out.println("Failed to create incident.");
                    }
                    break;

                case 2:
                    // Update Incident Status
                    System.out.println("Enter Incident ID: ");
                    int updateIncidentID = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.println("Enter new Status: ");
                    String newStatus = scanner.nextLine();
                    boolean statusUpdated = service.updateIncidentStatus(newStatus, updateIncidentID);
                    if (statusUpdated) {
                        System.out.println("Incident status updated successfully.");
                    } else {
                        System.out.println("Failed to update incident status.");
                    }
                    break;

                case 3:
                    // Get Incidents in Date Range
                    System.out.println("Enter Start Date (YYYY-MM-DD): ");
                    String startDateStr = scanner.nextLine();
                    Date startDate = java.sql.Date.valueOf(startDateStr);
                    System.out.println("Enter End Date (YYYY-MM-DD): ");
                    String endDateStr = scanner.nextLine();
                    Date endDate = java.sql.Date.valueOf(endDateStr);

                    Collection<Incident> incidentsInDateRange = service.getIncidentsInDateRange(startDate, endDate);
                    System.out.println("Incidents in Date Range:");
                    for (Incident inc : incidentsInDateRange) {
                        System.out.println(inc);
                    }
                    break;

                case 4:
                    // Search Incidents
                    System.out.println("Enter search criteria: ");
                    String criteria = scanner.nextLine();
                    Collection<Incident> searchedIncidents = service.searchIncidents(criteria);
                    System.out.println("Search Results:");
                    for (Incident inc : searchedIncidents) {
                        System.out.println(inc);
                    }
                    break;

                case 5:
                    // Generate Incident Report
                    System.out.println("Enter Incident ID: ");
                    int reportIncidentID = scanner.nextInt();
                    Incident reportIncident = service.getIncidentById(reportIncidentID); // Ensure this method is implemented in the service
                    if (reportIncident != null) {
                        Report report = service.generateIncidentReport(reportIncident);
                        System.out.println("Generated Report:");
                        System.out.println(report);
                    } else {
                        System.out.println("Incident not found.");
                    }
                    break;

                case 6:
                    System.out.print("Enter Case Description: ");
                    String caseDescription = scanner.nextLine();
                    System.out.print("Enter number of incidents: ");
                    int numIncidents = scanner.nextInt();
                    List<Incident> caseIncidents = new ArrayList<>();
                    for (int i = 0; i < numIncidents; i++) {
                        System.out.print("Enter Incident ID: ");
                        int caseIncidentID = scanner.nextInt();
                        Incident caseIncident = service.getIncidentById(caseIncidentID); // Assuming this method exists in the service
                        caseIncidents.add(caseIncident);
                    }
                    Cases newCase = service.createCase(caseDescription, caseIncidents);
                    System.out.println("New case created: " + newCase);
                    break;

                case 7:
                    // Get Case Details
                    System.out.println("Enter Case ID: ");
                    int caseID = scanner.nextInt();
                    Cases caseDetails = service.getCaseDetails(caseID);
                    if (caseDetails != null) {
                        System.out.println("Case Details:");
                        System.out.println(caseDetails);
                    } else {
                        System.out.println("Case not found.");
                    }
                    break;

                case 8:
                    // Update Case Details
                    System.out.println("Enter Case ID: ");
                    int updateCaseID = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.println("Enter new Case Description: ");
                    String newCaseDescription = scanner.nextLine();
                    Cases updateCase = new Cases(updateCaseID, newCaseDescription, null); // Adjust as per your constructor
                    boolean caseUpdated = service.updateCaseDetails(updateCase);
                    if (caseUpdated) {
                        System.out.println("Case details updated successfully.");
                    } else {
                        System.out.println("Failed to update case details.");
                    }
                    break;

                case 9:
                    // Get All Cases
                    Collection<Cases> allCases = service.getAllCases();
                    System.out.println("All Cases:");
                    for (Cases c : allCases) {
                        System.out.println(c);
                    }
                    break;

                case 0:
                    // Exit
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}

package com.hexa.dao;

import com.hexa.entity.*;
import com.hexa.util.DBConnection;
import java.sql.PreparedStatement;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class CrimeAnalysisServiceImpl implements ICrimeAnalysisService {
    private static Connection connection;

    public CrimeAnalysisServiceImpl() {
        connection = DBConnection.getConnection();
    }
  
    
    @Override
    public boolean createIncident(Incident incident) {
        boolean isCreated = false;
        try {
            String query = "INSERT INTO Incident (IncidentID, IncidentType, IncidentDate, Location, Description, Status, VictimID, SuspectID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, incident.getIncidentID());
            stmt.setString(2, incident.getIncidentType());
            java.sql.Date sqlDate = new java.sql.Date(incident.getIncidentDate().getTime());
            stmt.setDate(3, sqlDate);
            stmt.setString(4, incident.getLocation());
            stmt.setString(5, incident.getDescription());
            stmt.setString(6, incident.getStatus());
            stmt.setInt(7, incident.getVictimID());
            stmt.setInt(8, incident.getSuspectID());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                isCreated = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isCreated;
    }

    /*@Override
    public boolean createIncident(Incident incident) {
        String query = "INSERT INTO Incident (incidentID, incidentType, incidentDate, location, description, status, victimID, suspectID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, incident.getIncidentID());
            stmt.setString(2, incident.getIncidentType());
            stmt.setDate(3, new java.sql.Date(incident.getIncidentDate().getTime()));
            stmt.setString(4, incident.getLocation());
            stmt.setString(5, incident.getDescription());
            stmt.setString(6, incident.getStatus());
            stmt.setInt(7, incident.getVictimID());
            stmt.setInt(8, incident.getSuspectID());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }*/

    @Override
    public boolean updateIncidentStatus(String status, int incidentId) {
        String query = "UPDATE Incident SET status = ? WHERE incidentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, incidentId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Collection<Incident> getIncidentsInDateRange(Date startDate, Date endDate) {
        String query = "SELECT * FROM Incident WHERE incidentDate BETWEEN ? AND ?";
        Collection<Incident> incidents = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, new java.sql.Date(startDate.getTime()));
            stmt.setDate(2, new java.sql.Date(endDate.getTime()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Incident incident = new Incident(
                    rs.getInt("incidentID"),
                    rs.getString("incidentType"),
                    rs.getDate("incidentDate"),
                    rs.getString("location"),
                    rs.getString("description"),
                    rs.getString("status"),
                    rs.getInt("victimID"),
                    rs.getInt("suspectID")
                );
                incidents.add(incident);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return incidents;
    }

    @Override
    public Collection<Incident> searchIncidents(String criteria) {
        String query = "SELECT * FROM Incident WHERE description LIKE ?";
        Collection<Incident> incidents = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + criteria + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Incident incident = new Incident(
                    rs.getInt("incidentID"),
                    rs.getString("incidentType"),
                    rs.getDate("incidentDate"),
                    rs.getString("location"),
                    rs.getString("description"),
                    rs.getString("status"),
                    rs.getInt("victimID"),
                    rs.getInt("suspectID")
                );
                incidents.add(incident);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return incidents;
    }

    @Override
    public Report generateIncidentReport(Incident incident) {
        Report report = new Report();
        report.setIncidentID(incident.getIncidentID());
        report.setReportDetails("Generated report for incident: " + incident.getDescription());
        report.setReportDate(new Date());
        report.setStatus("Generated");
        return report;
    }

    @Override
    public Cases createCase(String caseDescription, Collection<Incident> incidents) {
        Cases casesObj = new Cases();
        casesObj.setCaseDescription(caseDescription);
        casesObj.setIncidents(incidents);
        return casesObj;
    }
    

    
    @Override
    public Incident getIncidentById(int id) {
        String query = "SELECT * FROM Incident WHERE incidentID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int incidentID = resultSet.getInt("incidentID");
                String incidentType = resultSet.getString("incidentType");
                Date incidentDate = resultSet.getDate("incidentDate");
                String location = resultSet.getString("location");
                String description = resultSet.getString("description");
                String status = resultSet.getString("status");
                int victimID = resultSet.getInt("victimID");
                int suspectID = resultSet.getInt("suspectID");

                // Create and return an Incident object
                return new Incident(incidentID, incidentType, incidentDate, location, description, status, victimID, suspectID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no incident found with the given ID
    } 

    @Override
    public Cases getCaseDetails(int caseId) {
        String query = "SELECT * FROM `Cases` WHERE caseID = ?";
        Cases caseObj = null;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, caseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                caseObj = new Cases();
                caseObj.setCaseID(rs.getInt("caseID"));
                caseObj.setCaseDescription(rs.getString("caseDescription"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return caseObj;
    }

    @Override
    public boolean updateCaseDetails(Cases caseObj) {
        String query = "UPDATE Cases SET caseDescription = ? WHERE caseID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, caseObj.getCaseDescription());
            stmt.setInt(2, caseObj.getCaseID());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Collection<Cases> getAllCases() {
        String query = "SELECT * FROM Cases";
        Collection<Cases> cases = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Cases caseObj = new Cases();
                caseObj.setCaseID(rs.getInt("caseID"));
                caseObj.setCaseDescription(rs.getString("caseDescription"));
                cases.add(caseObj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cases;
    }

	
}

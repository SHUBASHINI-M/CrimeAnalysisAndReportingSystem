package com.hexa.entity;

import java.util.Collection;

public class Cases {

	private int caseID;
    private String caseDescription;
    private Collection<Incident> incidents;

    
    public Cases() {}

    public Cases(int caseID, String caseDescription, Collection<Incident> incidents) {
        this.caseID = caseID;
        this.caseDescription = caseDescription;
        this.incidents = incidents;
    }

    // Getters and setters
    public int getCaseID() {
        return caseID;
    }

    public void setCaseID(int caseID) {
        this.caseID = caseID;
    }

    public String getCaseDescription() {
        return caseDescription;
    }

    public void setCaseDescription(String caseDescription) {
        this.caseDescription = caseDescription;
    }

    public Collection<Incident> getIncidents() {
        return incidents;
    }

    public void setIncidents(Collection<Incident> incidents) {
        this.incidents = incidents;
    }
    @Override
   	public String toString() {
   		return "Case [caseID=" + caseID + ", caseDescription=" + caseDescription + ", incidents=" + incidents + "]";
   	}
}

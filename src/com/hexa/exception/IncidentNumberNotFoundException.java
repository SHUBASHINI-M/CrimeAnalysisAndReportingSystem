package com.hexa.exception;

import com.hexa.entity.Incident;

public class IncidentNumberNotFoundException extends Exception {
    public IncidentNumberNotFoundException(String message) {
        super(message);
    }
}


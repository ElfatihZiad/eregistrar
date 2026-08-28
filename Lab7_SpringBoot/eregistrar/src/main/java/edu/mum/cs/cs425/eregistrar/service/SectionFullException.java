package edu.mum.cs.cs425.eregistrar.service;

/** Thrown by RegistrationService.register() when a section has no seats left (BR7). */
public class SectionFullException extends RuntimeException {

    public SectionFullException(String message) {
        super(message);
    }
}

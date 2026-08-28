package edu.mum.cs.cs425.eregistrar.service;

/** Thrown by RegistrationService.register() when the student already holds this registration. */
public class AlreadyRegisteredException extends RuntimeException {

    public AlreadyRegisteredException(String message) {
        super(message);
    }
}

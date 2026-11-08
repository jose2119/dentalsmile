package com.dentalsmile.dentalsmile.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String recurso, Object id) {
        super(recurso + " con id '" + id + "' no encontrado(a).");
    }
    public ResourceNotFoundException(String message) {
        super(message);
    }
}


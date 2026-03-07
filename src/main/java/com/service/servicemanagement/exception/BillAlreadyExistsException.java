package com.service.servicemanagement.exception;

public class BillAlreadyExistsException extends RuntimeException {

    public BillAlreadyExistsException(String message) {
        super(message);
    }
}
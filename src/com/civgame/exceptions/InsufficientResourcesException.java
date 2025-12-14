package com.civgame.exceptions;

// Custom Exception for Game Logic [Lecture 23-25, Slide 7]
public class InsufficientResourcesException extends Exception {
    public InsufficientResourcesException(String message) {
        super(message); // Pass message to parent Exception class
    }
}

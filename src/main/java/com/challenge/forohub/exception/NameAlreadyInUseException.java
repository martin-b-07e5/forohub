package com.challenge.forohub.exception;

public class NameAlreadyInUseException extends RuntimeException {
  public NameAlreadyInUseException(String message) {
    super(message);
  }
}

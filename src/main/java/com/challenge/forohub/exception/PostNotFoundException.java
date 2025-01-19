package com.challenge.forohub.exception;

public class PostNotFoundException extends RuntimeException {

  public PostNotFoundException(String message) {
    super(message);
  }

  public PostNotFoundException(Long id) {
    super("Post con ID " + id + " no encontrado");
  }


}

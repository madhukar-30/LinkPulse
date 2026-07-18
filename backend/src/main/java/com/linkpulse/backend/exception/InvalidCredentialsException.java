package com.linkpulse.backend.exception;

public class InvalidCredentialsException extends RuntimeException{

   public InvalidCredentialsException(String message){

       super(message);
   }

}

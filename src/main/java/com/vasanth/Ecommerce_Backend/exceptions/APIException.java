package com.vasanth.Ecommerce_Backend.exceptions;

public class APIException extends RuntimeException{

    public APIException(){

    }

    public APIException(String message){
        super(message);
    }
}
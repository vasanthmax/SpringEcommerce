package com.vasanth.Ecommerce_Backend.exceptions;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;
    private String field;
    private String fieldName;
    private UUID fieldId;

    public ResourceNotFoundException(){

    }

    public ResourceNotFoundException(String resourceName,String field,String fieldName){
        super(String.format("%s not found with %s: %s",resourceName,field,fieldName));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldName = fieldName;

    }

    public ResourceNotFoundException(String resourceName,String field,UUID fieldId){
        super(String.format("%s not found with %s: %s",resourceName,field,fieldId));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = fieldId;

    }
}

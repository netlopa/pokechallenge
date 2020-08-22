package com.netlopa.pokechallenge.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.netlopa.pokechallenge.exceptions.PokeApiWSException;
import com.netlopa.pokechallenge.exceptions.PokemonNotFoundException;
import com.netlopa.pokechallenge.exceptions.ShakespeareApiRateLimitsExceededException;
import com.netlopa.pokechallenge.exceptions.ShakespeareApiWSException;

/**
 * This class check the exceptions that are thrown in the application and give a human readable response to the end user
 * @author lpavez
 *
 */
@ControllerAdvice
public class RestErrorHandler {

    @ExceptionHandler(PokemonNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Object processPokemonNotFound(PokemonNotFoundException ex) {
        return "Pokemon Not Found";
    }
    
    @ExceptionHandler(PokeApiWSException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  
    @ResponseBody
    public Object processPokeApiWSException(PokeApiWSException ex) {
        return "Internal Server Error"; //I avoid to put the real error
    }
    
    @ExceptionHandler(ShakespeareApiWSException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Object processShakespeareApiWSException(ShakespeareApiWSException ex) {
        return "Internal Server Error"; //I avoid to put the real error
    }
    
    @ExceptionHandler(ShakespeareApiRateLimitsExceededException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Object processShakespeareApiRateLimitsExceededException(ShakespeareApiRateLimitsExceededException ex) {
        return "Rate Limits Exceeded: for new pokemon you need to wait 60 minutes in order that the limits are reset"; 
    }
}

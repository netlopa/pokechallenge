package com.netlopa.pokechallenge.controller;

import org.springframework.web.bind.annotation.RestController;

import com.netlopa.pokechallenge.dto.PokeDto;
import com.netlopa.pokechallenge.exceptions.PokeApiWSException;
import com.netlopa.pokechallenge.exceptions.PokemonNotFoundException;
import com.netlopa.pokechallenge.exceptions.ShakespeareApiRateLimitsExceededException;
import com.netlopa.pokechallenge.exceptions.ShakespeareApiWSException;
import com.netlopa.pokechallenge.service.PokeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class PokeController {
    
    @Autowired
    PokeService pokeService;
   
    private static final Logger LOGGER = LogManager.getLogger();

    @ApiOperation(value = "Retrieve the Shakesperean Phrase of the provided Pokemon")
    @ApiResponses(value = {
        @io.swagger.annotations.ApiResponse(code = 200, message = "Pokemon phrase"),
        @io.swagger.annotations.ApiResponse(code = 400, message = "Pokemon Not Found")
    })
	@GetMapping(value = "/pokemon/{pokemonName}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public PokeDto getPokemonShakespereanPhrase(@PathVariable String pokemonName) throws PokemonNotFoundException, PokeApiWSException, ShakespeareApiWSException, ShakespeareApiRateLimitsExceededException, IOException {
	    
	    LOGGER.info("Executing WS for getting the pokemon phrase with " + pokemonName);
	    PokeDto dto = pokeService.getPokemonShakespereanPhrase(pokemonName);

		return dto;
	}
	
    @ApiOperation(value = "Purge cache")
    @ApiResponses(value = {
        @io.swagger.annotations.ApiResponse(code = 200, message = "Operation performed"),
    })
	@PostMapping(value = "/purgecache", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public String purgeCache() {
	    LOGGER.info("Purging cache ...");
	    pokeService.purgeCache();
	    
	    return "OK";
	}
	

}

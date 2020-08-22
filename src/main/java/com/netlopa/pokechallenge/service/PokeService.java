package com.netlopa.pokechallenge.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.netlopa.pokechallenge.dto.PokeDto;
import com.netlopa.pokechallenge.exceptions.PokeApiWSException;
import com.netlopa.pokechallenge.exceptions.PokemonNotFoundException;
import com.netlopa.pokechallenge.exceptions.ShakespeareApiRateLimitsExceededException;
import com.netlopa.pokechallenge.exceptions.ShakespeareApiWSException;

@Service
public class PokeService {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    @Autowired
    PokeExternalService pokeExternalService;
   
    /**
     * This method get the Shakesperean phrase, given a name of a Pokemon
     * @param pokemonName
     * @return
     * @throws PokemonNotFoundException
     * @throws PokeApiWSException
     * @throws ShakespeareApiWSException
     * @throws ShakespeareApiRateLimitsExceededException
     */
    @Cacheable("pokemonPhrase")
    public PokeDto getPokemonShakespereanPhrase(String pokemonName) throws PokemonNotFoundException, PokeApiWSException, ShakespeareApiWSException, ShakespeareApiRateLimitsExceededException {
        
        LOGGER.info("Getting Pokemon Shakesperean Phrase for " + pokemonName);
        String pokemonPhrase = pokeExternalService.getPokemonSpeciesPhrase(pokemonName);
        String shakespereanPhrase = pokeExternalService.getShakespeareanPhrase(pokemonPhrase);
        PokeDto pokeDto = new PokeDto(pokemonName, shakespereanPhrase);
        return pokeDto;
        
    }
    
    @CacheEvict(value = "pokemonPhrase", allEntries=true)
    public void purgeCache() {
        LOGGER.info("Cache purged");
    }
        
}

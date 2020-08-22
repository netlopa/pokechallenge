package com.netlopa.pokechallenge.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.netlopa.pokechallenge.dto.PokeDto;
import com.netlopa.pokechallenge.exceptions.PokeApiWSException;
import com.netlopa.pokechallenge.exceptions.PokemonNotFoundException;
import com.netlopa.pokechallenge.exceptions.ShakespeareApiRateLimitsExceededException;
import com.netlopa.pokechallenge.exceptions.ShakespeareApiWSException;

/**
 * Test for the service
 * Unfortunately I put only the tests related to the PokeAPI because of the strict rate limiting of the Funtranslations API
 * @author lpavez
 *
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PokeServiceTest {
    
    @InjectMocks
    PokeService pokeService;
    
    @Mock
    PokeExternalService pokeExternalService;
    
    @Test
    public void shouldNotGetPhraseAndThrowPokemonNotFound() throws PokemonNotFoundException, PokeApiWSException {
        
        String fakePokemon = "dummymon";
        given(pokeExternalService.getPokemonSpeciesPhrase(fakePokemon)).willThrow(PokemonNotFoundException.class);
        
        Assertions.assertThrows(PokemonNotFoundException.class, () -> {
            pokeService.getPokemonShakespereanPhrase(fakePokemon);
        });

    }
    
    @Test
    public void shouldNotGetPhraseAndThrowShakespeareApiRateLimitsExceededException() throws PokemonNotFoundException, PokeApiWSException, ShakespeareApiWSException, ShakespeareApiRateLimitsExceededException {
        
        String pokemon = "charizard";
        given(pokeExternalService.getPokemonSpeciesPhrase(pokemon)).willReturn("phrase");
        given(pokeExternalService.getShakespeareanPhrase("phrase")).willThrow(ShakespeareApiRateLimitsExceededException.class);
        
        Assertions.assertThrows(ShakespeareApiRateLimitsExceededException.class, () -> {
            pokeService.getPokemonShakespereanPhrase(pokemon);
        });

    }
    
    @Test
    public void shouldGetPhrase() throws PokemonNotFoundException, PokeApiWSException, ShakespeareApiWSException, ShakespeareApiRateLimitsExceededException {
        
        String pokemon = "dummy";
        String phrase = "shakesperean";
        given(pokeExternalService.getPokemonSpeciesPhrase(pokemon)).willReturn("phrase");
        given(pokeExternalService.getShakespeareanPhrase("phrase")).willReturn(phrase);
        
        PokeDto dto = pokeService.getPokemonShakespereanPhrase(pokemon);
        assertThat(phrase).isEqualTo(dto.getDescription());
        assertThat(pokemon).isEqualTo(dto.getName());
        
    }
}

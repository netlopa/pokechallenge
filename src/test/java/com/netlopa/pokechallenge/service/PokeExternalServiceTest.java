package com.netlopa.pokechallenge.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.netlopa.pokechallenge.exceptions.PokeApiWSException;
import com.netlopa.pokechallenge.exceptions.PokemonNotFoundException;

import static org.assertj.core.api.Assertions.*;

/**
 * Test for the external service
 * Unfortunately I put only the tests related to the PokeAPI because of the strict rate limiting of the Funtranslations API
 * @author lpavez
 *
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PokeExternalServiceTest {
    
    @InjectMocks
    PokeExternalService pokeExternalService;

    @Test
    public void shouldGetPhrase() throws PokemonNotFoundException, PokeApiWSException {
        
        String phrase = pokeExternalService.getPokemonSpeciesPhrase("charizard");
        assertThat(phrase).isEqualTo("Spits fire that is hot enough to melt boulders. Known to cause forest fires unintentionally.");

    }
    
    @Test
    public void shouldNotGetPhraseAndThrowPokemonNotFound() throws PokemonNotFoundException, PokeApiWSException {
        
        Assertions.assertThrows(PokemonNotFoundException.class, () -> {
            pokeExternalService.getPokemonSpeciesPhrase("dummymon");
        });

    }
}

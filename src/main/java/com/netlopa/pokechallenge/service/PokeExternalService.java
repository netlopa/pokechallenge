package com.netlopa.pokechallenge.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.netlopa.pokechallenge.api.pokeapi.entities.PokemonSpecies;
import com.netlopa.pokechallenge.api.shakespeareapi.entities.ShakespeareApiResponse;
import com.netlopa.pokechallenge.exceptions.PokeApiWSException;
import com.netlopa.pokechallenge.exceptions.PokemonNotFoundException;
import com.netlopa.pokechallenge.exceptions.ShakespeareApiRateLimitsExceededException;
import com.netlopa.pokechallenge.exceptions.ShakespeareApiWSException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class PokeExternalService {

    private static final Logger LOGGER = LogManager.getLogger();
    
    /**
     * This method is calling the PokeAPI WS to retrieve the phrase of the Pokemon
     * @param pokemonName
     * @return
     * @throws PokemonNotFoundException
     * @throws PokeApiWSException
     */
    public String getPokemonSpeciesPhrase(String pokemonName) throws PokemonNotFoundException, PokeApiWSException {

        LOGGER.debug("Calling PokeAPI WS with " + pokemonName);
        String json = null;
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
                  Request request = new Request.Builder()
                    .url("https://pokeapi.co/api/v2/pokemon-species/" + pokemonName.toLowerCase())
                    .method("GET", null)
                    .build();
                  Response response = client.newCall(request).execute();
            
            json = response.body().string();       
            
        }
        catch (Exception e) {
            LOGGER.error("an error occurred during pokemon retrieval",e);
            throw new PokeApiWSException();
        }
        
        Gson gson = new Gson();
        if ("Not Found".equalsIgnoreCase(json)) throw new PokemonNotFoundException();
        PokemonSpecies pokemonSpeciesResponse = gson.fromJson(json, PokemonSpecies.class);
        
        Optional<String> pokemonPhrase = pokemonSpeciesResponse.getFlavorTextEntries().stream().filter(item -> {
            return ("en".equalsIgnoreCase(item.getLanguage().getName()));
        }).map(item -> item.getFlavorText()).findFirst();

        if (!pokemonPhrase.isPresent()) {
            return "";
        }
        return pokemonPhrase.get().replace("\n", " ").replace("\f", " ");
    
    }
    
    /**
     * This method is calling the Funtranslations API to get the Shakespearean phrase given the original one
     * @param phrase
     * @return
     * @throws ShakespeareApiWSException
     * @throws ShakespeareApiRateLimitsExceededException
     */
    public String getShakespeareanPhrase(String phrase) throws ShakespeareApiWSException, ShakespeareApiRateLimitsExceededException {
        LOGGER.debug("Calling Shakespeare API WS with " + phrase);
        String json = null;
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
              Request request = new Request.Builder()
                .url("https://api.funtranslations.com/translate/shakespeare.json?text=" + phrase)
                .method("GET", null)
                .build();
              Response response = client.newCall(request).execute();
              json = response.body().string();
        }
        catch (Exception e) {
            LOGGER.error("an error occurred during shakespeare phrase retrieval",e);
            throw new ShakespeareApiWSException();
        }
        
        if (json.toLowerCase().contains("too many requests")) {
            throw new ShakespeareApiRateLimitsExceededException();
        }
        Gson gson = new Gson();
        ShakespeareApiResponse shakespeareResponse = gson.fromJson(json, ShakespeareApiResponse.class);
        if (new Integer(1).equals(shakespeareResponse.getSuccess().getTotal())) {
            return shakespeareResponse.getContents().getTranslated();
        }
        return null;
    }
}

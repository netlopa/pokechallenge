package com.netlopa.pokechallenge.dto;

/**
 * DTO returned to the end user with the name and the shakesperean phrase
 * @author lpavez
 *
 */
public class PokeDto {

    private String name;
    private String description;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public PokeDto(String name, String description) {
        super();
        this.name = name;
        this.description = description;
    }
    
    
}

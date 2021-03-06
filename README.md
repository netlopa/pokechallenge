
# Pokemon Challenge

## Problem

Expose a REST API that, given a Pokemon name, returns its Shakespearean description

### Get Shakesperean Phrase

Use this API to get the Shakesperean Phrase related to the given pokemon
**`GET /pokemon/<pokemon name>`**
For example you can call this endpoint:
`http://localhost:8080/pokemon/pikachu`
And you get this response:
```
{
    "name": "pikachu",
    "description": "At which hour several of these pokémon gather,  their electricity couldst buildeth and cause lightning storms."
}
```

### Purge cache (additional feature)

**`POST /purgecache`**
With this API you purge the cache 

## Technology

This project was made with Java 8 with the Spring Boot framework

## How to run the application (standalone)

1. Make sure you have Java 8 installed and properly configured
2. Run the following command
```
git clone https://github.com/netlopa/pokechallenge.git
```
3. Inside the created folder you need to run 
```
mvn spring-boot:run
```
4. Run cURL or Postman (example with curl)
```
curl http://localhost:8080/pokemon/pikachu
```
## How to run the application with Docker

1. Make sure you have Java 8 installed and properly configured and clone the project
2. First of all you need to create the JAR package of the application: you need to go in the main folder of the project that you cloned and then execute this command
```
./mvnw package
```
3. Now, you need to create the Docker image, using this command
```
docker build -t netlopa/pokechallenge .
```
4. If you want to run the Docker image, run this command
```
docker run -p 8080:8080 -t netlopa/pokechallenge
```
## Design choices

This application connects to two APIs: PokeAPI and Shakespeare translator provided by funtranslations.

While the first API has not particular restrictions, the second one has a very strict API calls rates (60 calls per day AND 5 calls per hour).

So I decided to add the cache support to the project in order to optimize the API calls, particularly for the constraints of Funtranslations.

So it means that if I call the endpoint `http://localhost:8080/pokemon/pikachu` , the first time the application will perform all the needed API calls. From the second time and so on, for that Pokemon, the result will be retrieved from the application Cache.

If you want to purge the cache, I added an utility API called purgecache that will delete all the cache.
   
## Useful links

- **PokeAPI**: https://pokeapi.co/
- **Shakespeare translator**: https://funtranslations.com/api/shakespeare




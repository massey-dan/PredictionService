# PredictionService
A small service to manage predictions

## Assumptions 
1. Users cannot predict a draw
2. Users are trusted (There's no authentication on users)
3. Users like JSON returns but aren't too fussy about error types.
<br>In a full system I'd confirm how the users wanted data returned.
<br>Currently errors are returned as raw strings, but everything else is returning JSON objects.
4. It's just me working on this project as a one-off tech test, so incremental Git commits and full javadoc is not a priority.


## Running the app
This app required Java 21 and Maven for local builds, and required Docker to be installed to run. 

In order to run the app:
```bash
# 1. Build the app
./mvnw clean package -DskipTests

# 2. Start the services
docker-compose up --build
```

## Using the app
A postman collection is included with example calls for each of the endpoints. 
`PredictionService.postman_collection.json` 

If this was a wider used API, I'd add swagger documentation for the project.

## Next steps / Tradeoffs
I believe this app covers all the requirements detailed in the assignment. 
<br>However, future additions I would suggest are:
1. Add an endpoint to retrieve a finished match, that calculates which users have correctly guessed the result
2. Add other prediction types for a finishing order or a score (Hence abstract Prediction class)
3. Allow users to predict a draw
4. If this was used for long enough I'd want an endpoint only to get matches that haven't started yet, and possibly also one that takes a user id and returns matches that haven't started yet and that user hasn't placed a prediction on
5. Set more secure db variables, and don't store them in a public Git repo 
6. Stop duplicate matches being created (some form of check on teams and time)
7. Add some delete endpoints
8. Address a lack of integration/unit tests
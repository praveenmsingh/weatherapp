<h1> Weather APP </h1>

This is a Java 17, Springboot 3 API service which allows users to fetch near-real-time weather data of Top 20 populated Australian cities.

<h4> Notes </h4>

1. On startup, application loads up `City` geo-coordinates from `src/main/resources/application.yml` onto the local postgres db, and then proceeds to fetch Current weather data from Openweathermap.org for those cities.
2. The secrets such as database credentials and the APIKEY for Openweathermap.org API are stored in `application.yml`, but could be injected from a secrets management tool like AWS Secrets Manager or Hashicorp vault at runtime.
3. I wasn't able to add in the feature to track the `number of modifications` to `User profile`, as I prioritised it to the very end.
4. I wasn't able to add in the API to `fetch all user profiles for a given user`, as I prioritised it to the very end.


<h3> Steps to Run </h3>

1. Navigate to the root of the project folder.

2. Bring up the docker containers (in daemon mode)
 
```bash
   $ docker-compose up -d
```

3. Bring up the APIs
```bash
   $ ./gradlew bootRun 
```

<h3> Steps to test </h3>


1. Navigate to the root of the project folder.

2. Bring up the docker containers (in daemon mode)

```bash
   $ docker-compose up -d
```

3. Bring up the APIs
```bash
   $ ./gradlew test componentTest 
```
4. Test summary can be viewed on the web-browser by opening the file `./build/reports/test/index.html`

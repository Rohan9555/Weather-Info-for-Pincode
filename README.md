# Weather Info API (Pincode Based)

## Overview

This is a Spring Boot application that fetches weather information based on pincode and date. It stores the data in the database to avoid repeated API calls.

## Approach

* Check if weather data exists in the database for given pincode and date
* If present, return stored data
* If not, fetch latitude and longitude using external API
* Fetch weather data using coordinates
* Save response in database and return result

## APIs

* GET `/api/weather?pincode={pincode}&date={date}` – Get weather details

## Edge Cases

* Invalid or missing pincode
* External API failure
* Data not found in database

## Testing

Unit testing is implemented using JUnit and Mockito.

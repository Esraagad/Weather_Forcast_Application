# Weather Forecast Application
The Daily Forecast Application is a simple Android app that provides a daily weather forecast for a list of cities. Users can select a city from a dropdown menu and see the weather forecast, including details such as temperature and weather conditions like rain, snow, or clear sky. The app also handles data caching, enabling users to see previously fetched forecasts even without an internet connection.

## Table of Contents
- Features
- Architecture and Design
- Technologies Used
- Installation and Setup Instructions
- Usage
- Testing
  
## Features
- Daily Weather Forecast: 
  Fetches the weather forecast for a selected city from the dropdown.
  City Selection: Users can choose from a list of predefined cities.
- Weather Caching:
  Stores weather data locally so that users can access previously fetched data even if there is no   internet connection.
- Retry on Failure:
  Provides an option to retry the request in case of data retrieval failure.
  
## Architecture and Design
The app follows the MVVM (Model-View-ViewModel) architecture pattern, providing a separation of concerns, easier testing, and better maintainability.

## Flow Diagram

```
User -> View -> ViewModel -> Repository -> (Remote API / Local Database)
```

## Technologies Used
- Kotlin: Programming language used for the entire codebase.
- Retrofit: For API requests to fetch weather data.
- Room: For local caching of weather forecasts.
- Hilt: For dependency injection, ensuring a clean and testable architecture.
- Coroutines: To manage background tasks asynchronously.
- Livedata: For Keeping view data state. 
  
## Installation and Setup Instructions
- Prerequisites
  - Android Studio: Version 4.0 or higher.
  - OpenWeather API Key: You need to create an API key from OpenWeather.
- Steps to Install and Run
  - Clone the repository:
  ```
  git clone https://github.com/your-username/daily-forecast-app.git
  ```
  - Open the project in Android Studio.

   - Add API Key:
    - Create a local.properties file in the root directory of the project (if not already present).
    - Add your OpenWeather API key:
    ```API_KEY=YOUR_API_KEY```
  - Build the project in Android Studio by clicking "Build" -> "Make Project".

   - Run the application

## Usage
- Select a City: The top bar contains a dropdown menu that lists available cities. Select a city to see its forecast.
- View Weather Information: The app will display the weather details for the selected city, including the temperature and      weather description.
- Offline Mode: If the internet connection is lost, cached weather data will be shown with a warning indicating that it may    be outdated.
- Retry on Error: If the app is unable to retrieve the data, users will see an error message with an option to retry           fetching the weather.

## Testing
The application is tested with the following types of tests:

- Unit Tests:
  Tests for the WeatherRepository to verify data fetching and caching logic.

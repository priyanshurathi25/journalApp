package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.api.response.weatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.constant.PlaceHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
@Value("${weather.api.key}")
    private String Apikey;
@Autowired
    RestTemplate restTemplate;
@Autowired
    AppCache appCache;

@Autowired
RedisService redisService;
    public weatherResponse getWeather(String city){
        weatherResponse weatherResponse = redisService.get(" Weather_of_ " + city, weatherResponse.class);
        if (weatherResponse!=null){
            return weatherResponse;
        }else {
            String finalApi = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(PlaceHolder.City, city).replace(PlaceHolder.Api_key, Apikey);
            ResponseEntity<weatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, weatherResponse.class);
            weatherResponse body = response.getBody();
            if (body!=null){
                redisService.set("Weather_of_"+city, body , 300l);
            }
            return body;
        }
    }
}

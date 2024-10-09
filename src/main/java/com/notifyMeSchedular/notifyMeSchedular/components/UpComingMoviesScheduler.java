package com.notifyMeSchedular.notifyMeSchedular.components;

import com.notifyMeSchedular.notifyMeSchedular.DTOs.UpComingMovieDTO;
import jakarta.transaction.Transactional;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static com.notifyMeSchedular.notifyMeSchedular.APIs.API.*;

//Just like react component
//Fetches all upcoming movies every day and updated in movies table.
@Component
public class UpComingMoviesScheduler {

    //Why this used instead of using new RestTemplate ?
    //This reduces the memory load and uses the same bean everywhere.
    //Just like writing Axios in  other file and calling in a component.
    @Autowired
    RestTemplate restTemplate;

    //This constructor is called automatically by the spring boot,during component searching
    //And it finds the autowired annotation and match s the restTemplate class automatically here.
//    @Autowired
//    UpComingMoviesScheduler(RestTemplate restTemplate) {
//        this.restTemplate=restTemplate;
//    }

    Logger logger = LoggerFactory.getLogger(UpComingMoviesScheduler.class);

    //Daily Morning 8:30 AM
//    @Scheduled(cron = "0 30 8 * * ?")
//    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void performDailyApiCall() {
        Logger logger = LoggerFactory.getLogger(UpComingMoviesScheduler.class);
        String apiUrl = UPCOMING_MOVIES + "chennai";

        try {
            List<UpComingMovieDTO> upComingMovieDTOS = fetchUpcomingMovies(apiUrl);
            if (upComingMovieDTOS.isEmpty()) {
                logger.warn("No upcoming movies found.");
                return;
            }
            // Send processed data to API
            sendUpcomingMovies(upComingMovieDTOS);
        } catch (Exception e) {
            logger.error("Error during the daily API call: " + e.getMessage(), e);
        }
    }

    private List<UpComingMovieDTO> fetchUpcomingMovies(String apiUrl) {
        logger.info("Fetching upcoming movies from API: {}", apiUrl);
        try {
            // Fetch data from API
            JSONObject parentJson = new JSONObject(restTemplate.getForObject(apiUrl, String.class));
            JSONArray jsonArray = parentJson.getJSONArray("upcomingMovieData");

            List<UpComingMovieDTO> upComingMovieDTOS = new ArrayList<UpComingMovieDTO>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                UpComingMovieDTO movieDTO = mapJsonToDTO(jsonObject);
                if (movieDTO != null) {
                    upComingMovieDTOS.add(movieDTO);
                }
            }
            return upComingMovieDTOS;
        } catch (Exception e) {
            logger.error("Failed to fetch upcoming movies: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private UpComingMovieDTO mapJsonToDTO(JSONObject jsonObject) {
        try {
            UpComingMovieDTO upComingMovieDTO = new UpComingMovieDTO();
            upComingMovieDTO.setMovieId(String.valueOf(jsonObject.getLong("contentId")));
            upComingMovieDTO.setMoviePosterUrl(jsonObject.getString("moviePosterUrl"));
            upComingMovieDTO.setMovieName(jsonObject.getString("movie_name"));
            upComingMovieDTO.setMovieLanguage(jsonObject.getString("language"));
            upComingMovieDTO.setMovieLanguageGroup(jsonObject.getJSONArray("language_group").getString(0));
            upComingMovieDTO.setMovieGenre(String.valueOf(jsonObject.getJSONArray("genre").get(0)));
            upComingMovieDTO.setMovieReleaseDate(jsonObject.optString("releaseDate", null));
            logger.warn(upComingMovieDTO.toString());
            return upComingMovieDTO;

        } catch (Exception e) {
            LoggerFactory.getLogger(UpComingMoviesScheduler.class).warn("Error mapping movie JSON: {}", e.getMessage());
            return null;
        }
    }

    private void sendUpcomingMovies(List<UpComingMovieDTO> upComingMovieDTOS) {
        Logger logger = LoggerFactory.getLogger(UpComingMoviesScheduler.class);

        try {
            logger.info("Sending upcoming movies data to the API...");
            // Set headers and request body
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<List<UpComingMovieDTO>> request = new HttpEntity<>(upComingMovieDTOS, headers);

            // Send POST request and check status
            ResponseEntity<String> response = restTemplate.exchange(POST_UPCOMING_MOVIES, HttpMethod.POST, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("Data successfully sent to the API.");
            } else {
                logger.warn("Failed to send data. Status Code: {}", response.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("Error sending data to the API: {}", e.getMessage(), e);
        }
    }

}

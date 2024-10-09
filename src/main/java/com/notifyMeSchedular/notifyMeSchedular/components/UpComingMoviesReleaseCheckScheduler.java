package com.notifyMeSchedular.notifyMeSchedular.components;

import com.notifyMeSchedular.notifyMeSchedular.DTOs.MovieResponseDTO;
import com.notifyMeSchedular.notifyMeSchedular.DTOs.UserMovieDTO;
import com.notifyMeSchedular.notifyMeSchedular.notificationHelpers.MovieNotificationHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.notifyMeSchedular.notifyMeSchedular.APIs.API.USER_MOVIES;
import static com.notifyMeSchedular.notifyMeSchedular.constants.Constants.*;

@Component
public class UpComingMoviesReleaseCheckScheduler {
    @Autowired
    RestTemplate restTemplate;

    Logger logger = LoggerFactory.getLogger(UpComingMoviesScheduler.class);

    @Scheduled(cron = "*/5 * * * * *")
    public void getUserMoviesForWebScrapping() {
        List<UserMovieDTO> userMovieDTOList = getUserMovies();
        for (UserMovieDTO userMovieDTO : userMovieDTOList) {
            String movieUrl = userMovieDTO.getMovieUrl();
            {
                try {
                    Document doc = Jsoup.connect(movieUrl)
                            .cookie(MOVIE_COOKIE_NAME, MOVIE_COOKIE_VALUE)
                            .get();
                    Element link = doc.selectFirst("a:contains(Showlisting)");
                    logger.warn("Element :: "+link);
                    if(link!=null && link.text().equalsIgnoreCase(MOVIE_WEB_SCRAP_FLAG)){
                        logger.warn(userMovieDTO.getMovieName() +" OPENED ✅");
                        logger.warn(userMovieDTO.toString());
                      MovieNotificationHelper.getInstance().sendMovieNotification(userMovieDTO);
                    }else {
                        logger.warn(userMovieDTO.getMovieName()+" NOT OPEN \uD83E\uDDF2");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<UserMovieDTO> getUserMovies() {
        ResponseEntity<MovieResponseDTO> response = restTemplate.getForEntity(USER_MOVIES, MovieResponseDTO.class);
        logger.warn("USER MOVIES RESPONSE DATA :: " + response.getBody());
        if (isValidResponse(response)) {
            return Objects.requireNonNull(response.getBody()).getData();
        }
        return new ArrayList<>();
    }

    boolean isValidResponse(ResponseEntity<MovieResponseDTO> response) {
        return Objects.requireNonNull(response.getBody()).getStatusCode().equals("200");
    }




}

package com.notifyMeSchedular.notifyMeSchedular.APIs;

import javax.swing.plaf.PanelUI;

public class API {
    private static String BASE_URL = "http://localhost:8080";
    public static final String UPCOMING_MOVIES = "https://apiproxy.paytm.com/v3/movies/upcoming?city=";
    public static final String POST_UPCOMING_MOVIES = BASE_URL+"/api/v1/movie/upcoming";
    public static final String USER_MOVIES = BASE_URL + "/api/v1/movie";
}


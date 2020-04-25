package com.task.moviesapp.util;

import com.task.moviesapp.network.response.search.SearchResponse;

import java.util.HashMap;

public class Constants {

    public static class Details {
        public static final String IMBD_ID = "imbdId";

        public static HashMap<String, SearchResponse> bookmarkMap = new HashMap<>();
    }
}

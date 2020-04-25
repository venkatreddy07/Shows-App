package com.task.moviesapp.util;

import com.task.moviesapp.network.response.search.SearchList;
import com.task.moviesapp.network.response.search.SearchResponse;

import java.util.HashMap;

public class Constants {

    public static HashMap<String, SearchList> bookmarkMap = new HashMap<>();

    public static class Details {
        public static final String IMBD_ID = "imbdId";
    }

    public static class Pref {
        public static final String BOOK_MRK = "bookMark";
    }
}

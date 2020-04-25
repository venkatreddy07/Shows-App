package com.task.moviesapp.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.task.moviesapp.MyApplication;
import com.task.moviesapp.network.response.search.SearchList;
import com.task.moviesapp.ui.search.SearchActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Utils {

    private static Gson gson = new Gson();

    public static boolean isNetworkAvailable(final Context context) {
        if (context == null) return false;
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        if (Objects.requireNonNull(connectivityManager).getActiveNetworkInfo() != null) {
            try {
                return connectivityManager.getActiveNetworkInfo().isConnected();
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }


    public static void hideKeyboard(SearchActivity activity) {
        if (activity == null) return;
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //store bookmarked data in map and in shared preference
    public static void storeBookmarkDetails(List<SearchList> searchLists, int position) {
        if (searchLists != null && searchLists.size() > 0 && searchLists.get(position) != null) {

            if (searchLists.get(position).isBookMarked()) {
                searchLists.get(position).setBookMarked(false);
                Constants.bookmarkMap.remove(searchLists.get(position).getImdbID());

            } else {
                searchLists.get(position).setBookMarked(true);
                Constants.bookmarkMap.put(searchLists.get(position).getImdbID(), searchLists.get(position));
            }

            //convert to string using gson
            String hashMapString = gson.toJson(Constants.bookmarkMap);

            MyApplication.sharedPreferences.setStringPreference(Constants.Pref.BOOK_MRK, hashMapString);
        }
    }

    //get bookmarked data into map from preference
    public static List<SearchList> getBookMarkFromPref() {

        String storedHashMapString = MyApplication.sharedPreferences.getStringSharedPreference(Constants.Pref.BOOK_MRK, null);
        java.lang.reflect.Type type = new TypeToken<HashMap<String, SearchList>>() {
        }.getType();


        if (storedHashMapString != null) {
            Constants.bookmarkMap = gson.fromJson(storedHashMapString, type);
            return new ArrayList<SearchList>(Constants.bookmarkMap.values());
        }

        return null;
    }

    //check previously bookmarked details and update the list
    public static void checkForBookMarkedDetails(List<SearchList> searchLists) {
        if (Constants.bookmarkMap != null && !Constants.bookmarkMap.isEmpty()) {
            for (int i = 0; i < searchLists.size(); i++) {
                if (Constants.bookmarkMap.containsKey(searchLists.get(i).getImdbID())) {
                    searchLists.get(i).setBookMarked(true);
                }else {
                    searchLists.get(i).setBookMarked(false);
                }
            }
        }else {
            for (SearchList searchList: searchLists) {
                searchList.setBookMarked(false);
            }
        }
    }
}

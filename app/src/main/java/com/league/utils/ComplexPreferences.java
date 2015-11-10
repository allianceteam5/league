package com.league.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ComplexPreferences {

    private static ComplexPreferences complexPreferences;
    private Context context;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static ObjectMapper mapper = new ObjectMapper();
    public static final String LOGIN = "login";
    public static final String TOKEN = "token";
    public static final String USERNAME = "username";
    public static final String USER = "user";
    public static final String USERID = "userid";

    private ComplexPreferences(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(
                Constants.SharePrefrencesName, context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static ComplexPreferences getComplexPreferences(Context context) {
        if (complexPreferences == null) {
            complexPreferences = new ComplexPreferences(context);
        }
        return complexPreferences;
    }

    public static void setSkipGuidAcitivity(Context context) {
        getComplexPreferences(context);
        editor.putBoolean(Constants.SkipGuideAcitivity, true);
        editor.commit();
    }

    public static Boolean getIsSkipGuidActivity(Context context) {
        getComplexPreferences(context);
        return preferences.getBoolean(Constants.SkipGuideAcitivity, false);
    }

    public static void setLoginSuccess() {
        editor.putBoolean(LOGIN, true);
        editor.commit();
    }

    public static Boolean getIsLoginSuccess() {
        return preferences.getBoolean(LOGIN, false);
    }

    public static void setToken(String token) {
        editor.putString(TOKEN, token);
        editor.commit();
    }

    public static String getToken() {
        return preferences.getString(TOKEN, "");
    }

    public static void setUsername(String username) {
        editor.putString(USERNAME, username);
        editor.commit();
    }

    public static String getUsername() {
        return preferences.getString(USERNAME, "");
    }

    public static void setUserid(String userid) {
        editor.putString(USERID, userid);
        editor.commit();
    }

    public static String getUserid() {
        return preferences.getString(USERID, "");
    }

//    public static void setUser(User user) {
//        setObject(USER, user);
//    }
//
//    public static User getUser() {
//        return getObject(USER, new TypeReference<User>() {
//        });
//    }

    public static void setObject(String key, Object object) {
        if (object == null) {
            throw new IllegalArgumentException("object is null");
        }

        if (key.equals("") || key == null) {
            throw new IllegalArgumentException("key is empty or null");
        }

        try {
            editor.putString(key, mapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void commit() {
        editor.commit();
    }

    public static <T> T getObject(String key, TypeReference valueTypeRef) {

        String json = preferences.getString(key, null);
        Log.i("sharepreferences get" + key, json);
        if (json == null) {
            return null;
        } else {
            try {
                return mapper.readValue(json, valueTypeRef);
            } catch (Exception e) {
                throw new IllegalArgumentException("Object storaged with key "
                        + key + " is instanceof other class");
            }
        }
    }
}

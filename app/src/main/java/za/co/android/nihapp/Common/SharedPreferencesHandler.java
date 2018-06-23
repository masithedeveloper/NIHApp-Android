package za.co.android.nihapp.Common;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;

import java.util.Set;

import za.co.android.nihapp.Model.AuthModelLight;

public class SharedPreferencesHandler {
    private static SharedPreferences sharedPreferences;
    private static final String ACTIVE_USER_MODEL = "ACTIVE_USER_MODEL";

    //----------------------------------------------------------------------------------------------
    private static void InstantiateSharedPreferenceInstance(Context context){
        if(sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences("za.co.android.nihapp", Context.MODE_PRIVATE);
        }
    }

    //Methods to get values from the SharedPreferences object instance
    //----------------------------------------------------------------------------------------------
    public static AuthModelLight getPersonModel(Context context){
        InstantiateSharedPreferenceInstance(context);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(ACTIVE_USER_MODEL, "");
        return gson.fromJson(json, AuthModelLight.class);
    }

    //----------------------------------------------------------------------------------------------
    public static void putPersonModel(Context context, AuthModelLight authModelLight){
        InstantiateSharedPreferenceInstance(context);
        //authModelLight.setJsonObject(null);
        removePersonModel(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(authModelLight);
        editor.putString(ACTIVE_USER_MODEL, json);
        editor.commit();
    }

    //----------------------------------------------------------------------------------------------
    public static void removePersonModel(Context context){
        InstantiateSharedPreferenceInstance(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(ACTIVE_USER_MODEL);
        editor.apply();
    }

    //----------------------------------------------------------------------------------------------
    public static int getInt(Context context, String key){

        InstantiateSharedPreferenceInstance(context);

        return sharedPreferences.getInt(key, 0);
    }
    //----------------------------------------------------------------------------------------------
    public static boolean getBoolean(Context context, String key){

        InstantiateSharedPreferenceInstance(context);

        return sharedPreferences.getBoolean(key, false);
    }
    //----------------------------------------------------------------------------------------------
    public static float getFloat(Context context, String key){

        InstantiateSharedPreferenceInstance(context);

        return sharedPreferences.getFloat(key, 0.0f);
    }
    //----------------------------------------------------------------------------------------------
    public static Long getLong(Context context, String key){

        InstantiateSharedPreferenceInstance(context);

        return sharedPreferences.getLong(key, 0);
    }
    //----------------------------------------------------------------------------------------------
    public static String getString(Context context, String key){

        InstantiateSharedPreferenceInstance(context);

        return sharedPreferences.getString(key, "");
    }

    //----------------------------------------------------------------------------------------------
    //Methods to put values into SharedPreferences
    public static void putInt(Context context, String key, int value){
        InstantiateSharedPreferenceInstance(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    //----------------------------------------------------------------------------------------------
    public static void putString(Context context, String key, String value){
        InstantiateSharedPreferenceInstance(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    //----------------------------------------------------------------------------------------------
    public static void putBoolean(Context context, String key, boolean value){
        InstantiateSharedPreferenceInstance(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    //----------------------------------------------------------------------------------------------
    public static void putFloat(Context context, String key, float value){
        InstantiateSharedPreferenceInstance(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    //----------------------------------------------------------------------------------------------
    public static void putLong(Context context, String key, Long value){
        InstantiateSharedPreferenceInstance(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    //----------------------------------------------------------------------------------------------
    public static void putStringSet(Context context, String key, Set<String> value){
        InstantiateSharedPreferenceInstance(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, value);
        editor.apply();
    }

    //----------------------------------------------------------------------------------------------
    public static void removeElement(Context context, String key){
        InstantiateSharedPreferenceInstance(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    //----------------------------------------------------------------------------------------------
    // Firebase values
    public static String getFirebaseToken(Context context) {
        InstantiateSharedPreferenceInstance(context);
        return sharedPreferences.getString("firebaseToken", "");
    }

    //----------------------------------------------------------------------------------------------
    public static void setFirebaseToken(Context context, String firebaseToken) {
        InstantiateSharedPreferenceInstance(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("firebaseToken", firebaseToken);
        editor.apply();
    }

    //----------------------------------------------------------------------------------------------
    public static void setNewToken(Context context, String newToken) {
        InstantiateSharedPreferenceInstance(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("NEW_TOKEN", newToken);
        editor.apply();
    }

    //----------------------------------------------------------------------------------------------
    public static String getNewToken(Context context) {
        InstantiateSharedPreferenceInstance(context);
        return sharedPreferences.getString("NEW_TOKEN", "");
    }
    //----------------------------------------------------------------------------------------------
}

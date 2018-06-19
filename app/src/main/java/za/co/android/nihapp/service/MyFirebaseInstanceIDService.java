package za.co.android.nihapp.service;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceIdService;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import za.co.android.nihapp.Model.DeviceModel;
import za.co.android.nihapp.app.Config;

import static za.co.android.nihapp.app.Config.DEVICE_URL;

/**
 * Created by Masi on 14-Jan-18.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    //----------------------------------------------------------------------------------------------
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = "";//FirebaseInstanceId.getInstance().getToken();

        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);

        // sending reg id to your server
        sendRegistrationToServer(refreshedToken);
        //Toast.makeText(getApplicationContext(),refreshedToken,Toast.LENGTH_SHORT).show();
        // Notify UI that registration has completed, so the progress indicator can be hidden.
        //Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        //registrationComplete.putExtra("token", refreshedToken);
        //LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    //----------------------------------------------------------------------------------------------
    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        SharedPreferences sp=getSharedPreferences("myPref", Context.MODE_PRIVATE);
        String PersonId=sp.getString("PersonId", "");
        //"PersonId", response.get("PersonId").toString()

        DeviceModel deviceModel = new DeviceModel();
        deviceModel.setDeviceDescription(Build.MODEL); // device name
        deviceModel.setPersonId(Long.parseLong(PersonId));
        deviceModel.setDeviceCode(token);
        Gson gson = new Gson();
        String _json = gson.toJson(deviceModel);
        JSONObject json = null;
        try {
            json = new JSONObject(_json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, DEVICE_URL, json, new Response.Listener<JSONObject>() {
            //------------------------------------------------------------------------------
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("objectId") != null) {

                    }else{
                        // redo the thing until we persist this in the server
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //------------------------------------------------------------------------------
        },new Response.ErrorListener() {
            //----------------------------------------------------------------------------------
            @Override
            public void onErrorResponse(VolleyError error) {
            }
            //----------------------------------------------------------------------------------
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }

    //----------------------------------------------------------------------------------------------
    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.commit();
    }
    //----------------------------------------------------------------------------------------------
}

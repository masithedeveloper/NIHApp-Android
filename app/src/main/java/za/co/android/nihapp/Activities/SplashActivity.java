package za.co.android.nihapp.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import za.co.android.nihapp.Common.Config;
import za.co.android.nihapp.Interfaces.IParentSpinner;
import za.co.android.nihapp.Model.AuthModelLight;
import za.co.android.nihapp.Model.PersonModel;
import za.co.android.nihapp.R;
import za.co.android.nihapp.Common.NIHApplication;
import za.co.android.nihapp.Common.SharedPreferencesHandler;

public class SplashActivity extends AppCompatActivity {

    ArrayList<IParentSpinner> drivers = new ArrayList<>();
    int retries = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(getSupportActionBar() != null)
            getSupportActionBar().hide();

        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= 16){
            // Hide the status bar
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        NIHApplication nihApplication = new NIHApplication();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AuthModelLight authModelLight = SharedPreferencesHandler.getPersonModel(SplashActivity.this); // check for active session
                if(authModelLight != null && authModelLight.getSessionKey().length() > 0) {
                    if(authModelLight.isPersonType()) // parent
                        startActivity(new Intent(SplashActivity.this, BillSummaryActivity.class)); // offline login
                    else // driver
                        startActivity(new Intent(SplashActivity.this, EventActivity.class)); // offline login
                    finish();
                }
                else
                    getDrivers(); // the response of this will navigate to the Login Activity
            }
        }, 2000); // 2 seconds
    }

    //----------------------------------------------------------------------------------------------
    private void getDrivers(){
        JsonArrayRequest request = new JsonArrayRequest(Config.GET_DRIVERS_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) { // is this running on the UIThread?
                        if(response.length() > 0) {
                            TypeToken typeToken = new TypeToken<ArrayList<PersonModel>>(){};
                            PersonModel personModel = new PersonModel();
                            personModel.setPerFirstname("Select driver... *");
                            personModel.setPerLastname("");
                            drivers.clear();
                            drivers.add(0, personModel);
                            drivers.addAll((Collection<? extends IParentSpinner>) new Gson().fromJson(response.toString(), typeToken.getType()));
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class).putExtra("Drivers", drivers)); // online login
                            finish(); // finish splash
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { // retry 2 times
                VolleyLog.d("Error", "Error: " + error.getMessage());
                Log.d("Error", "Error: " + error.getMessage());
                if (retries != 3) { // allow 2 retries
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext(), R.style.MyAlertDialogStyle);
                    builder.setMessage(error.getMessage())
                            .setTitle("Network error")
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    retries++;
                                    getDrivers();
                                }
                            })
                            .setNegativeButton("Exit app", null); // close the app

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    Toast.makeText(getApplicationContext(), "Could not get drivers", Toast.LENGTH_SHORT).show();
                }
            }
        })
        {
            @Override
            public Map<String,String> getHeaders(){
                HashMap<String,String> headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("XClientId", Settings.Secure.getString(getApplication().getContentResolver(), Settings.Secure.ANDROID_ID));
                //headers.put("PersonId", valueOf(PersonId));
                //headers.put("XSessionId", valueOf(""));
                return headers;
            }
        };
        // Adding request to request queue
        NIHApplication.getRequestQueueInstance().add(request);
    }
    //-------------------------------------------------------------------------------------------------------------
}

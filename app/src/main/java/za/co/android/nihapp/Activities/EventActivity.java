package za.co.android.nihapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import za.co.android.nihapp.Model.EventModel;
import za.co.android.nihapp.Model.PersonModel;
import za.co.android.nihapp.R;

import static android.widget.Toast.makeText;
import static java.lang.String.valueOf;
import static za.co.android.nihapp.app.Config.EVENT_URL;

public class EventActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ProgressBar mProgressBar;
    EventModel eventModel = new EventModel();
    Spinner list_of_parents_spinner;
    RadioButton pick_up_radio;
    RadioButton drop_off_radio;
    Switch from_home_switch;
    Button send_event_button;
    Long PersonId = 0L;
    String SessionKey;
    ArrayList<PersonModel> parents = null;
    ArrayAdapter<PersonModel> personModelArrayAdapter;

    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        SharedPreferences sp = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        PersonId = sp.getLong("PersonId", 0);
        SessionKey = sp.getString("SessionKey", "");
        mProgressBar = findViewById(R.id.progressBar1);
        list_of_parents_spinner = findViewById(R.id.list_of_parents_spinner);
        pick_up_radio = findViewById(R.id.pick_up_radio);
        drop_off_radio = findViewById(R.id.drop_off_radio);
        from_home_switch = findViewById(R.id.from_home_switch);
        send_event_button = findViewById(R.id.send_event_button);
        getParents();
        // populate eventModel
        send_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindUIDataToModel();
                if(eventModel.validate())
                    sendEvent();
                else
                    Toast.makeText(getApplicationContext(), "Fill in all required fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //----------------------------------------------------------------------------------------------
    void bindUIDataToModel(){
        eventModel.setEvtParentId(4);
        eventModel.setEvtDrivertId(PersonId);
        eventModel.setEvtPickUpTime("018-06-11T22:39:26");
        eventModel.setEvtDropOffTime("018-06-11T22:39:26");
        eventModel.setEvtLongitude("73.564097");
        eventModel.setEvtLatitude("73.564097");
    }
    //----------------------------------------------------------------------------------------------
    private void getParents(){
        JsonArrayRequest request = new JsonArrayRequest("http://10.0.92.134:8089/api/person?driverId=6",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response", response.toString());
                        Type listType = new TypeToken<ArrayList<PersonModel>>(){}.getType();
                        parents = new Gson().fromJson(response.toString(), listType);
                        if(parents.size() > 0)
                            configureParentsSpinner(parents);
                        //else
                            // show some dialog and do not proceed

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        /*{

            @Override
            public Map<String,String> getHeaders(){
                HashMap<String,String> headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("XClientId", Settings.Secure.getString(getApplication().getContentResolver(), Settings.Secure.ANDROID_ID));
                headers.put("PersonId", valueOf(PersonId));
                headers.put("XSessionId", valueOf(SessionKey));
                headers.put("F748AE85-9AC5-46A9-B1F7-E76390BB3A85", "masi");
                headers.put("xerxes=1", "masi");
                return headers;
            }
        }*/;

        // Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

    private void configureParentsSpinner(ArrayList<PersonModel> parents) {
        personModelArrayAdapter = new ArrayAdapter<>(this,  R.layout.support_simple_spinner_dropdown_item, parents);
        list_of_parents_spinner.setAdapter(personModelArrayAdapter);
        list_of_parents_spinner.setOnItemSelectedListener(this);
    }
    //----------------------------------------------------------------------------------------------
    private void sendEvent() {
        Gson gson = new Gson();
        String _json = gson.toJson(eventModel);
        JSONObject json = null;
        try {
            json = new JSONObject(_json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, EVENT_URL, json, new Response.Listener<JSONObject>() {
            //------------------------------------------------------------------------------
            @Override
            public void onResponse(JSONObject response) {
                mProgressBar.setVisibility(View.GONE);
                try {
                    if(response.getString("EvtID") != null &&  response.getInt("EvtID") != 0) {
                        makeText(EventActivity.this, "Event send", Toast.LENGTH_SHORT).show();
                        //
                        eventModel = new EventModel();
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext(),R.style.MyAlertDialogStyle);
                        builder.setMessage(response.get("Desc").toString()) //temp
                                .setTitle("Error!")
                                .setPositiveButton(android.R.string.ok, null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
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
                mProgressBar.setVisibility(View.GONE);
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext(),R.style.MyAlertDialogStyle);
                builder.setMessage(error.toString()) //temp
                        .setTitle("Error!")
                        .setPositiveButton(android.R.string.ok, null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            //----------------------------------------------------------------------------------
        })

        {
            /** Passing some request headers* */
            @Override
            public Map<String,String> getHeaders(){
                HashMap<String,String> headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("XClientId", Settings.Secure.getString(getApplication().getContentResolver(), Settings.Secure.ANDROID_ID));
                headers.put("PersonId", valueOf(PersonId));
                headers.put("XSessionId", valueOf(SessionKey));
                headers.put("F748AE85-9AC5-46A9-B1F7-E76390BB3A85", "masi");
                headers.put("xerxes=1", "masi");
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //if(view.getId() == R.id.text){
        eventModel.setEvtParentId(((PersonModel)parent.getSelectedItem()).getPerId());
        //}
        // else if view id is event type
        Toast.makeText(this, ((PersonModel)parent.getSelectedItem()).getPerFirstname(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    //----------------------------------------------------------------------------------------------
}

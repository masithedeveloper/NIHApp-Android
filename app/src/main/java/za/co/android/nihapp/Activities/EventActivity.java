package za.co.android.nihapp.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import za.co.android.nihapp.Adapters.PersonSpinnerAdapter;
import za.co.android.nihapp.Interfaces.IParentSpinner;
import za.co.android.nihapp.Model.AuthModelLight;
import za.co.android.nihapp.Model.EventModel;
import za.co.android.nihapp.Model.PersonModel;
import za.co.android.nihapp.R;
import za.co.android.nihapp.Common.Config;
import za.co.android.nihapp.Common.NIHApplication;
import za.co.android.nihapp.Common.SharedPreferencesHandler;

import static android.widget.Toast.makeText;
import static java.lang.String.valueOf;
import static za.co.android.nihapp.Common.Config.EVENT_URL;

public class EventActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ProgressBar mProgressBar;
    EventModel eventModel = new EventModel();
    Spinner list_of_parents_spinner;
    RadioButton selectedEventType;
    RadioGroup event_type_group;
    Switch from_home_switch;
    Button send_event_button;
    Long PersonId = 0L;
    String SessionKey;
    ArrayList<IParentSpinner> parents = new ArrayList<>();
    private boolean isClicked = false;
    IParentSpinner selectedParent = new PersonModel();
    private DrawerLayout mDrawerLayout;

    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        switch (menuItem.getItemId()){
                            case R.id.logout:
                                SharedPreferencesHandler.removePersonModel(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "Logged out", Toast.LENGTH_LONG).show();
                                // try navigating to login tab
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                        }
                        return true;
                    }
                });
        AuthModelLight loggedInPersonModel = SharedPreferencesHandler.getPersonModel(this);
        PersonId = loggedInPersonModel.getPersonId();
        //SessionKey = loggedInPersonModel.getSessionKey();
        mProgressBar = findViewById(R.id.progressBar1);
        list_of_parents_spinner = findViewById(R.id.list_of_parents_spinner);
        event_type_group = findViewById(R.id.event_type_group);
        from_home_switch = findViewById(R.id.from_home_switch);
        send_event_button = findViewById(R.id.send_event_button);
        getParents();
        // populate eventModel
        send_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedEventType = findViewById(event_type_group.getCheckedRadioButtonId());
                if(bindUIDataToModel() && eventModel.validate())
                    sendEvent();
                else
                    Toast.makeText(getApplicationContext(), "Fill in all required fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //----------------------------------------------------------------------------------------------
    private boolean bindUIDataToModel(){ // called on send event
        boolean isValid = false;
        eventModel.setEvtDriverId(PersonId);
        if(selectedEventType != null) {
            isValid = true;
            if (selectedEventType.getId() == R.id.pick_up_radio)
                eventModel.setEvtType("PickUp");
            else
                eventModel.setEvtType("DropOff");
        }
        eventModel.setEvtTripFromHome(from_home_switch.isChecked());
        eventModel.setEvtLongitude("73.564097"); // can be null
        eventModel.setEvtLatitude("73.564097"); // can be null
        return isValid;
    }

    //----------------------------------------------------------------------------------------------
    private void getParents(){
        JsonArrayRequest request = new JsonArrayRequest(Config.GET_PARENTS_URL + PersonId,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response.length() > 0) {
                            TypeToken typeToken = new TypeToken<ArrayList<PersonModel>>(){};
                            PersonModel personModel = new PersonModel();
                            personModel.setPerFirstname("Select child's parent... *");
                            personModel.setPerLastname("");
                            parents.clear();
                            parents.add(0, personModel);
                            parents.addAll((Collection<? extends IParentSpinner>) new Gson().fromJson(response.toString(), typeToken.getType()));
                            configureParentsSpinner();
                        }
                        else
                            list_of_parents_spinner.setEnabled(false); // maybe show text message next to the view
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Could not get parents", Toast.LENGTH_SHORT).show();
            } //0637361513
        })
        {
            @Override
            public Map<String,String> getHeaders(){
                HashMap<String,String> headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("XClientId", Settings.Secure.getString(getApplication().getContentResolver(), Settings.Secure.ANDROID_ID));
                headers.put("PersonId", valueOf(PersonId));
                //headers.put("XSessionId", valueOf(SessionKey));
                return headers;
            }
        };
        // Adding request to request queue
        NIHApplication.getRequestQueueInstance().add(request);
    }

    //----------------------------------------------------------------------------------------------
    private void configureParentsSpinner() {
        final PersonSpinnerAdapter personModelArrayAdapter = new PersonSpinnerAdapter(this, R.layout.layout_spinner, parents){
            @Override
            public boolean isEnabled(int position){
                if(position == 0){
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else{
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.parseColor("#000000"));
                }
                return view;
            }
        };
        if(list_of_parents_spinner != null){
            list_of_parents_spinner.setAdapter(personModelArrayAdapter);
        }
        list_of_parents_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                IParentSpinner current = (IParentSpinner) parent.getItemAtPosition(position);
                if(current != null){
                    selectedParent = current;
                    eventModel.setEvtParentId(current.GetID());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //----------------------------------------------------------------------------------------------
    private void sendEvent() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, EVENT_URL, new JSONObject(new Gson().toJson(eventModel)), new Response.Listener<JSONObject>() {
                //------------------------------------------------------------------------------
                @Override
                public void onResponse(JSONObject response) {
                    mProgressBar.setVisibility(View.GONE);
                    try {
                        eventModel = new Gson().fromJson(response.toString(), EventModel.class);
                        if(eventModel != null && eventModel.getEvtID() != 0) {
                            String eventMessage = "A " + eventModel.getEvtType().toLowerCase() + " notification has been sent"; // to " + selectedParent.GetDisplay();
                            makeText(EventActivity.this, eventMessage, Toast.LENGTH_SHORT).show();
                            eventModel = new EventModel();  // reset thing
                            startActivity(getIntent());
                            finish();
                        }
                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext(),R.style.MyAlertDialogStyle);
                            builder.setMessage("Tsek your shit") //temp
                                    .setTitle("Error!")
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //----------------------------------------------------------------------------------
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
            }){
                @Override
                public Map<String,String> getHeaders(){ // THINKING OF STORING THIS ON SHAREDPREF AND JUST RETRIEVE IT EACH TIME
                    HashMap<String,String> headers = new HashMap();
                    headers.put("Content-Type", "application/json");
                    headers.put("XClientId", Settings.Secure.getString(getApplication().getContentResolver(), Settings.Secure.ANDROID_ID));
                    headers.put("PersonId", valueOf(PersonId));
                    //headers.put("XSessionId", valueOf(SessionKey));
                    return headers;
                }
            };

            NIHApplication.getRequestQueueInstance().add(jsonObjectRequest);
            mProgressBar.setVisibility(View.VISIBLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(isClicked) {
            eventModel.setEvtParentId(((PersonModel) parent.getSelectedItem()).getPerId());
            Toast.makeText(this, ((PersonModel) parent.getSelectedItem()).getPerFirstname(), Toast.LENGTH_SHORT).show();
        }
        isClicked = true;
    }

    //----------------------------------------------------------------------------------------------
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    //----------------------------------------------------------------------------------------------
}

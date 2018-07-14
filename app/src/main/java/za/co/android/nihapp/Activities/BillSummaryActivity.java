package za.co.android.nihapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import za.co.android.nihapp.Common.NIHApplication;
import za.co.android.nihapp.Common.SharedPreferencesHandler;
import za.co.android.nihapp.Model.AuthModelLight;
import za.co.android.nihapp.Model.BillSummaryModel;
import za.co.android.nihapp.R;
import za.co.android.nihapp.Utils.Utils;

import static java.lang.String.valueOf;
import static za.co.android.nihapp.Common.Config.GET_PARENT_BILL_URL;

public class BillSummaryActivity extends AppCompatActivity{

    ProgressBar mProgressBar;
    BillSummaryModel billSummaryModel = new BillSummaryModel();
    Long PersonId = 0L;
    String SessionKey;
    TextView number_of_trips, rate_per_trip, bill_amount;
    Button view_messages_button;
    private DrawerLayout mDrawerLayout;

    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_summary);
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
        SessionKey = loggedInPersonModel.getSessionKey();
        mProgressBar = findViewById(R.id.progressBar1);
        number_of_trips = findViewById(R.id.number_of_trips_value);
        rate_per_trip = findViewById(R.id.rate_per_trips_value);
        bill_amount = findViewById(R.id.bill_amount_value);
        view_messages_button = findViewById(R.id.view_messages_button);
        view_messages_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BillSummaryActivity.this, "View Messages Button", Toast.LENGTH_SHORT).show();
            }
        });
        getBillSummary();
        // populate billSummaryActivity
    }

    //----------------------------------------------------------------------------------------------
    private void getBillSummary(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, GET_PARENT_BILL_URL + PersonId, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response != null) {
                            billSummaryModel = new Gson().fromJson(response.toString(), BillSummaryModel.class);
                            if(billSummaryModel != null)
                                displaySummary();
                            else
                                Toast.makeText(getApplicationContext(), "Shit happened", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Shit happened", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Could not get bill", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String,String> getHeaders(){
                HashMap<String,String> headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("XClientId", Settings.Secure.getString(getApplication().getContentResolver(), Settings.Secure.ANDROID_ID));
                headers.put("PersonId", valueOf(PersonId));
                headers.put("XSessionId", valueOf(SessionKey));
                return headers;
            }
        };
        // Adding request to request queue
        NIHApplication.getRequestQueueInstance().add(request);
    }

    //----------------------------------------------------------------------------------------------
    private void displaySummary() {
        number_of_trips.setText(String.valueOf(billSummaryModel.getNumberOfTrips()));
        rate_per_trip.setText(Utils.formatMoney(String.valueOf(billSummaryModel.getRatePerTrip())));
        bill_amount.setText(Utils.formatMoney(String.valueOf(billSummaryModel.getTotalCost())));
    }

    //----------------------------------------------------------------------------------------------
}

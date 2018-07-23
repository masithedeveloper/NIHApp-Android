package za.co.android.nihapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
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

import za.co.android.nihapp.Activities.LoginActivity;
import za.co.android.nihapp.Adapters.PersonSpinnerAdapter;
import za.co.android.nihapp.Common.Config;
import za.co.android.nihapp.Interfaces.IParentSpinner;
import za.co.android.nihapp.Model.PersonModel;
import za.co.android.nihapp.Model.PersonRegisterModel;
import za.co.android.nihapp.R;
import za.co.android.nihapp.Common.NIHApplication;

import static android.text.TextUtils.isEmpty;
import static java.lang.String.valueOf;
import static za.co.android.nihapp.Activities.LoginActivity.getDrivers;
import static za.co.android.nihapp.Common.Config.REGISTER_URL;


public class SignupFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    EditText firstName;
    EditText lastName;
    EditText cellPhone;
    EditText email;
    EditText password;
    Spinner list_of_drivers_spinner;
    Button signBtn;
    RadioGroup radioGroup;
    RadioButton selectBtn;
    ProgressBar mProgressBar;
    String FirstName, LastName, CellPhone, EmailAddress, Password;
    PersonRegisterModel personRegisterModel = new PersonRegisterModel();
    Context con;
    private boolean isClicked = false;
    IParentSpinner selectedDriver = new PersonModel();

    //----------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        con = getActivity();
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    //----------------------------------------------------------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        firstName = getView().findViewById(R.id.firstName);
        lastName = getView().findViewById(R.id.lastName);
        cellPhone = getView().findViewById(R.id.cellPhone);
        email = getView().findViewById(R.id.email);
        password = getView().findViewById(R.id.password);
        radioGroup = getView().findViewById(R.id.rGroup);
        list_of_drivers_spinner = getView().findViewById(R.id.list_of_drivers_spinner);
        signBtn = getView().findViewById(R.id.signUpBtn);
        // This overrides the radiogroup onCheckListener
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            public void onCheckedChanged(RadioGroup group, int checkedId){
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked){
                    // Changes the textview's text to "Checked: example radiobutton text"
                    if(checkedRadioButton.getId() == R.id.parent_radio_button) {
                        list_of_drivers_spinner.setVisibility(View.VISIBLE);
                        getView().findViewById(R.id.sign_in_form).post(new Runnable() {
                            @Override
                            public void run() {
                                ((ScrollView)getView().findViewById(R.id.sign_in_form)).fullScroll(View.FOCUS_DOWN);
                            }
                        });
                    }
                    else
                        list_of_drivers_spinner.setVisibility(View.GONE);
                }
            }
        });

        mProgressBar = getView().findViewById(R.id.progressBar1);
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirstName = firstName.getText().toString().trim();
                LastName = lastName.getText().toString().trim();
                CellPhone = cellPhone.getText().toString().trim();
                EmailAddress = email.getText().toString().trim();
                Password = password.getText().toString().trim();
                selectBtn = getView().findViewById(radioGroup.getCheckedRadioButtonId());

                if(isEmpty(FirstName))
                    firstName.setError(getString(R.string.error_field_required));
                if(isEmpty(LastName))
                    lastName.setError(getString(R.string.error_field_required));
                if(isEmpty(CellPhone))
                    cellPhone.setError(getString(R.string.error_field_required));
                else if(isEmpty(EmailAddress))
                    email.setError(getString(R.string.error_field_required));
                else if(isEmpty(Password))
                    password.setError(getString(R.string.error_field_required));
                else {
                    bindUIDataToModel();
                    if(personRegisterModel.validate())
                        register();
                    else
                        Toast.makeText(getContext(), "Fill in all required fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        configureDriversSpinner();
    }

    //----------------------------------------------------------------------------------------------
    private void configureDriversSpinner() {
        final PersonSpinnerAdapter personModelArrayAdapter = new PersonSpinnerAdapter(getActivity(), R.layout.layout_spinner, getDrivers()){
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
        if(list_of_drivers_spinner != null && getDrivers() != null){
            list_of_drivers_spinner.setAdapter(personModelArrayAdapter);
        }
        list_of_drivers_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                IParentSpinner current = (IParentSpinner) parent.getItemAtPosition(position);
                if(current != null){
                    selectedDriver = current;
                    personRegisterModel.setPerTransportId(current.GetID());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //----------------------------------------------------------------------------------------------
    void bindUIDataToModel(){ // with dummy data
        personRegisterModel.setPerFirstname(FirstName);
        personRegisterModel.setPerLastname(LastName);
        personRegisterModel.setPerCellPhone(CellPhone);
        personRegisterModel.setEmailAddress(EmailAddress);
        personRegisterModel.setPassword(Password);
        personRegisterModel.setDeviceCode("firebaseToken");
        personRegisterModel.setDeviceDescription(Build.MODEL);
        personRegisterModel.setOS("Android");
        if(selectBtn.getId() == R.id.parent_radio_button)
            personRegisterModel.setPerType(true);
        else
            personRegisterModel.setPerType(false);
    }
    //----------------------------------------------------------------------------------------------
    private void register() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.PUT, REGISTER_URL, new JSONObject(new Gson().toJson(personRegisterModel)), new Response.Listener<JSONObject>() {
                        //------------------------------------------------------------------------------
                        @Override
                        public void onResponse(JSONObject response) {
                            mProgressBar.setVisibility(View.GONE);
                            try {
                                if(response != null) {
                                    if (response.getInt("PersonId") != 0) {
                                        Toast.makeText(getActivity(), "Registered Successful", Toast.LENGTH_LONG).show();
                                        // try navigating to login tab
                                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(getActivity().getIntent());
                                        getActivity().finish();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
                                        builder.setMessage(response.get("Desc").toString()) //temp
                                                .setTitle("Error!")
                                                .setPositiveButton(android.R.string.ok, null);
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        //------------------------------------------------------------------------------
                    }, new Response.ErrorListener() {
                        //------------------------------------------------------------------------------
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            mProgressBar.setVisibility(View.GONE);
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.MyAlertDialogStyle);
                            builder.setMessage(error.toString()) //temp
                                    .setTitle("Error!")
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        //------------------------------------------------------------------------------
                    });

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
            personRegisterModel.setPerTransportId(((PersonModel) parent.getSelectedItem()).getPerId());
        }
        isClicked = true;
    }

    //----------------------------------------------------------------------------------------------
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    //----------------------------------------------------------------------------------------------
}

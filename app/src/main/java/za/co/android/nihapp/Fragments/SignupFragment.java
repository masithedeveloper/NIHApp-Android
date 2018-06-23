package za.co.android.nihapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import za.co.android.nihapp.Activities.LoginActivity;
import za.co.android.nihapp.Model.PersonRegisterModel;
import za.co.android.nihapp.R;
import za.co.android.nihapp.Common.NIHApplication;

import static android.text.TextUtils.isEmpty;
import static za.co.android.nihapp.Common.Config.REGISTER_URL;


public class SignupFragment extends Fragment {

    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;
    Button signBtn;
    RadioGroup radioGroup;
    RadioButton selectBtn;
    ProgressBar mProgressBar;
    String FirstName, LastName, EmailAddress, Password;
    PersonRegisterModel personRegisterModel = new PersonRegisterModel();
    Context con;

    //----------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        con=getActivity();
        return  inflater.inflate(R.layout.fragment_signup, container, false);
    }

    //----------------------------------------------------------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        firstName = getView().findViewById(R.id.firstName);
        lastName = getView().findViewById(R.id.lastName);
        email = getView().findViewById(R.id.email);
        password = getView().findViewById(R.id.password);
        radioGroup = getView().findViewById(R.id.rGroup);
        signBtn = getView().findViewById(R.id.signUpBtn);
        mProgressBar = getView().findViewById(R.id.progressBar2);
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirstName = firstName.getText().toString().trim();
                LastName = lastName.getText().toString().trim();
                EmailAddress = email.getText().toString().trim();
                Password = password.getText().toString().trim();
                selectBtn = getView().findViewById(radioGroup.getCheckedRadioButtonId());

                if(isEmpty(FirstName))
                    firstName.setError(getString(R.string.error_field_required));
                if(isEmpty(LastName))
                    lastName.setError(getString(R.string.error_field_required));
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
    }

    //----------------------------------------------------------------------------------------------
    void bindUIDataToModel(){ // with dummy data
        personRegisterModel.setPerFirstname(FirstName);
        personRegisterModel.setPerLastname(LastName);
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
}

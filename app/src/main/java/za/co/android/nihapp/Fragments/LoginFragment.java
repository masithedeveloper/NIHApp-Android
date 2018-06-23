package za.co.android.nihapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import za.co.android.nihapp.Activities.BillSummaryActivity;
import za.co.android.nihapp.Activities.EventActivity;
import za.co.android.nihapp.Activities.SplashActivity;
import za.co.android.nihapp.Model.AuthModelLight;
import za.co.android.nihapp.R;
import za.co.android.nihapp.Common.NIHApplication;
import za.co.android.nihapp.Common.SharedPreferencesHandler;

import static android.text.TextUtils.isEmpty;
import static za.co.android.nihapp.Common.Config.LOGIN_URL;

public class LoginFragment extends Fragment {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private ProgressBar mProgressBar;
    String username,password;
    private AuthModelLight authModelLight = null;

    //----------------------------------------------------------------------------------------------
    public LoginFragment() {
        // Required empty public constructor
    }
    //----------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    //----------------------------------------------------------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        mEmailView = getView().findViewById(R.id.email);
        mPasswordView = getView().findViewById(R.id.password);
        mProgressBar = getView().findViewById(R.id.progressBar1);

        Button mEmailSignInButton = getView().findViewById(R.id.email_sign_in_button);

        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = mEmailView.getText().toString();
                password = mPasswordView.getText().toString();
                mEmailView.setError(null);
                mPasswordView.setError(null);

                boolean cancel = false;
                View focusView = null;

                if (isEmpty(password)) {
                    mPasswordView.setError(getString(R.string.error_invalid_password));
                    focusView = mPasswordView;
                    cancel = true;
                }

                if (isEmpty(username)) {
                    mEmailView.setError(getString(R.string.error_field_required));
                    focusView = mEmailView;
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                } else {
                    authModelLight = new AuthModelLight();
                    authModelLight.setEmailAddress(username);
                    authModelLight.setPassword(password);
                    userLogin();
                }
            }
        });
    }

    //----------------------------------------------------------------------------------------------
    private void userLogin() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    LOGIN_URL,
                    new JSONObject(new Gson().toJson(authModelLight)),
                    new Response.Listener<JSONObject>() {
                //------------------------------------------------------------------------------
                @Override
                public void onResponse(JSONObject response) {
                    mProgressBar.setVisibility(View.GONE);
                    try {
                        authModelLight = new Gson().fromJson(response.toString(), AuthModelLight.class);
                        if(authModelLight != null && authModelLight.getPersonId() != 0 && authModelLight.getSessionKey() != null){
                            SharedPreferencesHandler.putPersonModel(getContext(), authModelLight);
                            Toast.makeText(getActivity(), authModelLight.getDesc(),Toast.LENGTH_SHORT).show();
                            // Navigate to the main screen
                            Intent intent = null;
                            if(authModelLight.isPersonType()) // parent
                                intent =  new Intent(getContext(), BillSummaryActivity.class); // offline login
                            else // driver
                                intent = new Intent(getContext(), EventActivity.class); // offline login
                            authModelLight = null;
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            getActivity().finish();
                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.MyAlertDialogStyle);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.MyAlertDialogStyle);
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
                    headers.put("XClientId", Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID)); //
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
}


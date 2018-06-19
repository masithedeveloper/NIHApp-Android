package za.co.android.nihapp.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import za.co.android.nihapp.Activities.EventActivity;
import za.co.android.nihapp.R;

import static android.content.Context.MODE_PRIVATE;
import static android.text.TextUtils.isEmpty;
import static za.co.android.nihapp.app.Config.LOGIN_URL;

public class LoginFragment extends Fragment {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private ProgressBar mProgressBar;
    private View mLoginFormView;
    SharedPreferences sharedPreferences;
    String username,password;

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
        sharedPreferences=getActivity().getSharedPreferences("myPref",MODE_PRIVATE);
        mEmailView = getView().findViewById(R.id.email);
        mPasswordView = getView().findViewById(R.id.password);
        mProgressBar = getView().findViewById(R.id.progressBar1);

        String DeviceId = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        SharedPreferences.Editor e = sharedPreferences.edit();
        e.putString("DeviceId", DeviceId);
        e.commit();

        Button mEmailSignInButton = getView().findViewById(R.id.email_sign_in_button);
        if(sharedPreferences.contains("username")) {
            startActivity(new Intent(getActivity(), EventActivity.class)); // landing screen
            getActivity().finish();
        }
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
                    userLogin(username,password);
                }
            }
        });

        mLoginFormView = getView().findViewById(R.id.login_form);
    }

    //----------------------------------------------------------------------------------------------
    private void userLogin(final String EmailAddress,final String Password) {
        JSONObject json = new JSONObject();
        try {
            json.put("EmailAddress",EmailAddress);
            json.put("Password",Password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, LOGIN_URL, json, new Response.Listener<JSONObject>() {
            //------------------------------------------------------------------------------
            @Override
            public void onResponse(JSONObject response) {
                mProgressBar.setVisibility(View.GONE);
                try {
                    if(response.getString("SessionKey") != null &&  response.getInt("PersonId") != 0) {
                        SharedPreferences.Editor e = sharedPreferences.edit();
                        e.putString("EmailAddress", EmailAddress);
                        e.putString("Password", Password);
                        e.putString("SessionKey", response.get("SessionKey").toString()); // things is important
                        e.putLong("PersonId", response.getLong("PersonId"));
                        e.commit();

                        Toast.makeText(getActivity(),"LogIn Successful",Toast.LENGTH_LONG).show();
                        // Navigate to the main screen
                        Intent intent = new Intent(getActivity(), EventActivity.class);
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
            public Map<String,String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("XClientId", Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID)); //
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjectRequest);
        mProgressBar.setVisibility(View.VISIBLE);
    }
    //----------------------------------------------------------------------------------------------
}


package za.co.android.nihapp.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import za.co.android.nihapp.Model.AuthModelLight;
import za.co.android.nihapp.R;
import za.co.android.nihapp.Common.NIHApplication;
import za.co.android.nihapp.Common.SharedPreferencesHandler;

public class SplashActivity extends AppCompatActivity {

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
                }
                else
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class)); // online login
                finish();
            }
        }, 2000); // 2 seconds
    }

    //-------------------------------------------------------------------------------------------------------------
}

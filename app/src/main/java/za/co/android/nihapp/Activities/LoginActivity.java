package za.co.android.nihapp.Activities;

import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

import za.co.android.nihapp.Adapters.loginTabAdapter;
import za.co.android.nihapp.Interfaces.IParentSpinner;
import za.co.android.nihapp.R;

public class LoginActivity extends AppCompatActivity {
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    public static ArrayList<IParentSpinner> drivers = new ArrayList<>();

    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= 16){
            // Hide the status bar
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
        drivers = (ArrayList<IParentSpinner>) getIntent().getSerializableExtra("Drivers");
        mFragmentManager = getSupportFragmentManager();
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0358B2")));
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.activity_main,new loginTabAdapter()).commit();

        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MapView mv = new MapView(getApplicationContext());
                    mv.onCreate(null);
                    mv.onPause();
                    mv.onDestroy();
                }catch (Exception ignored){

                }
            }
        }).start();
        */
    }
    //----------------------------------------------------------------------------------------------

    public static ArrayList<IParentSpinner> getDrivers() {
        return drivers;
    }

    //----------------------------------------------------------------------------------------------
}

package za.co.android.nihapp.Common;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class NIHApplication extends Application {

    private static RequestQueue requestQueue = null;

    //----------------------------------------------------------------------------------------------
    @Override
    public void onCreate() {
        super.onCreate();
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(this);
        }
    }

    //----------------------------------------------------------------------------------------------
    public static RequestQueue getRequestQueueInstance(){
        return requestQueue;
    }
    //----------------------------------------------------------------------------------------------
}

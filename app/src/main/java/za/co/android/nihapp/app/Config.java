package za.co.android.nihapp.app;

/**
 * Created by Masi on 16-Jun-18.
 */

public class Config {
//    public static final String BASE_URL = "http://10.0.92.134:8089/";
    public static final String BASE_URL = "http://192.168.68.1:8089/";
    public static final String REGISTER_URL = BASE_URL + "api/Auth";
    public static final String LOGIN_URL = BASE_URL + "api/Auth";
    public static final String DEVICE_URL = BASE_URL + "api/Device";
    public static final String EVENT_URL = BASE_URL + "api/Event";
    public static final String PARENT_URL = BASE_URL + "api/Person";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String DEST_LOCATION = ""; // school location

    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static final String SHARED_PREF = "ah_firebase";
}

package za.co.android.nihapp.app;

/**
 * Created by Masi on 16-Jun-18.
 */

public class Config {
    public static final String BASE_URL = "http://192.168.68.1:8089/";
    public static final String REGISTER_URL = BASE_URL + "api/Auth"; // juice found here PUT
    public static final String LOGIN_URL = BASE_URL + "Api/Auth"; // juice is here POST
    public static final String DRIVER_URL = "http://smartlbus.esy.es/getDriver.php?busno=";
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

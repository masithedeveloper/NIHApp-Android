package za.co.android.nihapp.Common;

/**
 * Created by Masi on 16-Jun-18.
 */

public class Config {
    //public static final String BASE_URL = "http://10.0.92.134:8089/";
    //public static final String BASE_URL = "http://192.168.68.1:8089/";
    public static final String BASE_URL = "http://192.168.8.104:8089/"; // Phalyst wifi
    public static final String REGISTER_URL = BASE_URL + "api/Auth";
    public static final String LOGIN_URL = BASE_URL + "api/Auth";
    public static final String DEVICE_URL = BASE_URL + "api/Device";
    public static final String EVENT_URL = BASE_URL + "api/Event";
    public static final String GET_PARENTS_URL = BASE_URL + "api/person?driverId=";
    public static final String GET_DRIVERS_URL = BASE_URL + "api/auth";
    public static final String GET_PARENT_BILL_URL = BASE_URL + "api/event?parentId=";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static final String SHARED_PREF = "firebase";
}
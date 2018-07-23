package za.co.android.nihapp.Common;

/**
 * Created by Masi on 16-Jun-18.
 */

public class Config {
    //public static final String BASE_URL = "http://10.0.92.134:8089/"; //work wifi
    //public static final String BASE_URL = "http://10.0.92.27:8089/api/"; // work Ethernet
    //public static final String BASE_URL = "http://192.168.68.1:8089/api/"; // mine at home
    //public static final String BASE_URL = "http://192.168.8.104:8089/"; // Phalyst wifi
    public static final String BASE_URL = "http://www.nihapp.co.za/api/"; // production
    public static final String REGISTER_URL = BASE_URL + "Auth";
    public static final String LOGIN_URL = BASE_URL + "Auth";
    public static final String DEVICE_URL = BASE_URL + "Device";
    public static final String EVENT_URL = BASE_URL + "Event";
    public static final String GET_PARENTS_URL = BASE_URL + "person?driverId=";
    public static final String GET_DRIVERS_URL = BASE_URL + "Auth";
    public static final String GET_PARENT_BILL_URL = BASE_URL + "event?parentId=";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static final String SHARED_PREF = "firebase";
}
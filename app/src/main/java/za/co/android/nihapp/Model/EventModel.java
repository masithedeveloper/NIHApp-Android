package za.co.android.nihapp.Model;

public class EventModel {
    public long EvtID;
    public long EvtParentId;
    public long EvtDrivertId;
    public String EvtPickUpTime;
    public String EvtDropOffTime;
    public boolean EvtTripFromHome;
    public String EvtType;
    public String EvtLongitude;
    public String EvtLatitude;

    public EventModel() {
    }

    public EventModel(long evtID, long evtParentId, long evtDrivertId, String evtPickUpTime, String evtDropOffTime, boolean evtTripFromHome, String evtType, String evtLongitude, String evtLatitude) {
        EvtID = evtID;
        EvtParentId = evtParentId;
        EvtDrivertId = evtDrivertId;
        EvtPickUpTime = evtPickUpTime;
        EvtDropOffTime = evtDropOffTime;
        EvtTripFromHome = evtTripFromHome;
        EvtType = evtType;
        EvtLongitude = evtLongitude;
        EvtLatitude = evtLatitude;
    }

    public long getEvtID() {
        return EvtID;
    }

    public void setEvtID(long evtID) {
        EvtID = evtID;
    }

    public long getEvtParentId() {
        return EvtParentId;
    }

    public void setEvtParentId(long evtParentId) {
        EvtParentId = evtParentId;
    }

    public long getEvtDrivertId() {
        return EvtDrivertId;
    }

    public void setEvtDrivertId(long evtDrivertId) {
        EvtDrivertId = evtDrivertId;
    }

    public String getEvtPickUpTime() {
        return EvtPickUpTime;
    }

    public void setEvtPickUpTime(String evtPickUpTime) {
        EvtPickUpTime = evtPickUpTime;
    }

    public String getEvtDropOffTime() {
        return EvtDropOffTime;
    }

    public void setEvtDropOffTime(String evtDropOffTime) {
        EvtDropOffTime = evtDropOffTime;
    }

    public boolean isEvtTripFromHome() {
        return EvtTripFromHome;
    }

    public void setEvtTripFromHome(boolean evtTripFromHome) {
        EvtTripFromHome = evtTripFromHome;
    }

    public String getEvtType() {
        return EvtType;
    }

    public void setEvtType(String evtType) {
        EvtType = evtType;
    }

    public String getEvtLongitude() {
        return EvtLongitude;
    }

    public void setEvtLongitude(String evtLongitude) {
        EvtLongitude = evtLongitude;
    }

    public String getEvtLatitude() {
        return EvtLatitude;
    }

    public void setEvtLatitude(String evtLatitude) {
        EvtLatitude = evtLatitude;
    }

    public boolean validate(){
        if(EvtParentId == 0)
            return false;
        if(EvtDrivertId == 0)
            return false;
        if(EvtPickUpTime.length() == 0)
            return false;
        if(EvtDropOffTime.length() == 0)
            return false;
        if(EvtLongitude.length() == 0)
            return false;
        if(EvtLatitude.length() == 0)
            return false;

        return true;
    }
}

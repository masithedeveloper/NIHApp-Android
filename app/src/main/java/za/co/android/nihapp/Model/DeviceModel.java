package za.co.android.nihapp.Model;

public class DeviceModel {

    public long ObjectId;
    public String DeviceCode;
    public String DeviceDescription;
    public String OS;
    public long PersonId;
    public String CreateDate;
    public String ModifiedDate;

    public DeviceModel(long objectId, String deviceCode, String deviceDescription, String OS, long personId, String createDate, String modifiedDate) {
        ObjectId = objectId;
        DeviceCode = deviceCode;
        DeviceDescription = deviceDescription;
        this.OS = OS;
        PersonId = personId;
        CreateDate = createDate;
        ModifiedDate = modifiedDate;
    }

    public DeviceModel() {

    }

    public long getObjectId() {
        return ObjectId;
    }

    public void setObjectId(long objectId) {
        ObjectId = objectId;
    }

    public String getDeviceCode() {
        return DeviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        DeviceCode = deviceCode;
    }

    public String getDeviceDescription() {
        return DeviceDescription;
    }

    public void setDeviceDescription(String deviceDescription) {
        DeviceDescription = deviceDescription;
    }

    public String getOS() {
        return OS;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public long getPersonId() {
        return PersonId;
    }

    public void setPersonId(long personId) {
        PersonId = personId;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        ModifiedDate = modifiedDate;
    }
}

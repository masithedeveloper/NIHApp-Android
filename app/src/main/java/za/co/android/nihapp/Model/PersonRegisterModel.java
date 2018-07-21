package za.co.android.nihapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class PersonRegisterModel extends PersonModel implements Parcelable {
    private String Password;
    private String EmailAddress;
    private String DeviceCode;
    private String DeviceDescription;
    private String OS;

    public PersonRegisterModel() {
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
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

    public boolean validate(){
        if(PerFirstname == null && PerFirstname.length() == 0)
            return false;
        if(PerLastname == null && PerLastname.length() == 0)
            return false;
        if(EmailAddress == null && EmailAddress.length() == 0)
            return false;
        if(Password == null && Password.length() == 0)
            return false;
        if(DeviceCode == null && DeviceCode.length() == 0)
            return false;
        if(DeviceDescription == null && DeviceDescription.length() == 0)
            return false;
        if(OS == null && OS.length() == 0)
            return false;
        //if(PerType) // if person type is parent then validate driver selection
        if(PerType && PerTransportId == 0) // if first condition evaluation is false then the compiler does not evaluate the second condition
            return false;
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        
    }
}

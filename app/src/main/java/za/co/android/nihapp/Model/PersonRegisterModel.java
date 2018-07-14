package za.co.android.nihapp.Model;

public class PersonRegisterModel extends PersonModel {
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
        if(PerTransportId == 0)
            return false;
        return true;
    }
}

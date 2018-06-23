package za.co.android.nihapp.Model;

public class AuthModelLight {
    private String Desc;
    private String EmailAddress;
    private String Password;
    private String SessionKey;
    private long PersonId;
    public boolean PersonType;
    public String PersonFullName;

    public AuthModelLight() {
    }

    public AuthModelLight(String desc, String emailAddress, String password, String sessionKey, long personId, boolean personType, String personFullName) {
        Desc = desc;
        EmailAddress = emailAddress;
        Password = password;
        SessionKey = sessionKey;
        PersonId = personId;
        PersonType = personType;
        PersonFullName = personFullName;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getSessionKey() {
        return SessionKey;
    }

    public void setSessionKey(String sessionKey) {
        SessionKey = sessionKey;
    }

    public long getPersonId() {
        return PersonId;
    }

    public void setPersonId(long personId) {
        PersonId = personId;
    }

    public boolean isPersonType() {
        return PersonType;
    }

    public void setPersonType(boolean personType) {
        PersonType = personType;
    }

    public String getPersonFullName() {
        return PersonFullName;
    }

    public void setPersonFullName(String personFullName) {
        PersonFullName = personFullName;
    }

    @Override
    public String toString() {
        return "AuthModelLight{" +
                "Desc='" + Desc + '\'' +
                ", EmailAddress='" + EmailAddress + '\'' +
                ", Password='" + Password + '\'' +
                ", SessionKey='" + SessionKey + '\'' +
                ", PersonId=" + PersonId +
                ", PersonType=" + PersonType +
                ", PersonFullName='" + PersonFullName + '\'' +
                '}';
    }
}

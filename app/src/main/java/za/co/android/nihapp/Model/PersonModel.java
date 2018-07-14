package za.co.android.nihapp.Model;

import za.co.android.nihapp.Interfaces.IParentSpinner;

public class PersonModel implements IParentSpinner {
    public long PerId;
    public String PerFirstname;
    public String PerLastname;
    public String PerFullname;
    public String PerEmail ;
    public String PerDob ;
    public String PerIdNumber ;
    public String PerPassword;
    public String PerHashPassword;
    public boolean PerPasswordReset;
    public boolean PerType;
    public short PerLockCount ;
    public String PerLockedAt;
    public short PerAccessType ;
    public short PerVerifyCode;
    public String PerEmailVerified;
    public long PerTransportId ;
    public String PerCellPhone ;

    public PersonModel() {
    }

    public PersonModel(long perId, String perFirstname, String perLastname, String perFullname, String perEmail, String perDob, String perIdNumber, String perPassword, String perHashPassword, boolean perPasswordReset, short perLockCount, String perLockedAt, short perAccessType, short perVerifyCode, String perEmailVerified, int perTransportId, String perCellPhone) {
        PerId = perId;
        PerFirstname = perFirstname;
        PerLastname = perLastname;
        PerFullname = perFullname;
        PerEmail = perEmail;
        PerDob = perDob;
        PerIdNumber = perIdNumber;
        PerPassword = perPassword;
        PerHashPassword = perHashPassword;
        PerPasswordReset = perPasswordReset;
        PerLockCount = perLockCount;
        PerLockedAt = perLockedAt;
        PerAccessType = perAccessType;
        PerVerifyCode = perVerifyCode;
        PerEmailVerified = perEmailVerified;
        PerTransportId = perTransportId;
        PerCellPhone = perCellPhone;
    }

    public long getPerId() {
        return PerId;
    }

    public void setPerId(long perId) {
        PerId = perId;
    }

    public String getPerFirstname() {
        return PerFirstname;
    }

    public void setPerFirstname(String perFirstname) {
        PerFirstname = perFirstname;
    }

    public String getPerLastname() {
        return PerLastname;
    }

    public void setPerLastname(String perLastname) {
        PerLastname = perLastname;
    }

    public String getPerFullname() {
        return PerFirstname + " " +  PerLastname ;
    }

    public void setPerFullname(String perFullname) {
        PerFullname = perFullname;
    }

    public String getPerEmail() {
        return PerEmail;
    }

    public void setPerEmail(String perEmail) {
        PerEmail = perEmail;
    }

    public String getPerDob() {
        return PerDob;
    }

    public void setPerDob(String perDob) {
        PerDob = perDob;
    }

    public String getPerIdNumber() {
        return PerIdNumber;
    }

    public void setPerIdNumber(String perIdNumber) {
        PerIdNumber = perIdNumber;
    }

    public String getPerPassword() {
        return PerPassword;
    }

    public void setPerPassword(String perPassword) {
        PerPassword = perPassword;
    }

    public String getPerHashPassword() {
        return PerHashPassword;
    }

    public void setPerHashPassword(String perHashPassword) {
        PerHashPassword = perHashPassword;
    }

    public boolean isPerPasswordReset() {
        return PerPasswordReset;
    }

    public void setPerPasswordReset(boolean perPasswordReset) {
        PerPasswordReset = perPasswordReset;
    }

    public short getPerLockCount() {
        return PerLockCount;
    }

    public void setPerLockCount(short perLockCount) {
        PerLockCount = perLockCount;
    }

    public String getPerLockedAt() {
        return PerLockedAt;
    }

    public void setPerLockedAt(String perLockedAt) {
        PerLockedAt = perLockedAt;
    }

    public short getPerAccessType() {
        return PerAccessType;
    }

    public void setPerAccessType(short perAccessType) {
        PerAccessType = perAccessType;
    }

    public short getPerVerifyCode() {
        return PerVerifyCode;
    }

    public void setPerVerifyCode(short perVerifyCode) {
        PerVerifyCode = perVerifyCode;
    }

    public String getPerEmailVerified() {
        return PerEmailVerified;
    }

    public void setPerEmailVerified(String perEmailVerified) {
        PerEmailVerified = perEmailVerified;
    }

    public long getPerTransportId() {
        return PerTransportId;
    }

    public void setPerTransportId(long perTransportId) {
        PerTransportId = perTransportId;
    }

    public String getPerCellPhone() {
        return PerCellPhone;
    }

    public void setPerCellPhone(String perCellPhone) {
        PerCellPhone = perCellPhone;
    }

    public boolean isPerType() {
        return PerType;
    }

    public void setPerType(boolean perType) {
        PerType = perType;
    }

    @Override
    public String GetDisplay() {
        return getPerFullname();
    }

    @Override
    public long GetID() {
        return getPerId();
    }
}
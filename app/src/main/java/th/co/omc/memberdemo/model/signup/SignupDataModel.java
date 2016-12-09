package th.co.omc.memberdemo.model.signup;

import java.io.Serializable;

/**
 * Created by teera-s on 11/24/2016 AD.
 */

public class SignupDataModel implements Serializable {

    private String sponsorsID;
    private String sponsorsName;
    private String uplineID;
    private String uplineName;
    private String sideid;
    private String sidename;
    private String prefixID;
    private String prefixName;
    private String Name;
    private String genderID;
    private String genderName;
    private String birthDate;
    private String nationality;
    private String identification;
    private String mobile;
    private String email;
    private String address;
    private String province;
    private String district;
    private String subDistrict;
    private String zip;

    public SignupDataModel(String sponsorsID, String sponsorsName, String uplineID, String uplineName, String sideid, String sidename, String prefixID, String prefixName, String name, String genderID, String genderName, String birthDate, String nationality, String identification, String mobile, String email, String address, String province, String district, String subDistrict, String zip) {
        this.sponsorsID = sponsorsID;
        this.sponsorsName = sponsorsName;
        this.uplineID = uplineID;
        this.uplineName = uplineName;
        this.sideid = sideid;
        this.sidename = sidename;
        this.prefixID = prefixID;
        this.prefixName = prefixName;
        this.Name = name;
        this.genderID = genderID;
        this.genderName = genderName;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.identification = identification;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
        this.province = province;
        this.district = district;
        this.subDistrict = subDistrict;
        this.zip = zip;
    }

    public String getSponsorsID() {
        return sponsorsID;
    }

    public void setSponsorsID(String sponsorsID) {
        this.sponsorsID = sponsorsID;
    }

    public String getSponsorsName() {
        return sponsorsName;
    }

    public void setSponsorsName(String sponsorsName) {
        this.sponsorsName = sponsorsName;
    }

    public String getUplineID() {
        return uplineID;
    }

    public void setUplineID(String uplineID) {
        this.uplineID = uplineID;
    }

    public String getUplineName() {
        return uplineName;
    }

    public void setUplineName(String uplineName) {
        this.uplineName = uplineName;
    }

    public String getPrefixName() {
        return prefixName;
    }

    public void setPrefixName(String prefixName) {
        this.prefixName = prefixName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGenderID() {
        return genderID;
    }

    public void setGenderID(String genderID) {
        this.genderID = genderID;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPrefixID() {
        return prefixID;
    }

    public void setPrefixID(String prefixID) {
        this.prefixID = prefixID;
    }

    public String getSideid() {
        return sideid;
    }

    public void setSideid(String sideid) {
        this.sideid = sideid;
    }

    public String getSidename() {
        return sidename;
    }

    public void setSidename(String sidename) {
        this.sidename = sidename;
    }
}

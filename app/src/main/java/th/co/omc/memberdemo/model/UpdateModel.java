package th.co.omc.memberdemo.model;

import java.io.Serializable;

/**
 * Created by teera-s on 11/30/2016 AD.
 */

public class UpdateModel implements Serializable {

    private String mobile;
    private String email;
    private String address;
    private String building;
    private String village;
    private String soi;
    private String street;
    private String subDistrict;
    private String district;
    private String province;
    private String zip;
    private String imageName;


    public UpdateModel(String mobile, String email, String address, String building, String village, String soi, String street, String subDistrict, String district, String province, String zip, String imageName) {
        this.mobile = mobile;
        this.email = email;
        this.address = address;
        this.building = building;
        this.village = village;
        this.soi = soi;
        this.street = street;
        this.subDistrict = subDistrict;
        this.district = district;
        this.province = province;
        this.zip = zip;
        this.imageName = imageName;
    }

    public UpdateModel() {

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

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getSoi() {
        return soi;
    }

    public void setSoi(String soi) {
        this.soi = soi;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}

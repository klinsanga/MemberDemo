package th.co.omc.memberdemo.model.shopping;

/**
 * Created by teera-s on 11/10/2016 AD.
 */

public class AddressModel {

    private String memberCode;
    private String memberName;
    private String memberMobile;
    private String memberEmail;
    private String memberZip;
    private String memberAddress;
    private String memberBuilding;
    private String memberVillage;
    private String memberStreet;
    private String memberSoi;
    private String memberDistrict;
    private String memberAmphur;
    private String memberProvince;
    private String memberLocationBase;

    public AddressModel(String memberCode, String memberName, String memberMobile, String memberEmail, String memberZip, String memberAddress, String memberBuilding, String memberVillage, String memberStreet, String memberSoi, String memberDistrict, String memberAmphur, String memberProvince, String memberLocationBase) {
        this.memberCode = memberCode;
        this.memberName = memberName;
        this.memberMobile = memberMobile;
        this.memberEmail = memberEmail;
        this.memberZip = memberZip;
        this.memberAddress = memberAddress;
        this.memberBuilding = memberBuilding;
        this.memberVillage = memberVillage;
        this.memberStreet = memberStreet;
        this.memberSoi = memberSoi;
        this.memberDistrict = memberDistrict;
        this.memberAmphur = memberAmphur;
        this.memberProvince = memberProvince;
        this.memberLocationBase = memberLocationBase;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    public String getMemberZip() {
        return memberZip;
    }

    public void setMemberZip(String memberZip) {
        this.memberZip = memberZip;
    }

    public String getMemberAddress() {
        return memberAddress;
    }

    public void setMemberAddress(String memberAddress) {
        this.memberAddress = memberAddress;
    }

    public String getMemberStreet() {
        return memberStreet;
    }

    public void setMemberStreet(String memberStreet) {
        this.memberStreet = memberStreet;
    }

    public String getMemberBuilding() {
        return memberBuilding;
    }

    public void setMemberBuilding(String memberBuilding) {
        this.memberBuilding = memberBuilding;
    }

    public String getMemberVillage() {
        return memberVillage;
    }

    public void setMemberVillage(String memberVillage) {
        this.memberVillage = memberVillage;
    }

    public String getMemberSoi() {
        return memberSoi;
    }

    public void setMemberSoi(String memberSoi) {
        this.memberSoi = memberSoi;
    }

    public String getMemberDistrict() {
        return memberDistrict;
    }

    public void setMemberDistrict(String memberDistrict) {
        this.memberDistrict = memberDistrict;
    }

    public String getMemberAmphur() {
        return memberAmphur;
    }

    public void setMemberAmphur(String memberAmphur) {
        this.memberAmphur = memberAmphur;
    }

    public String getMemberProvince() {
        return memberProvince;
    }

    public void setMemberProvince(String memberProvince) {
        this.memberProvince = memberProvince;
    }

    public String getMemberLocationBase() {
        return memberLocationBase;
    }

    public void setMemberLocationBase(String memberLocationBase) {
        this.memberLocationBase = memberLocationBase;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }
}

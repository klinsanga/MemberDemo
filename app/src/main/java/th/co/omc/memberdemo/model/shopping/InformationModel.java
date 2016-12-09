package th.co.omc.memberdemo.model.shopping;

import java.io.Serializable;

/**
 * Created by teera-s on 11/15/2016 AD.
 */

public class InformationModel implements Serializable {

    private String uid;
    private String mcode;
    private String mname;
    private String phone;
    private String locationbase;
    private String totalPrice;
    private String totalPv;
    private String totalWeight;
    private String saType;
    private String saTypeId;
    private String payment;
    private String pahymentTypeId;
    private String send;
    private String sendId;
    private String cAddress;
    private String cDistrictId;
    private String cAmphurId;
    private String cProvinceId;
    private String cZip;
    private String cProvinceName;
    private String cAmphurName;
    private String cDistrictName;
    private String branch;
    private String branchName;
    private String note;

    public InformationModel(String uid, String mcode, String mname, String phone, String locationbase, String totalPrice, String totalPv, String totalWeight, String saType, String saTypeId
            , String payment, String pahymentTypeId, String send, String sendId, String cAddress, String cDistrictId, String cAmphurId
            , String cProvinceId, String cZip, String cProvinceName, String cAmphurName, String cDistrictName, String branch, String branchName, String note) {
        this.uid = uid;
        this.mcode = mcode;
        this.mname = mname;
        this.phone = phone;
        this.locationbase = locationbase;
        this.totalPrice = totalPrice;
        this.totalPv = totalPv;
        this.totalWeight = totalWeight;
        this.saType = saType;
        this.saTypeId = saTypeId;
        this.payment = payment;
        this.pahymentTypeId = pahymentTypeId;
        this.send = send;
        this.sendId = sendId;
        this.cAddress = cAddress;
        this.cDistrictId = cDistrictId;
        this.cAmphurId = cAmphurId;
        this.cProvinceId = cProvinceId;
        this.cZip = cZip;
        this.cProvinceName = cProvinceName;
        this.cAmphurName = cAmphurName;
        this.cDistrictName = cDistrictName;
        this.branch = branch;
        this.branchName = branchName;
        this.note = note;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMcode() {
        return mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
    }

    public String getLocationbase() {
        return locationbase;
    }

    public void setLocationbase(String locationbase) {
        this.locationbase = locationbase;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalPv() {
        return totalPv;
    }

    public void setTotalPv(String totalPv) {
        this.totalPv = totalPv;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getSaType() {
        return saType;
    }

    public void setSaType(String saType) {
        this.saType = saType;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public String getcAddress() {
        return cAddress;
    }

    public void setcAddress(String cAddress) {
        this.cAddress = cAddress;
    }

    public String getcDistrictId() {
        return cDistrictId;
    }

    public void setcDistrictId(String cDistrictId) {
        this.cDistrictId = cDistrictId;
    }

    public String getcAmphurId() {
        return cAmphurId;
    }

    public void setcAmphurId(String cAmphurId) {
        this.cAmphurId = cAmphurId;
    }

    public String getcProvinceId() {
        return cProvinceId;
    }

    public void setcProvinceId(String cProvinceId) {
        this.cProvinceId = cProvinceId;
    }

    public String getcZip() {
        return cZip;
    }

    public void setcZip(String cZip) {
        this.cZip = cZip;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getcProvinceName() {
        return cProvinceName;
    }

    public void setcProvinceName(String cProvinceName) {
        this.cProvinceName = cProvinceName;
    }

    public String getcAmphurName() {
        return cAmphurName;
    }

    public void setcAmphurName(String cAmphurName) {
        this.cAmphurName = cAmphurName;
    }

    public String getcDistrictName() {
        return cDistrictName;
    }

    public void setcDistrictName(String cDistrictName) {
        this.cDistrictName = cDistrictName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getSaTypeId() {
        return saTypeId;
    }

    public void setSaTypeId(String saTypeId) {
        this.saTypeId = saTypeId;
    }

    public String getPahymentTypeId() {
        return pahymentTypeId;
    }

    public void setPahymentTypeId(String pahymentTypeId) {
        this.pahymentTypeId = pahymentTypeId;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }
}
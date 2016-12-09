package th.co.omc.memberdemo.model.shopping;

/**
 * Created by teera-s on 11/16/2016 AD.
 */

public class DistrictModel {

    private String districtId;
    private String districtTh;
    private String districtEn;

    public DistrictModel(String districtId, String districtTh, String districtEn) {
        this.districtId = districtId;
        this.districtTh = districtTh;
        this.districtEn = districtEn;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getDistrictTh() {
        return districtTh;
    }

    public void setDistrictTh(String districtTh) {
        this.districtTh = districtTh;
    }

    public String getDistrictEn() {
        return districtEn;
    }

    public void setDistrictEn(String districtEn) {
        districtEn = districtEn;
    }
}

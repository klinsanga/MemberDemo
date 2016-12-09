package th.co.omc.memberdemo.model.shopping;

/**
 * Created by teera-s on 11/16/2016 AD.
 */

public class SubDistrictModel {

    private String subdistrictId;
    private String subdistrictTh;
    private String subdistrictEn;

    public SubDistrictModel(String subdistrictId, String subdistrictTh, String subdistrictEn) {
        this.subdistrictId = subdistrictId;
        this.subdistrictTh = subdistrictTh;
        this.subdistrictEn = subdistrictEn;
    }

    public String getSubdistrictId() {
        return subdistrictId;
    }

    public void setSubdistrictId(String subdistrictId) {
        this.subdistrictId = subdistrictId;
    }

    public String getSubdistrictTh() {
        return subdistrictTh;
    }

    public void setSubdistrictTh(String subdistrictTh) {
        this.subdistrictTh = subdistrictTh;
    }

    public String getSubdistrictEn() {
        return subdistrictEn;
    }

    public void setSubdistrictEn(String subdistrictEn) {
        this.subdistrictEn = subdistrictEn;
    }
}

package th.co.omc.memberdemo.model.shopping;

/**
 * Created by teera-s on 11/16/2016 AD.
 */

public class ProvinceModel {

    private String provicneId;
    private String provinceTh;
    private String ProvinceEn;

    public ProvinceModel(String provicneId, String provinceTh, String provinceEn) {
        this.provicneId = provicneId;
        this.provinceTh = provinceTh;
        this.ProvinceEn = provinceEn;
    }

    public String getProvicneId() {
        return provicneId;
    }

    public void setProvicneId(String provicneId) {
        this.provicneId = provicneId;
    }

    public String getProvinceTh() {
        return provinceTh;
    }

    public void setProvinceTh(String provinceTh) {
        this.provinceTh = provinceTh;
    }

    public String getProvinceEn() {
        return ProvinceEn;
    }

    public void setProvinceEn(String provinceEn) {
        ProvinceEn = provinceEn;
    }
}

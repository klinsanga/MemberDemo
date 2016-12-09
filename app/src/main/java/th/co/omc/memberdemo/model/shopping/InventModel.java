package th.co.omc.memberdemo.model.shopping;

/**
 * Created by teera-s on 11/17/2016 AD.
 */

public class InventModel {

    private String inventCode;
    private String inventDesc;

    public InventModel(String inventCode, String inventDesc) {
        this.inventCode = inventCode;
        this.inventDesc = inventDesc;
    }

    public String getInventCode() {
        return inventCode;
    }

    public void setInventCode(String inventCode) {
        this.inventCode = inventCode;
    }

    public String getInventDesc() {
        return inventDesc;
    }

    public void setInventDesc(String inventDesc) {
        this.inventDesc = inventDesc;
    }
}

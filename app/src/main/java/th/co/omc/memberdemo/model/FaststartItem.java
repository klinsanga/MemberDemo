package th.co.omc.memberdemo.model;

/**
 * Created by Teera-s.me on 27/9/2559.
 */

public class FaststartItem {

    private String fastDate;
    private String fastID;
    private String fastName;
    private String fastBonus;

    public FaststartItem(String fastDate, String fastID, String fastName, String fastBonus) {

        this.fastDate = fastDate;
        this.fastID = fastID;
        this.fastName = fastName;
        this.fastBonus = fastBonus;
    }

    public String getFastDate() {
        return fastDate;
    }

    public void setFastDate(String fastDate) {
        this.fastDate = fastDate;
    }

    public String getFastID() {
        return fastID;
    }

    public void setFastID(String fastID) {
        this.fastID = fastID;
    }

    public String getFastName() {
        return fastName;
    }

    public void setFastName(String fastName) {
        this.fastName = fastName;
    }

    public String getFastBonus() {
        return fastBonus;
    }

    public void setFastBonus(String fastBonus) {
        this.fastBonus = fastBonus;
    }
}

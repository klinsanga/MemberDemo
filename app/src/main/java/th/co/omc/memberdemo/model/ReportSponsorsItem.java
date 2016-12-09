package th.co.omc.memberdemo.model;

/**
 * Created by Teera-s.me on 24/9/2559.
 */

public class ReportSponsorsItem {

    private String id;
    private String pv;
    private String name;
    private String issueDate;
    private String position;
    private String honor;
    private String sponsorsID;
    private String sponsorsName;
    private String sponsorsLevel;
    private String imgProfile;

    public ReportSponsorsItem() {

    }

    public ReportSponsorsItem(String id, String name, String issueDate, String position, String honor, String pv, String sponsorsID, String sponsorsName, String sponsorsLevel, String imgProfile) {
        this.id = id;
        this.pv = pv;
        this.name = name;
        this.issueDate = issueDate;
        this.position = position;
        this.honor = honor;
        this.sponsorsID = sponsorsID;
        this.sponsorsName = sponsorsName;
        this.sponsorsLevel = sponsorsLevel;
        this.imgProfile = imgProfile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPv() {
        return pv;
    }

    public void setPv(String pv) {
        this.pv = pv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getHonor() {
        return honor;
    }

    public void setHonor(String honor) {
        this.honor = honor;
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

    public String getSponsorsLevel() {
        return sponsorsLevel;
    }

    public void setSponsorsLevel(String sponsorsLevel) {
        this.sponsorsLevel = sponsorsLevel;
    }

    public String getImgProfile() {
        return imgProfile;
    }

    public void setImgProfile(String imgProfile) {
        this.imgProfile = imgProfile;
    }
}

package th.co.omc.memberdemo.model;


/**
 * Created by Teera-s.me on 18/9/2559.
 */
public class ItemMember {

    private String memberCode;
    private String memberRegisterDate;
    private String birthDate;
    private String gender;
    private String mobile;
    private String email;
    private String sponsorsName;
    private String uplinName;
    private String ewallet;

    private String fullName;
    private String profileImage;

    private String currentPosition;
    private String currentSuperPosistion;
    private String superPositionImage;
    private String personalPV;
    private String autoship;
    private String downlineLeftName;
    private String downlineLeftCode;
    private String downlineLeftPoints;
    private String downlineLeftImage;
    private String downlineRightName;
    private String downlineRightCode;
    private String downlineRightPoints;
    private String downlineRightImage;
    private String locationbase;

    public ItemMember () {

    }

    public ItemMember(String mcode, String fullName, String mdate, String birthday, String sex, String mobile, String email, String sp_name, String upa_name, String ewallet, String profileImage, String currentPosition, String currentSuperPosistion, String superPositionImage, String personalPV, String autoship, String downlineLeftName, String downlineLeftCode, String downlineLeftPoints, String downlineLeftImage, String downlineRightName, String downlineRightCode, String downlineRightPoints, String downlineRightImage, String locationbase) {
        this.memberCode = mcode;
        this.fullName = fullName;
        this.memberRegisterDate = mdate;
        this.birthDate = birthday;
        this.gender = sex;
        this.mobile = mobile;
        this.email = email;
        this.sponsorsName = sp_name;
        this.uplinName = upa_name;
        this.ewallet = ewallet;
        this.profileImage = profileImage;
        this.currentPosition = currentPosition;
        this.currentSuperPosistion = currentSuperPosistion;
        this.superPositionImage = superPositionImage;
        this.personalPV = personalPV;
        this.autoship = autoship;
        this.downlineLeftName = downlineLeftName;
        this.downlineLeftCode = downlineLeftCode;
        this.downlineLeftPoints = downlineLeftPoints;
        this.downlineLeftImage = downlineLeftImage;
        this.downlineRightName = downlineRightName;
        this.downlineRightCode = downlineRightCode;
        this.downlineRightPoints = downlineRightPoints;
        this.downlineRightImage = downlineRightImage;
        this.locationbase = locationbase;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String mcode) {
        this.memberCode = mcode;
    }

    public String getMemberRegisterDate() {
        return memberRegisterDate;
    }

    public void setMemberRegisterDate(String mdate) {
        this.memberRegisterDate = mdate;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthday) {
        this.birthDate = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String sex) {
        this.gender = sex;
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

    public String getSponsorsName() {
        return sponsorsName;
    }

    public void setSponsorsName(String sp_name) {
        this.sponsorsName = sp_name;
    }

    public String getUplinName() {
        return uplinName;
    }

    public void setUplinName(String upa_name) {
        this.uplinName = upa_name;
    }

    public String getEwallet() {
        return ewallet;
    }

    public void setEwallet(String ewallet) {
        this.ewallet = ewallet;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    public String getCurrentSuperPosistion() {
        return currentSuperPosistion;
    }

    public void setCurrentSuperPosistion(String currentSuperPosistion) {
        this.currentSuperPosistion = currentSuperPosistion;
    }

    public String getSuperPositionImage() {
        return superPositionImage;
    }

    public void setSuperPositionImage(String superPositionImage) {
        this.superPositionImage = superPositionImage;
    }

    public String getPersonalPV() {
        return personalPV;
    }

    public void setPersonalPV(String personalPV) {
        this.personalPV = personalPV;
    }

    public String getAutoship() {
        return autoship;
    }

    public void setAutoship(String autoship) {
        this.autoship = autoship;
    }

    public String getDownlineLeftPoints() {
        return downlineLeftPoints;
    }

    public void setDownlineLeftPoints(String downlineLeftPoints) {
        this.downlineLeftPoints = downlineLeftPoints;
    }

    public String getDownlineRightPoints() {
        return downlineRightPoints;
    }

    public void setDownlineRightPoints(String downlineRightPoints) {
        this.downlineRightPoints = downlineRightPoints;
    }

    public String getLocationbase() {
        return locationbase;
    }

    public void setLocationbase(String locationbase) {
        this.locationbase = locationbase;
    }

    public String getDownlineLeftName() {
        return downlineLeftName;
    }

    public void setDownlineLeftName(String downlineLeftName) {
        this.downlineLeftName = downlineLeftName;
    }

    public String getDownlineLeftCode() {
        return downlineLeftCode;
    }

    public void setDownlineLeftCode(String downlineLeftCode) {
        this.downlineLeftCode = downlineLeftCode;
    }

    public String getDownlineRightName() {
        return downlineRightName;
    }

    public void setDownlineRightName(String downlineRightName) {
        this.downlineRightName = downlineRightName;
    }

    public String getDownlineRightCode() {
        return downlineRightCode;
    }

    public void setDownlineRightCode(String downlineRightCode) {
        this.downlineRightCode = downlineRightCode;
    }

    public String getDownlineLeftImage() {
        return downlineLeftImage;
    }

    public void setDownlineLeftImage(String downlineLeftImage) {
        this.downlineLeftImage = downlineLeftImage;
    }

    public String getDownlineRightImage() {
        return downlineRightImage;
    }

    public void setDownlineRightImage(String downlineRightImage) {
        this.downlineRightImage = downlineRightImage;
    }
}

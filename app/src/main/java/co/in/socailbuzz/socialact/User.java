package co.in.socailbuzz.socialact;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String id;
    private String name;
    private String designation;
    private String company;
    private String email;
    private String mobile;
    private String empcode;
    private String type;
    private String avatar;
    private String band_uid;
    private String band_number;
    private String social_id;
    private String checkin;

    public User(String id, String name, String designation, String company, String email, String mobile,String empcode, String type, String avatar, String band_uid, String band_number, String social_id, String checkin) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.company = company;
        this.email = email;
        this.mobile = mobile;
        this.type = type;
        this.avatar = avatar;
        this.band_uid = band_uid;
        this.band_number = band_number;
        this.social_id = social_id;
        this.checkin = checkin;
        this.empcode = empcode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmpcode() {
        return empcode;
    }

    public void setEmpcode(String empcode) {
        this.empcode = empcode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBand_uid() {
        return band_uid;
    }

    public void setBand_uid(String band_uid) {
        this.band_uid = band_uid;
    }

    public String getBand_number() {
        return band_number;
    }

    public void setBand_number(String band_number) {
        this.band_number = band_number;
    }

    public String getSocial_id() {
        return social_id;
    }

    public void setSocial_id(String social_id) {
        this.social_id = social_id;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.designation);
        dest.writeString(this.company);
        dest.writeString(this.email);
        dest.writeString(this.mobile);
        dest.writeString(this.empcode);
        dest.writeString(this.type);
        dest.writeString(this.avatar);
        dest.writeString(this.band_uid);
        dest.writeString(this.band_number);
        dest.writeString(this.social_id);
        dest.writeString(this.checkin);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.designation = in.readString();
        this.company = in.readString();
        this.email = in.readString();
        this.mobile = in.readString();
        this.empcode = in.readString();
        this.type = in.readString();
        this.avatar = in.readString();
        this.band_uid = in.readString();
        this.band_number = in.readString();
        this.social_id = in.readString();
        this.checkin = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj != null && !(obj instanceof User))
            return false;
        User user = (User) obj;

        return (this.email.equals(user.email) &&
                this.mobile.equals(user.mobile));
    }
}

package partner.its.com.retrofitproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by roman on 9.6.17.
 */

public class RepoModel implements Parcelable{
    @SerializedName("id")
    @Expose
    private int mId;

    @SerializedName("name")
    @Expose
    private String mName;

    @SerializedName("description")
    @Expose
    private String mDescription;

    public RepoModel(int id, String name, String description) {
        mId = id;
        mName = name;
        mDescription = description;
    }

    public RepoModel(Parcel in){
        mId = in.readInt();
        mName = in.readString();
        mDescription = in.readString();
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeString(mDescription);
    }

    public static final Parcelable.Creator<RepoModel> CREATOR = new Parcelable.Creator<RepoModel>(){
        @Override
        public RepoModel createFromParcel(Parcel source) {
            return new RepoModel(source);
        }

        @Override
        public RepoModel[] newArray(int size) {
            return new RepoModel[size];
        }
    };
}

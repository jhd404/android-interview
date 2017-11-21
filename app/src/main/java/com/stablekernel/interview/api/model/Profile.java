package com.stablekernel.interview.api.model;

/*
    Provide an implementation of this data model.

    A Profile consists of:
        * A name.
        * A percentage representing their progress.
        * Any number of skills.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class Profile implements Parcelable {

    @SerializedName("name")
    private String mName;

    @SerializedName("progress")
    private double mProgress;

    @SerializedName("skills")
    private List<String> mSkills;

    public Profile(String name, double progress, List<String> skills) {
        this.mName = name;
        this.mProgress = progress;
        this.mSkills = skills;
    }

    private Profile(Parcel in) {
        mName = in.readString();
        mProgress = in.readDouble();
        mSkills = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mName);
        out.writeDouble(mProgress);
        out.writeStringList(mSkills);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Profile> CREATOR
            = new Parcelable.Creator<Profile>() {

        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    public String getName() {
        return mName;
    }

    public double getProgress() {
        return mProgress;
    }

    public List<String> getSkills() {
        return mSkills;
    }
}

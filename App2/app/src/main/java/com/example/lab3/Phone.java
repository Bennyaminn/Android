package com.example.lab3;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "phone_table")
public class Phone  implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @NonNull
    @ColumnInfo(name = "producent")
    private String mProducent;

    @NonNull
    @ColumnInfo(name = "model")
    private String mModel;

    @NonNull
    @ColumnInfo(name = "version")
    private String mVersion;

    @NonNull
    @ColumnInfo(name = "website")
    private String mWebsite;

    public Phone(@NonNull String producent, @NonNull String model, @NonNull String version, @NonNull String website){
        mProducent = producent;
        mModel = model;
        mVersion = version;
        mWebsite = website;
    }

    protected Phone(Parcel in) {
        mId = in.readLong();
        mProducent = in.readString();
        mModel = in.readString();
        mVersion = in.readString();
        mWebsite = in.readString();
    }

    public static final Creator<Phone> CREATOR = new Creator<Phone>() {
        @Override
        public Phone createFromParcel(Parcel in) {
            return new Phone(in);
        }

        @Override
        public Phone[] newArray(int size) {
            return new Phone[size];
        }
    };

    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    @NonNull
    public String getProducent() {
        return mProducent;
    }

    public void setProducent(@NonNull String mProducent) {
        this.mProducent = mProducent;
    }

    @NonNull
    public String getModel() {
        return mModel;
    }

    public void setModel(@NonNull String mModel) {
        this.mModel = mModel;
    }

    @NonNull
    public String getVersion() {
        return mVersion;
    }

    public void setVersion(@NonNull String mVersion) {
        this.mVersion = mVersion;
    }

    @NonNull
    public String getWebsite() {
        return mWebsite;
    }

    public void setWebsite(@NonNull String mWebsite) {
        this.mWebsite = mWebsite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mId);
        parcel.writeString(mProducent);
        parcel.writeString(mModel);
        parcel.writeString(mVersion);
        parcel.writeString(mWebsite);
    }
}
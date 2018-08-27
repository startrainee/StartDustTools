package com.stardust.tools.startdusttools;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created on 2018/8/27.
 *
 * @author siasun-wangchongyang
 */
public class PoiModel implements SearchSuggestion{

    private String body;

    PoiModel(String body) {
        this.body = body;
    }

    @Override
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    private PoiModel(Parcel in) {
        body = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(body);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PoiModel> CREATOR = new Creator<PoiModel>() {
        @Override
        public PoiModel createFromParcel(Parcel in) {
            return new PoiModel(in);
        }

        @Override
        public PoiModel[] newArray(int size) {
            return new PoiModel[size];
        }
    };
}


package com.netlopa.pokechallenge.api.shakespeareapi.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShakespeareApiResponse {

    @SerializedName("success")
    @Expose
    private Success success;
    @SerializedName("contents")
    @Expose
    private Contents contents;

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }

    public Contents getContents() {
        return contents;
    }

    public void setContents(Contents contents) {
        this.contents = contents;
    }

}

package net.jkcats.simplemvvm.network.api;

import com.google.gson.annotations.SerializedName;

import net.jkcats.simplemvvm.network.annotation.ResponseState;

public abstract class ResponseData {
    @SerializedName("errorCode")
    public int code = -1;
    @SerializedName("errorMsg")
    public String msg = null;
    public @ResponseState int state = ResponseState.FAILURE;
}

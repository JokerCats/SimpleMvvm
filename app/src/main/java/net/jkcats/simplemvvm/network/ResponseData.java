package net.jkcats.simplemvvm.network;

import com.google.gson.annotations.SerializedName;

public class ResponseData<T> {
    @SerializedName("errorCode")
    public int code = -1;
    @SerializedName("errorMsg")
    public String msg = null;
    public T data = null;
    public ResponseState state = ResponseState.Failure;
}

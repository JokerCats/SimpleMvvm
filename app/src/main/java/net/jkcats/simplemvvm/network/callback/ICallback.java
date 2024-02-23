package net.jkcats.simplemvvm.network.callback;

public interface ICallback {

    void onSuccess(String response);

    void onFailure(String msg);
}

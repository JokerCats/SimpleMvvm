package net.jkcats.simplemvvm.network.process;

import net.jkcats.simplemvvm.network.callback.ICallback;

import java.util.Map;

public interface IClientProcess {

    void sendGet(String path, ICallback callback);

    void sendGet(String path, Map<String, Object> params, ICallback callback);

    void sendPost(String path, Map<String, Object> params, ICallback callback);
}

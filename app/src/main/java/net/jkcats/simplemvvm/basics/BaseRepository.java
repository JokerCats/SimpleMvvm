package net.jkcats.simplemvvm.basics;

import net.jkcats.simplemvvm.network.ResponseData;
import net.jkcats.simplemvvm.network.ResponseState;
import net.jkcats.simplemvvm.network.RequestConfig;

import java.util.function.Function;

public class BaseRepository {

    protected void executeSP() {

    }

    protected void executeDB() {

    }

    protected void executeFiles() {

    }

    protected <T> ResponseData<T> executeServer(Function<Void, ResponseData<T>> block) {
        ResponseData<T> data = block.apply(null);
        if (data.code == RequestConfig.ERROR_CODE) {
            data.state = ResponseState.Failure;
        } else {
            data.state = ResponseState.Success;
        }
        return data;
    }
}

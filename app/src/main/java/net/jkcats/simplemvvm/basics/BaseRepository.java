package net.jkcats.simplemvvm.basics;

import net.jkcats.simplemvvm.network.RequestConfig;
import net.jkcats.simplemvvm.network.RequestService;
import net.jkcats.simplemvvm.network.ResponseBlock;
import net.jkcats.simplemvvm.network.ResponseData;
import net.jkcats.simplemvvm.network.ResponseState;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

public abstract class BaseRepository {

    @Inject
    protected RequestService mRequestService;

    protected void executeSP() {

    }

    protected void executeDB() {

    }

    protected void executeFiles() {

    }

    protected <T> ResponseData<T> executeServer(ResponseBlock<T> block) {
        ResponseData<T> data = block.execute();
        if (data.code == RequestConfig.ERROR_CODE) {
            data.state = ResponseState.Failure;
        } else {
            data.state = ResponseState.Success;
        }
        return data;
    }
}

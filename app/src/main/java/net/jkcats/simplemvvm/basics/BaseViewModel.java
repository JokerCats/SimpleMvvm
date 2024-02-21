package net.jkcats.simplemvvm.basics;

import androidx.lifecycle.ViewModel;

import net.jkcats.simplemvvm.assist.SingleLiveData;
import net.jkcats.simplemvvm.network.RequestBlock;

public class BaseViewModel extends ViewModel {

    public SingleLiveData<String> mCrashData = new SingleLiveData<>();

    public SingleLiveData<Boolean> mLoadingData = new SingleLiveData<>();

    public void sendRequest(RequestBlock block) {
        try {
            block.invoke();
        } catch (Exception exception) {
            mCrashData.postValue(exception.getMessage());
        }
    }

    public void sendRequestWithLoading(RequestBlock block) {
        invokeLoading();
        new Thread(() -> {
            try {
                block.invoke();
            } catch (Exception exception) {
                mCrashData.postValue(exception.getMessage());
            } finally {
                finishLoading();
            }
        }).start();
    }

    public void invokeLoading() {
        mLoadingData.postValue(true);
    }

    public void finishLoading() {
        mLoadingData.postValue(false);
    }
}

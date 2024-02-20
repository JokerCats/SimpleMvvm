package net.jkcats.simplemvvm.basics;

import androidx.lifecycle.ViewModel;

import net.jkcats.simplemvvm.assist.SingleLiveData;
import net.jkcats.simplemvvm.network.RequestBlock;

public class BaseViewModel extends ViewModel {

    protected SingleLiveData<String> mCrashData = new SingleLiveData<>();

    protected SingleLiveData<Boolean> mLoadingData = new SingleLiveData<>();

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

    private void invokeLoading() {
        mLoadingData.postValue(true);
    }

    private void finishLoading() {
        mLoadingData.postValue(false);
    }
}

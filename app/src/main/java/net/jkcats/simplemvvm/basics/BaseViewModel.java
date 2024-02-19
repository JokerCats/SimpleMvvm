package net.jkcats.simplemvvm.basics;

import androidx.lifecycle.ViewModel;

import net.jkcats.simplemvvm.assist.SingleLiveData;

import java.util.function.Function;

public class BaseViewModel extends ViewModel {

    protected SingleLiveData<String> mCrashData = new SingleLiveData<>();

    protected SingleLiveData<Boolean> mLoadingData = new SingleLiveData<>();

    public void sendRequest(Function<Void, Void> block) {
        try {
            block.apply(null);
        } catch (Exception exception) {
            mCrashData.postValue(exception.getMessage());
        }
    }

    public void sendRequestWithLoading(Function<Void, Void> block) {
        invokeLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    block.apply(null);
                } catch (Exception exception) {
                    mCrashData.postValue(exception.getMessage());
                } finally {
                    finishLoading();
                }
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

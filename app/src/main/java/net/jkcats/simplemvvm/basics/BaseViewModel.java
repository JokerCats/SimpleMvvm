package net.jkcats.simplemvvm.basics;

import androidx.lifecycle.ViewModel;

import net.jkcats.simplemvvm.assist.SingleLiveData;
import net.jkcats.simplemvvm.network.api.RequestBlock;
import net.jkcats.simplemvvm.network.proxy.ClientProxy;

import javax.inject.Inject;

public abstract class BaseViewModel extends ViewModel {

    @Inject
    protected ClientProxy mClientProxy;

    protected SingleLiveData<String> mCrashData = new SingleLiveData<>();

    protected SingleLiveData<Boolean> mLoadingData = new SingleLiveData<>();

    public void sendRequestWithLoading(RequestBlock block) {
        invokeLoading();
        try {
            block.invoke();
        } catch (Exception exception) {
            mCrashData.postValue(exception.getMessage());
        } finally {
            finishLoading();
        }
    }

    private void invokeLoading() {
        mLoadingData.postValue(true);
    }

    private void finishLoading() {
        mLoadingData.postValue(false);
    }
}

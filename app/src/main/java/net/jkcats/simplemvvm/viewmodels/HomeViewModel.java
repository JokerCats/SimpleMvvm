package net.jkcats.simplemvvm.viewmodels;

import androidx.lifecycle.MutableLiveData;

import net.jkcats.simplemvvm.basics.BaseViewModel;
import net.jkcats.simplemvvm.models.HomeModel;
import net.jkcats.simplemvvm.network.callback.ServerCallback;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeViewModel extends BaseViewModel {

    @Inject
    public HomeViewModel() {

    }

    public final MutableLiveData<HomeModel> homeData = new MutableLiveData<>();

    public void getHomeData() {
        sendRequestWithLoading(
                () -> mClientProxy.sendGet(
                        "banner/json", new ServerCallback<HomeModel>() {
                            @Override
                            public void onCompleted(HomeModel result) {
                                homeData.postValue(result);
                            }

                            @Override
                            public void onError(String errorMsg) {
                                mCrashData.postValue(errorMsg);
                            }
                        }
                )
        );
    }
}
